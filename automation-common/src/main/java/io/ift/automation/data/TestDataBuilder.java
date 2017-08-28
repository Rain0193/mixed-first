package io.ift.automation.data;

/**
 * Created by patrick on 15/9/28.
 */
public class TestDataBuilder<T extends TestData> {
    private T testData;

    public TestDataBuilder(Class<T> clazz) {
        try {
            this.testData = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            //ignore this error
            throw new RuntimeException(e);
        }
    }
    
    public static TestDataBuilder forDataType(Class clazz) {
        return new TestDataBuilder(clazz);
    }

    public static TestDataBuilder defaultDataType() {
        return new TestDataBuilder(TestData.class);
    }

    public TestDataBuilder add(String key, String value) {
        this.testData.set(key, value);
        return this;
    }

    public T build() {
        this.testData.dataComposeAfter();
        return this.testData;
    }

    public T changeAfterBuild(String key,String value){
       return change(key,value);
    }

    public T changeBeforeBuild(String key,String value){
        return change(key,value);
    }

    private T change(String key,String value){
        this.testData.set(key,value);
        return this.testData;
    }

}
