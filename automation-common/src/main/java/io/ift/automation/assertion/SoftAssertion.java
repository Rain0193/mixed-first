
package io.ift.automation.assertion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.ift.automation.AppName;
import io.ift.automation.helpers.CollectionsHelper;
import io.ift.automation.helpers.DateHelper;
import io.ift.automation.helpers.JSONHelper;
import io.ift.automation.helpers.StringHelper;
import io.ift.automation.helpers.database.SpringJdbcTemplateUtils;
import io.ift.automation.logging.TestResultLogger;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.springframework.http.ResponseEntity;
import org.testng.Assert;

import javax.annotation.Nullable;


public class SoftAssertion {

    private List<Throwable> softAssertList = new ArrayList<>();
    ;
    private static final Logger logger = LogManager.getLogger(SoftAssertion.class.getName());

    /**
     * 创建SoftAssetion实例
     *
     * @return
     */
    public static SoftAssertion build() {
        return new SoftAssertion();
    }

    /**
     * 增加出错信息
     *
     * @param e
     */
    private void addSoftAsserts(Throwable e) {
        logger.info(e.toString());
        TestResultLogger.error(e);
        softAssertList.add(e);
    }

    /**
     * 简单的一个JSON字符串的方法，可以忽略一部分key值，忽略字段通过ignoreFields传入
     * use SoftAssertion#assertJson
     *
     * @param reason       “出错的原因”
     * @param actualJson   实际的json字符串
     * @param expectedJson 期望的json字符串
     * @param ignoreFields 忽略的JSON key 值
     */

    public SoftAssertion assertJsonObject(final String actualJson, final String expectedJson,
                                          final String reason, String... ignoreFields) {
        try {
            JSONObject act = JSONHelper.parseObject(actualJson.trim());
            JSONObject exp = JSONHelper.parseObject(expectedJson.trim());
            compareJson(reason, act, exp, ignoreFields);
        } catch (ClassCastException e) {
            JSONArray act = JSONHelper.parseArray(actualJson.trim());
            JSONArray exp = JSONHelper.parseArray(expectedJson.trim());
            compareJson(reason, act, exp, ignoreFields);
        }

        return this;//遍历Actual，放置多余字段
    }

    /**
     * 简单的一个JSON字符串的方法，可以忽略一部分key值，忽略字段通过ignoreFields传入
     * 建议使用这个方法,名字更加不容易引起误会
     *
     * @param reason       “出错的原因”
     * @param actualJson   实际的json字符assertApiResponse串
     * @param expectedJson 期望的json字符串
     * @param ignoreFields 忽略的JSON key 值
     */
    public SoftAssertion assertJson(final String actualJson, final String expectedJson,
                                    final String reason, String... ignoreFields) {

        return assertJsonObject(actualJson, expectedJson, reason, ignoreFields);
    }

    /**
     * 比较JSON
     *
     * @param reason       “出错的原因”
     * @param act          实际的json字符assertApiResponse串
     * @param exp          期望的json字符串
     * @param ignoreFields 忽略的JSON key 值
     */
    @SuppressWarnings("SuspiciousMethodCalls")
    private void compareJson(String reason, JSONObject act, JSONObject exp, String[] ignoreFields) {

        if (act != null && exp != null) {
            this.assertEquals(act.size(), exp.size(), "返回的字段数目是否相同");

            for (Map.Entry entry : exp.entrySet()) {
                if (isIgnoredFields(ignoreFields, entry)) continue;
                try {
                    if (entry.getValue() == null) {
                        if (null == act.get(entry.getKey())) {
                            continue;
                        }
                    }
                    if (isIgnoredFields(ignoreFields, entry)) continue;

                    //if JsonObject
                    if (entry.getValue() instanceof JSONObject && act.get(entry.getKey()) instanceof JSONObject) {
                        compareJson(reason, ((JSONObject) act.get(entry.getKey())), ((JSONObject) entry.getValue()), ignoreFields);
                        return;
                    }

                    //if JsonArray
                    if (entry.getValue() instanceof JSONArray && act.get(entry.getKey()) instanceof JSONArray) {
                        compareJson(reason, ((JSONArray) act.get(entry.getKey())), ((JSONArray) entry.getValue()), ignoreFields);
                        return;
                    }

                    //assert value
                    this.assertEquals(act.get(entry.getKey()), entry.getValue(), "检查" + entry.getKey() + "是否一致");
                } catch (Exception e) {
                    addSoftAsserts(e);
                }
            }
        } else {
            if (!(act == null && exp == null)) {
                assertTrue(false, "期望和实际返回中有个是null");
            }
        }
    }

    /**
     * 比较JSON
     *
     * @param reason       “出错的原因”
     * @param act          实际的json字符assertApiResponse串
     * @param exp          期望的json字符串
     * @param ignoreFields 忽略的JSON key 值
     */
    private void compareJson(String reason, JSONArray act, JSONArray exp, String[] ignoreFields) {
        try {
            if (act == null && exp == null) return;
            this.assertEquals(act.size(), exp.size(), reason);
            for (int i = 0; i < act.size(); i++) {

                if (act.get(0) instanceof JSONObject && exp.get(0) instanceof JSONObject) {
                    compareJson(reason, ((JSONObject) act.get(i)), ((JSONObject) exp.get(i)), ignoreFields);
                    return;
                }

                if (act.get(0) instanceof JSONArray && exp.get(0) instanceof JSONArray) {
                    compareJson(reason, ((JSONArray) act.get(i)), ((JSONArray) exp.get(i)), ignoreFields);
                    return;
                }

                this.assertEquals(act.get(i), exp.get(i), reason);
            }
        } catch (Exception e) {
            addSoftAsserts(e);
        }

    }

    /**
     * judge if the fields are ignored when compare
     *
     * @param ignoreFields
     * @param entry
     * @return
     */
    private boolean isIgnoredFields(String[] ignoreFields, Map.Entry entry) {

        return CollectionsHelper.arrayContains(ignoreFields, entry.getKey().toString());
    }

    /**
     * 检查日期是否相等
     *
     * @param actual
     * @param expected
     * @param reason
     * @param ignoreDay，不比较day
     */
    public SoftAssertion assertDate(String actual, String expected, String reason, boolean ignoreDay) {
        Triple actualTriple = DateHelper.convertDate(actual);
        Triple expectedTriple = DateHelper.convertDate(expected);
        if (ignoreDay) {
            assertEquals(actualTriple.getLeft(), expectedTriple.getLeft(), reason + ":检查年份");
            assertEquals(actualTriple.getMiddle(), expectedTriple.getMiddle(), reason + ":检查月份");
        } else {
            assertEquals(actualTriple.getLeft(), expectedTriple.getLeft(), reason + ":检查年份");
            assertEquals(actualTriple.getMiddle(), expectedTriple.getMiddle(), reason + ":检查月份");
            assertEquals(actualTriple.getRight(), expectedTriple.getRight(), reason + ":检查日");
        }

        return this;
    }

    /**
     * 判断是否为true
     *
     * @param condition
     * @param msg
     */
    public SoftAssertion assertTrue(boolean condition, String msg) {
        try {
            Assert.assertTrue(condition, msg);
        } catch (AssertionError e) {
            addSoftAsserts(e);
        }

        return this;
    }

    /**
     * @param reason  检查原因
     * @param actual  实际值
     * @param matcher 匹配器
     * @param <T>     匹配器匹配类型
     */
    public <T> SoftAssertion assertThat(T actual, Matcher<? super T> matcher, String reason) {
        try {

            MatcherAssert.assertThat(reason, actual, matcher);

        } catch (AssertionError e) {

            addSoftAsserts(e);
        }

        return this;
    }

    /**
     * 判断是否为false
     *
     * @param condition
     * @param msg
     */
    public SoftAssertion assertFalse(boolean condition, String msg) {
        try {
            Assert.assertFalse(condition, msg);
        } catch (AssertionError e) {
            addSoftAsserts(e);
        }

        return this;
    }

    /**
     * 判断是否相等
     *
     * @param actual
     * @param expected
     * @param msg
     */
    public SoftAssertion assertEquals(Object actual, Object expected, String msg) {
        try {

            Assert.assertEquals(actual, expected, msg);
        } catch (AssertionError e) {
            TestResultLogger.error("actual: {}, but expected is {}", actual, expected);
            addSoftAsserts(e);
        }

        return this;
    }

    /**
     * 错误数量
     *
     * @return
     */
    public int errorCount() {
        return softAssertList.size();
    }

    /**
     * 获取错误
     *
     * @param index
     * @return
     */
    public Throwable get(int index) {
        return softAssertList.get(index);
    }

    /**
     * 加入测试结果，以方便得到最后结果
     *
     * @param sa
     */
    public SoftAssertion addTestResult(SoftAssertion sa) {
        this.softAssertList.addAll(sa.getSoftAssertList());
        return this;
    }

    /**
     * 加入异常
     *
     * @return
     */
    public List<Throwable> getSoftAssertList() {
        return softAssertList;
    }

    /**
     * 输入最后的检查结果
     */
    public void getFinalResult() {
        if (!softAssertList.isEmpty()) {
            for (Throwable throwable : softAssertList) {
                TestResultLogger.error(throwable.getMessage());
                TestResultLogger.error(throwable.getCause());
                TestResultLogger.error(throwable);
            }
        }
        Assert.assertTrue(softAssertList.isEmpty(), softAssertList.toString());
    }

    /**
     * 验证API返回值
     *
     * @param response         api 真实返回
     * @param expectedStatus   api 状态
     * @param expectedJsonBody 期望api返回包体
     * @return
     */
    public SoftAssertion simpleAssertApiResponse(ResponseEntity response, String expectedStatus, String expectedJsonBody) {

        this.assertEquals(response.getStatusCode().toString(), expectedStatus, "检查返回状态");
        if (expectedStatus.equalsIgnoreCase("200")) {
            this.assertEquals(JSONHelper.parseObject(response.getBody().toString()), JSONHelper.parseObject(expectedJsonBody), "检查返回包体");
        }

        return this;
    }

    /**
     * 验证API返回值
     *
     * @param response         api 真实返回
     * @param expectedStatus   api 状态
     * @param expectedJsonBody 期望api返回包体
     * @param ignoreFields     需要忽略的字段
     * @return
     */
    public SoftAssertion assertApiResponse(ResponseEntity response, String expectedStatus, String expectedJsonBody, String... ignoreFields) {

        if (response.getBody() == null) {
            this.assertTrue(false, "response body is null not as expected");
            return this;
        }
        this.assertEquals(response.getStatusCode().toString(), expectedStatus, "检查返回状态");
        if (StringHelper.isNoneContentString(expectedJsonBody)) {
            expectedJsonBody = "{}";
        }
        return assertJson(response.getBody().toString(), expectedJsonBody, "检查返回包体", ignoreFields);
    }


    /**
     * 验证数据库的值
     *
     * @param appName        应用的名称
     * @param sql            获取数据的sql
     * @param expectedValues 期望值
     * @return
     */
    public SoftAssertion assertDBValues(String appName, String sql,
                                        Map<String, String> expectedValues) {

        waitForSubmit(5000L); //wait for verify data
        Map<String, Object> dbResult = SpringJdbcTemplateUtils
                .useDataBase(appName).getMultipleValue(sql, Lists.newArrayList(
                        expectedValues.keySet()), new com.google.common.base.Function() {
                    @Nullable
                    @Override
                    public Object apply(Object o) {
                        return o == null || o.toString().equalsIgnoreCase("null") ? StringHelper.EMPTY : o;
                    }
                });
        for (Map.Entry<String, String> entry : expectedValues.entrySet()) {
            this.assertEquals(String.valueOf(dbResult.get(entry.getKey())), String.valueOf(entry.getValue()), "检查字段" + entry.getKey());
        }
        return this;
    }

    public <T> SoftAssertion assertContains(Collection<Map<String,String>> source
                    ,Map<String,T> valueToCheck,String message){
        boolean flag = true;
        for (Map<String, String> map : source) {
            if(map.size()==valueToCheck.size()){
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    if(!String.valueOf(entry.getValue())
                            .equals(String.valueOf(valueToCheck.get(entry.getKey())))){
                        flag=false;
                    }
                }
            }

        }

        assertTrue(flag,message+": "+valueToCheck+"是否在预期结果中");
        return this;

    }

    /**
     * 检查是否数据库返回值在指定的Map中
     * @param appName
     * @param sql
     * @param referenceValues
     * @return
     */
    public SoftAssertion assertContainsDBValues(String appName, String sql,
                                                Map<String, String> referenceValues) {

        Map<String, Object> dbResult = SpringJdbcTemplateUtils
                .useDataBase(appName).getMultipleValue(sql, Lists.newArrayList(
                        referenceValues.keySet()), new com.google.common.base.Function() {
                    @Nullable
                    @Override
                    public Object apply(Object o) {
                        return o == null || o.toString().equalsIgnoreCase("null") ? StringHelper.EMPTY : o;
                    }
                });
        for (Map.Entry<String, Object> entry : dbResult.entrySet()) {
            this.assertEquals(String.valueOf(dbResult.get(entry.getKey())), String.valueOf(entry.getValue()), "检查字段" + entry.getKey());
        }
        return this;
    }


    /**
     * 验证数据库的值
     *
     * @param appName        应用的名称
     * @param sql            获取数据的sql
     * @param expectedValues 期望值
     * @return
     */
    public SoftAssertion assertDBValues(String appName, String sql,
                                        List<Map<String, String>> expectedValues) {

        List<Map<String, Object>> dbResult = SpringJdbcTemplateUtils
                .useDataBase(appName).getAllRawResult(sql);
        List<Map<String, String>> modifiedDBResult = Lists.newArrayList();
        for (Map<String, Object> item : dbResult) {
            Map<String, String> newItem = Maps.newHashMap();
            for (Map.Entry<String, Object> entry : item.entrySet()) {
                newItem.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
            modifiedDBResult.add(newItem);
        }

        assertEquals(modifiedDBResult.size(), expectedValues.size(), "检查数据量是否正确");
        for (Map<String, String> entry : expectedValues) {
            assertTrue(modifiedDBResult.contains(entry), "检查数据库返回值: expected:" + entry + "不在结果集" + modifiedDBResult);
        }
        return this;
    }

    private static void waitForSubmit(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            TestResultLogger.error(e);
        }
    }

    /**
     * 验证数据库的值
     *
     * @param appName        应用的名称
     * @param sql            获取数据的sql
     * @param expectedValues 期望值
     * @return
     */

    public SoftAssertion assertDBValues(AppName appName, String sql,
                                        Map<String, String> expectedValues) {
        return assertDBValues(appName.getName(), sql, expectedValues);
    }

    /**
     * 比较Map<String,String>和Map<String,Object>
     *
     * @param jsonToMap
     * @param expected
     * @param message
     * @return
     */
    public <T> SoftAssertion assertMapEquals(Map<String, String> jsonToMap, Map<String, T> expected,
                                             String message, String... ignoredField) {
        assertEquals(jsonToMap.size(), expected.size(), "检查数量");
        expected.entrySet().stream()
                .filter(entry -> !CollectionsHelper.arrayContains(ignoredField, entry.getKey()))
                .forEach(entry -> assertEquals(String.valueOf(entry.getValue()),
                        String.valueOf(jsonToMap.get(entry.getKey())),
                        message+" 检查字段" + entry.getKey()));
        return this;
    }


}
