package io.ift.automation.assertion;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by patrick on 15/8/20.
 * 构建期望值,目的是为了让代码可读性更加好
 */
public class ExpectedResultSpec {

    private Map<String,String> expectedResultSpec;

    public ExpectedResultSpec() {
        expectedResultSpec = Maps.newHashMap();
    }

    public static ExpectedResultSpec build(){
        return new ExpectedResultSpec();
    }

    /**
     *
     * @param key  期望值的字段名
     * @param value 期望值字段的值
     * @return
     */
    public ExpectedResultSpec addExpectation(String key,String value){
        expectedResultSpec.put(key,value);
        return this;
    }

    /**
     * 结束所有期望值的构建
     * @return
     */
    public Map<String,String> end(){
        return expectedResultSpec;
    }

    public String get(String key){
        return expectedResultSpec.get(key);
    }

    public Map<String, String> getExpectedResultSpec() {
        return expectedResultSpec;
    }

    public void setExpectedResultSpec(Map<String, String> expectedResultSpec) {
        this.expectedResultSpec = expectedResultSpec;
    }
}
