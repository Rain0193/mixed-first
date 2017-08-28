package io.ift.automation.data;

import com.alibaba.fastjson.JSON;
import com.dooioo.automation.helpers.*;
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.ift.automation.helpers.CollectionsHelper;
import io.ift.automation.helpers.ExcelHelper;
import io.ift.automation.helpers.JSONHelper;
import io.ift.automation.helpers.MapsHelper;
import io.ift.automation.helpers.ReflectionHelper;
import io.ift.automation.helpers.StringHelper;

/**
 * Created by patrick on 15/3/18.
 *
 * @version $Id$
 */
@SuppressWarnings("unchecked")
public class TestData {

    private transient static final Logger logger = LogManager.getLogger(TestData.class.getName());
    private transient String ignoredFields = StringHelper.EMPTY;
    private transient Map<String, Object> dataHolder;

    public TestData() {
        dataHolder = Maps.newConcurrentMap();
    }

    //not used anymore
    public List<String> ignoredFields() {
        if (StringHelper.isNotEmptyOrNotBlankString(ignoredFields)) {
            this.ignoredFields= this.ignoredFields.replaceAll(",", "-");
            return CollectionsHelper.arrayToList(this.ignoredFields.split("-"));
        } else {
            return Collections.EMPTY_LIST;
        }

    }

    public void addIgnoredField(String fieldName) {
        this.ignoredFields = this.ignoredFields + "-" + fieldName;
    }

    /**
     * 获取字段值:
     * 获取字段顺序:
     * 1. getter方法
     * 2. 字段名称
     * 3. data holder中获取
     * @param names
     * @param <T>
     * @return
     */
    public
    @Nullable
    <T> T get(String... names) {

        for (String name : names) {
            try {
                //there is no true for false in test flow
                T result = (T) ReflectionHelper.invokeGetter(this, name);
                if (result != null) {//to cover get and get field
                    return result;
                }else{
                    result = (T) ReflectionHelper.getFieldValue(this, name);
                    if(result!=null) return result;
                }
            } catch (Exception e) {
                logger.debug("ignore this exception =", e);
            }
        }

        for (String name : names) {
            Object value = dataHolder.get(name);
            if (value != null) return (T) value;
        }

        return null;
    }

    public Map<String,String> getAsMap(String ... keys){
        Map<String,String> result = Maps.newHashMap();
        for (String key : keys) {
            result.put(key,get(key));
        }
        return result;
    }
    /**
     * 设定字段值，首先从自身的成员变量找，如果找到则更新字段值，否则把值放到dataHolder里面
     *
     * @param value
     * @param name
     */
    public TestData set(String name, Object value) {
        try {
            ReflectionHelper.setFieldValue(this, name, value);
            return this;
        } catch (Exception e) {
            logger.debug("ignore this exception =", e);
        }

        dataHolder.put(name, value);
        return this;
    }

    /**
     * 将Map中的值复制到实例中相应的成员变量中
     * @param nameAndValue
     */
    public void set(Map<String,Object> nameAndValue){
        for (Map.Entry<String, Object> entry : nameAndValue.entrySet()) {
            set(entry.getKey(),entry.getValue());
        }
    }

    /**
     * 将TestData方法中值复制过来
     * @param t
     */
    public void set(TestData t){
       DataComposer.copyFrom(t).to(this);
    }

    /**
     * 增加测试数据到data holder里面去，以方便使用
     *
     * @param data
     * @param <T>
     */
    public <T> TestData addData(T data) {
        try {
            dataHolder.putAll(BeanUtils.describe(data));
        } catch (Exception e) {
            logger.debug("failed to add data ={}", data);
        }
        return this;
    }

    /**
     * 将测试数据类转化为Map
     *
     * @return
     */
    public Map<String, String> toMap() {

        return MapsHelper.instanceToMap(this);
    }

    /**
     * hook function after test data is created.
     * for example:
     * once a java bean is initialized, there are fields pricePerUnit, units,
     * and also there is totalAmount of the goods
     * you can override the dataComposeAfter method to set totalAmount = pricePerUnit*units
     * it is invoked by ExcelHelper#convertToObjectArray when using data provider in testng
     *
     */
    public void dataComposeAfter(){}

    public String[] toCSV() {
        String[] csv = new String[2];
        csv[0] = toMap().keySet().stream().filter(item ->
                !"class".equalsIgnoreCase(item)).map(item -> this.getClass().getSimpleName() + '.' + item)
                .reduce((current, next) -> current + ',' + next).get();
        csv[1] = toMap().values().stream().
                filter(item -> {
                    if (item == null) item = "";
                    return !item.contains("class");
                })
                .reduce((current, next) -> current + ',' + next).get();
        return csv;
    }

    /**
     * 转化成JsonString
     * @return
     */
    public String toJsonString() {
        return JSONHelper.toJSON(this).toString();
    }

    public void setIgnoredFields(String ignoredFields) {
        this.ignoredFields = ignoredFields;
    }

    /**
     * 测试数据实例写入到Excel文件中
     * @param testCaseName
     * @param fileName
     */
    public void toXLS(String testCaseName, String fileName) {
        ExcelHelper.createTestCaseData(testCaseName, fileName, this);
    }

    @Override
    public String toString() {
       return JSON.toJSON(this).toString();
    }
}
