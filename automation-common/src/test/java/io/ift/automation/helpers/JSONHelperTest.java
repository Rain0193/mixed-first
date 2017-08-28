package io.ift.automation.helpers;


import com.alibaba.fastjson.*;
import io.ift.automation.assertion.SoftAssertion;
import io.ift.automation.helpers.database.SpringJdbcTemplateUtils;
import io.ift.automation.helpers.testmodel.TestCase_Test;
import io.ift.automation.testscaffold.apitest.BaseJsonEntity;
import com.google.common.collect.Lists;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.*;

public class JSONHelperTest {

    private String jsonString = "{\n" +
            "    \"store\": {\n" +
            "        \"book\": [\n" +
            "            {\n" +
            "                \"category\": \"reference\",\n" +
            "                \"author\": \"Nigel Rees\",\n" +
            "                \"title\": \"Sayings of the Century\",\n" +
            "                \"price\": 8.95\n" +
            "            },\n" +
            "            {\n" +
            "                \"category\": \"fiction\",\n" +
            "                \"author\": \"Evelyn Waugh\",\n" +
            "                \"title\": \"Sword of Honour\",\n" +
            "                \"price\": 12.99\n" +
            "            },\n" +
            "            {\n" +
            "                \"category\": \"fiction\",\n" +
            "                \"author\": \"Herman Melville\",\n" +
            "                \"title\": \"Moby Dick\",\n" +
            "                \"isbn\": \"0-553-21311-3\",\n" +
            "                \"price\": 8.99\n" +
            "            },\n" +
            "            {\n" +
            "                \"category\": \"fiction\",\n" +
            "                \"author\": \"J. R. R. Tolkien\",\n" +
            "                \"title\": \"The Lord of the Rings\",\n" +
            "                \"isbn\": \"0-395-19395-8\",\n" +
            "                \"price\": 22.99\n" +
            "            }\n" +
            "        ],\n" +
            "        \"bicycle\": {\n" +
            "            \"color\": \"red\",\n" +
            "            \"price\": 19.95\n" +
            "        }\n" +
            "    },\n" +
            "    \"expensive\": 10\n" +
            "}";


    @Test
    public void testToBean() throws Exception {

        String test = JSONHelper.toBean("{'test':'abcd'}",String.class);
        assertNotNull(test);
    }

    @Test
    public  void testToList(){

        String temp = "[{\"testId\":\"123\",\"error\":\"test\"}]";
        List<TestCase_Test> list = JSONHelper.toList(temp, new TypeReference<List<TestCase_Test>>() {
        });
        Assert.assertEquals(list.size(), 1);
    }

    @Test
    public  void testToList_NULL(){

        String temp = "[{\"testId\":\"123\",\"error\":\"test\"},{\"testId\":\"123\",\"error\":\"test\"}]";
        List list = JSONHelper.toList(temp,new TypeReference<List<String>>(){});
        Assert.assertEquals(list.size(),2);
    }
    @Test
    public void testGetValue_ForList() throws Exception {
        //getting JSONArray as List
        List<String> value = (List<String>) JSONHelper.getValue(jsonString,".store.book[*].author");
        System.out.println(value);
        assertThat(value.size(), is(4));
    }


    @Test
    public void testGetValue_ForList_1() throws Exception {
        //getting JSONArray as List
        List<Map<String,String>> value = (List<Map<String,String>>) JSONHelper.getValue(jsonString,"$..book");
        System.out.println(value);
        assertThat(value.size(), is(4));
    }

    @Test
    public void testGetValue_ForListMap() throws Exception {
        //getting JSONArray as List
        List<Map> value = (List<Map>) JSONHelper.getValue(jsonString,".store.book[*]");
        System.out.println(value);
        assertThat(value.size(), is(4));
    }

    @Test
    public void testGetValue_ForValueMap() throws Exception {
        //getting JSONArray as List
        List<Map> value = (List<Map>) JSONHelper.getValue(jsonString,".store.*");
        System.out.println(value);
        assertThat(value.size(), is(2));
    }


    @Test
    public void testGetValue_ForValueFromRoot() throws Exception {
        //getting JSONArray as List
        List<Map<String,String>> value = (List<Map<String,String>>) JSONHelper.getValue(jsonString,"$.*");
        System.out.println(value);
        assertThat(value.size(), is(2));
    }


    @Test
    public void testGetValue_AllValues() throws Exception {
        //getting JSONArray as List
        List<String> value = (List<String>) JSONHelper.getValue(jsonString,"$..author");
        System.out.println(value);
        assertThat(value.size(), is(4));
    }

    @Test
    public void testGetValue_GetByIndex(){
        Map<String,String> value = JSONHelper.getArrayValueToMap(jsonString,"$..book[2]");
        System.out.println(value);
        assertThat(value.size(),greaterThan(0));
    }

    @Test
    public void testGetValue_GetListByIndex(){
        List<Map<String,String>> value = (List<Map<String, String>>) JSONHelper.getValue(jsonString,"$..book[2]");
        System.out.println(value);
        assertThat(value.size(),greaterThan(0));
    }

    @Test
    public void testGetValue_GetByPath(){
        String value = (String) JSONHelper.getValue(jsonString,"$.store.book[2].title");
        System.out.println(value);
        assertThat(value.length(),greaterThan(0));
    }

    @Test
    public void testGetValue_GetByIndexSlice(){
        List<String> value = (List<String>) JSONHelper.getValue(jsonString,"$.store.book[1:2]");
        System.out.println(value);
        assertThat(value.size(),greaterThan(0));
    }

    @Test
    public void testGetValue_GetBySlice(){
        List<String> value = (List<String>) JSONHelper.getValue(jsonString,"$.store.book[-1:]");
        System.out.println(value);
        assertThat(value.size(),greaterThan(0));
    }

    @Test
    public void testGetValue_GetBySliceIndex(){
        List<Map> value = (List<Map>) JSONHelper.getValue(jsonString,"$.store.book[-2:]");
        System.out.println(value);
        assertThat(value.size(),greaterThan(0));
    }

    @Test
    public void testGetValue_GetByAttribute(){
        List<Map> value = (List<Map>) JSONHelper.getValue(jsonString,"$.store.book[?(@.price < 10)]");
        System.out.println(value);
        assertThat(value.size(),greaterThan(0));
    }

    @Test
    public void testGetValue_GetByExpress(){
        List<Map> value = (List<Map>) JSONHelper.getValue(jsonString,"$..book[?(@.price <= $['expensive'])]");
        System.out.println(value);
        assertThat(value.size(),greaterThan(0));
    }

    @Test
    public void testGetValue_GetAll(){
        List<Map> value = (List<Map>) JSONHelper.getValue(jsonString,"$..*");
        System.out.println(value);
        assertThat(value.size(),greaterThan(0));
    }
//
//    $.store.book[*].author	The authors of all books
//    $..author	All authors
//    $.store.*	All things, both books and bicycles
//    $.store..price	The price of everything
//    $..book[2]	The third book
//    $..book[(@.length-1)]	The last book
//    $..book[0,1]	The first two books
//    $..book[:2]	All books from index 0 (inclusive) until index 2 (exclusive)
//    $..book[1:2]	All books from index 1 (inclusive) until index 2 (exclusive)
//    $..book[-2:]	Last two books
//    $..book[2:]	Book number two from tail
//    $..book[?(@.isbn)]	All books with an ISBN number
//    $.store.book[?(@.price < 10)]	All books in store cheaper than 10
//    $..book[?(@.price <= $['expensive'])]	All books in store that are not "expensive"
//    $..book[?(@.author =~ /.*REES/i)]	All books matching regex (ignore case)
//    $..*	Give me every thing
    @Test
    public void testMapToJSONString(){
        Map map = new HashMap<>();
        map.put("test","test2314");
        map.put("testlist", Lists.newArrayList("acd","bbdsc","llodf"));
        System.out.println(JSONHelper.toJSONString(map));
    }

    @Test
    public void testJavaBeanToJSONString(){
        DummyUser user = new DummyUser();
        user.setAge("1234");
        user.setName("name");
        System.out.println(JSONHelper.toJSONString(user));
    }


    @Test
    public void testJSONStringToJavaBean(){
        String jsonString ="{\"age\":\"1234\",\"name\":\"name\"}";
        DummyUser user = JSONHelper.toBean(jsonString, DummyUser.class);
        Assert.assertEquals(user.getAge(),"1234");
        Assert.assertEquals(user.getName(),"name");
    }

    public static class DummyUser{
        private String name;
        private String age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }


    @Test
    public void useBaseJsonEntity(){
        String sql = "select * from T_BMS_ORDER where id = 100";
        //得到数据里面的字段
        BMSOrder expectedOrder = SpringJdbcTemplateUtils.DEFAULT().queryForObject(sql, BMSOrder.class);
        System.out.println(JSONHelper.toJSONString(expectedOrder));
        //mockJsonResponse 代表了responseEntity.getBody().toString()
        String mockJsonResponse = "{\"caseId\":954,\"id\":100,\"receiptMoney\":\"2.000000\",\"receiptTime\":1315584000000}";

        BMSOrder actualOrder = JSONHelper.toBean(mockJsonResponse,BMSOrder.class);

        Assert.assertEquals(actualOrder,expectedOrder,"比较返回数据是否和数据库中一致");
        Assert.assertTrue(actualOrder.equalsByPassIgnoreFields(expectedOrder,"caseId","id"),
                "比较返回数据是否和数据库中一致");
    }

    public static class BMSOrder extends BaseJsonEntity {
        private Integer id;
        private Integer caseId;
        private String receiptMoney;
        private Date receiptTime;


        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getCaseId() {
            return caseId;
        }

        public void setCaseId(Integer caseId) {
            this.caseId = caseId;
        }

        public String getReceiptMoney() {
            return receiptMoney;
        }

        public void setReceiptMoney(String receiptMoney) {
            this.receiptMoney = receiptMoney;
        }

        public Date getReceiptTime() {
            return receiptTime;
        }

        public void setReceiptTime(Date receiptTime) {
            this.receiptTime = receiptTime;
        }
    }

    @Test
    public void test_listMap(){
        String data ="[{\"phoneType\":\"微信\",\"phoneNumber\":\"009090\"},{\"phoneType\":\"微信\",\"phoneNumber\":\"009090\"}]";
        String data1 ="{\"key\":\"value\"}";
        JSONArray a= JSONHelper.parseArray(data);
        List<Map<String,String>> source =JSONHelper.toList(data,new TypeReference<List<Map<String,String>>>(){});
        try{
            List<Map<String,String>> source1 =JSONHelper.toList(data1,new TypeReference<List<Map<String,String>>>(){});
        }catch (JSONException e){
            JSONObject source2=JSONHelper.parseObject(data1);
            System.out.println(source2);
        }
        System.out.println(source);

    }

    @Test
    public void test_getStringValue(){
        String data ="{\"token\":\"11234\"}";
        Assert.assertEquals(JSONHelper.getStringValue(data, "token"), "11234");
    }


    @Test
    public void test_getResponseStringValue(){
        ResponseEntity data = new ResponseEntity("{\"token\":\"11234\"}",HttpStatus.ACCEPTED);
        Assert.assertEquals(JSONHelper.getStringValue(data,"token"),"11234");
    }


    @Test
    public void test_getValue(){
        ResponseEntity data = new ResponseEntity("{\"token\":\"11234\"}",HttpStatus.ACCEPTED);
        Assert.assertEquals(JSONHelper.getValue(data, "token"),"11234");
    }

    @Test
    public void test_toBean(){
        ResponseEntity data = new ResponseEntity("{\"token\":\"11234\"}",HttpStatus.ACCEPTED);
        Assert.assertEquals(JSONHelper.toBean(data, String.class),"{\"token\":\"11234\"}");
    }

    @Test
    public void test_map(){
        String result="{\n" +
                "  \"past\" : 157,\n" +
                "  \"recent\" : {\n" +
                "    \"2016-01-06\" : 7,\n" +
                "    \"2016-01-07\" : 4,\n" +
                "    \"2016-01-08\" : 1,\n" +
                "    \"2016-01-09\" : 1,\n" +
                "    \"2016-01-10\" : 10\n" +
                "  },\n" +
                "  \"future\" : 5\n" +
                "}";
        Map<String,String> map = (Map<String, String>) JSONHelper.getValue(result,"recent");
        System.out.println(map);
    }
    @Test
    public void test_tovalue() throws IOException {
        String jsonFile="{" +
                "  \"employee\": {\n" +
                "    \"userCode\": 108951,\n" +
                "    \"password\": \"dooioo&!&!\",\n" +
                "    \"companyId\": 1,\n" +
                "    \"orgId\": 21467,\n" +
                "    \"userName\": \"顾佳佳\",\n" +
                "    \"sex\": \"女\",\n" +
                "    \"mobilePhone\": \"13300108951\",\n" +
                "    \"alternatePhone\": \"13636512592\",\n" +
                "    \"showedPhone\": \"18721690086\",\n" +
                "    \"orgName\": \"房客源开发部\",\n" +
                "    \"positionName\": \"测试工程师\",\n" +
                "    \"leaveDate\": null,\n" +
                "    \"joinDate\": 1416758400000,\n" +
                "    \"latestJoinDate\": 1416758400000,\n" +
                "    \"status\": \"正式\",\n" +
                "    \"orgLongCode\": \"12020001/150103155238/12020006/140214094131/140408151141\"\n" +
                "  },\n" +
                "  \"token\": {\n" +
                "    \"token\": 1451977379439108951\n" +
                "  }\n" +
                "}";
//        String result = JSON.parseObject(jsonFile.toString(), String.class);
//        System.out.println(jsonFile.startsWith("\""));
//        System.out.println(JSON.toJSONString(jsonFile, SerializerFeature.WriteMapNullValue).startsWith("\""));
//        String result1=  JSON.parseObject(JSON.toJSONString(jsonFile), String.class);

//        ObjectMapper ob = new ObjectMapper();
//        JsonParser parser = new JsonFactory().createParser(jsonFile);
//        JsonNode rootNode= ob.readValue(jsonFile, JsonNode.class);
//        JsonNode tree= ob.readTree(parser);
//        System.out.println(rootNode);
//        System.out.println(tree);
//        JSONObject o = JSON.parseObject(jsonFile);
//        System.out.println(o.toJSONString(SerializerFeature.WriteMapNullValue));
        String result="{\"suppliers\":[{\"supplierId\":2544,\"supplierCode\":\"DY-0319\",\"supplierName\":\"上海霆晟智能科技有限公司\"},{\"supplierId\":2543,\"supplierCode\":\"DY-0318\",\"supplierName\":\"上海曲直装饰工程有限公司\"},{\"supplierId\":2542,\"supplierCode\":\"DY-0317\",\"supplierName\":\"上海锋硕标识工程有限公司\"},{\"supplierId\":2541,\"supplierCode\":\"DY-0316\",\"supplierName\":\"上海汇公电子科技有限公司\"},{\"supplierId\":2540,\"supplierCode\":\"DY-0315\",\"supplierName\":\"上海康沁环保环境技术有限公司\"},{\"supplierId\":2539,\"supplierCode\":null,\"supplierName\":\"上海依禄铧服饰有限公司\"},{\"supplierId\":2538,\"supplierCode\":null,\"supplierName\":\"上海瑾瑞速递有限公司\"},{\"supplierId\":2537,\"supplierCode\":null,\"supplierName\":\"上海立乡商贸有限公司\"},{\"supplierId\":2536,\"supplierCode\":null,\"supplierName\":\"上海声通信息科技股份有限公司\"},{\"supplierId\":2535,\"supplierCode\":\"DY-0268\",\"supplierName\":\"上海一简智能科技有限公司\"},{\"supplierId\":2533,\"supplierCode\":\"DY-0311\",\"supplierName\":\"上海哥德堡建筑安装工程有限公司\"},{\"supplierId\":2532,\"supplierCode\":null,\"supplierName\":\"中国电信股份有限公司上海分公司\"},{\"supplierId\":2531,\"supplierCode\":\"DY-0306\",\"supplierName\":\"上海浦起机电有限公司\"},{\"supplierId\":2530,\"supplierCode\":null,\"supplierName\":\"上海华映文化传媒股份有限公司\"},{\"supplierId\":2529,\"supplierCode\":null,\"supplierName\":\"上海清嘉机械设备租赁有限公司\"},{\"supplierId\":2528,\"supplierCode\":\"DY-0305\",\"supplierName\":\"上海贝奥建筑装饰工程有限公司\"},{\"supplierId\":2526,\"supplierCode\":\"DY-0304\",\"supplierName\":\"上海鸿登机电设备有限公司\"},{\"supplierId\":2525,\"supplierCode\":null,\"supplierName\":\"上海晶维商务咨询有限公司\"},{\"supplierId\":2524,\"supplierCode\":\"DY-0301\",\"supplierName\":\"上海擎信实业有限公司\"},{\"supplierId\":2523,\"supplierCode\":null,\"supplierName\":\"上海颜博办公用品有限公司\"}],\"message\":\"\",\"status\":\"ok\"}";
        JSONObject a = JSONHelper.parseObject(result);
        System.out.println(a);
        Map map = new HashMap();
        map.put("key1","test1");
        map.put("key2","test2");
        map.put("key3", "test3");
        new SoftAssertion().assertDBValues("ky", "select * from inquiry", JSONHelper.toBean(result, Map.class));
        new SoftAssertion().assertJson(result,JSONHelper.toJSONString(map),"testresukt").getFinalResult();
    }

    @Test
    public void test_replace(){
        String result ="{\n" +
                "  “key”：“value”\n" +
                "}";
        try{
            JSONHelper.parse(result);
        }catch (Exception e){
            System.out.println(e);
        }
        result=result.replaceAll("”","\"").replaceAll("“","\"").replaceAll("：",":");

        System.out.println("".replaceAll("”","\"").replaceAll("“","\"").replaceAll("：",":"));
        try{
            JSONHelper.parse(result);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void test_json_array(){
        String json="{\"suppliers\":[{\"supplierId\":2544,\"supplierCode\":\"DY-0319\",\"supplierName\":\"上海霆晟智能科技有限公司\"},{\"supplierId\":2543,\"supplierCode\":\"DY-0318\",\"supplierName\":\"上海曲直装饰工程有限公司\"},{\"supplierId\":2542,\"supplierCode\":\"DY-0317\",\"supplierName\":\"上海锋硕标识工程有限公司\"},{\"supplierId\":2541,\"supplierCode\":\"DY-0316\",\"supplierName\":\"上海汇公电子科技有限公司\"},{\"supplierId\":2540,\"supplierCode\":\"DY-0315\",\"supplierName\":\"上海康沁环保环境技术有限公司\"},{\"supplierId\":2539,\"supplierCode\":null,\"supplierName\":\"上海依禄铧服饰有限公司\"},{\"supplierId\":2538,\"supplierCode\":null,\"supplierName\":\"上海瑾瑞速递有限公司\"},{\"supplierId\":2537,\"supplierCode\":null,\"supplierName\":\"上海立乡商贸有限公司\"},{\"supplierId\":2536,\"supplierCode\":null,\"supplierName\":\"上海声通信息科技股份有限公司\"},{\"supplierId\":2535,\"supplierCode\":\"DY-0268\",\"supplierName\":\"上海一简智能科技有限公司\"},{\"supplierId\":2533,\"supplierCode\":\"DY-0311\",\"supplierName\":\"上海哥德堡建筑安装工程有限公司\"},{\"supplierId\":2532,\"supplierCode\":null,\"supplierName\":\"中国电信股份有限公司上海分公司\"},{\"supplierId\":2531,\"supplierCode\":\"DY-0306\",\"supplierName\":\"上海浦起机电有限公司\"},{\"supplierId\":2530,\"supplierCode\":null,\"supplierName\":\"上海华映文化传媒股份有限公司\"},{\"supplierId\":2529,\"supplierCode\":null,\"supplierName\":\"上海清嘉机械设备租赁有限公司\"},{\"supplierId\":2528,\"supplierCode\":\"DY-0305\",\"supplierName\":\"上海贝奥建筑装饰工程有限公司\"},{\"supplierId\":2526,\"supplierCode\":\"DY-0304\",\"supplierName\":\"上海鸿登机电设备有限公司\"},{\"supplierId\":2525,\"supplierCode\":null,\"supplierName\":\"上海晶维商务咨询有限公司\"},{\"supplierId\":2524,\"supplierCode\":\"DY-0301\",\"supplierName\":\"上海擎信实业有限公司\"},{\"supplierId\":2523,\"supplierCode\":null,\"supplierName\":\"上海颜博办公用品有限公司\"}],\"message\":\"\",\"status\":\"ok\"}";
        Object result = JSONHelper.getValue(json,"suppliers");
        Map<String,String> result1 = JSONHelper.getArrayValueToMap(json, "suppliers");
        List<Map<String,String>> result2 = (List<Map<String, String>>) JSONHelper.getValue(json, "suppliers");
        System.out.println(result);
        System.out.println(result1);
        System.out.println(result2);
    }

}
