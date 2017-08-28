package io.ift.automation.helpers;

import io.ift.automation.helpers.testmodel.TestCase_Test;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class MapsHelperTest {

    @Test
    public void testPut() throws Exception {
        TestCase_Test testCaseTest = new TestCase_Test();
        testCaseTest.setDescription("testing");
        testCaseTest.setError("error");
        testCaseTest.setTestId("errorNo");
        Map<String,List<TestCase_Test>> map = Maps.newHashMap();
        MapsHelper.put(map,"test1234",testCaseTest);
        Assert.assertEquals(map.get("test1234").get(0), testCaseTest);
        MapsHelper.put(map, "test1234", testCaseTest);
        Assert.assertEquals(map.get("test1234").get(1), testCaseTest);
        Assert.assertEquals(map.get("test1234").size(), 2);
    }

    @Test
    public void testInstanceToMap() throws Exception {
        TestCase_Test testCaseTest = new TestCase_Test();
        testCaseTest.setDescription("testing");
        testCaseTest.setError("error");
        testCaseTest.setTestId("errorNo");
        Map<String,String> map = testCaseTest.toMap();
        System.out.println(map);
        System.out.println(testCaseTest.toMap());
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(String.format("%s=%s",entry.getKey(),entry.getValue()));
        }
        Assert.assertTrue(map.size() > 0);
    }


    @Test
    public void testGetMultiple_varargs() throws Exception {
        TestCase_Test testCaseTest = new TestCase_Test();
        testCaseTest.setDescription("testing");
        testCaseTest.setError("error");
        testCaseTest.setTestId("errorNo");
        Map<String, String> map = testCaseTest.toMap();
        Map<String,String> result =MapsHelper.getMultiple(map,"description","testId");
        Assert.assertTrue(result.size()==2);

    }

    @Test
    public void testGetMultiple_list() throws Exception {
        TestCase_Test testCaseTest = new TestCase_Test();
        testCaseTest.setDescription("testing");
        testCaseTest.setError("error");
        testCaseTest.setTestId("errorNo");
        Map<String, String> map = testCaseTest.toMap();
        Map<String,String> result =MapsHelper.getMultiple(map, Lists.newArrayList("description","testId"));
        Assert.assertTrue(result.size()==2);
    }

    @Test
    public void testGetMultiple_nullkeys() throws Exception {
        TestCase_Test testCaseTest = new TestCase_Test();
        testCaseTest.setDescription("testing");
        testCaseTest.setError("error");
        testCaseTest.setTestId("errorNo");
        Map<String, String> map = testCaseTest.toMap();
        Map<String,String> result =MapsHelper.getMultiple(map);
        Assert.assertTrue(result.size()>2);
    }

    @Test
    public void testGetNotNullObject() throws Exception {
        TestCase_Test testCaseTest = new TestCase_Test();
        testCaseTest.setDescription("testing");
        testCaseTest.setError("error");
        testCaseTest.setTestId("errorNo");
        Map<String, String> map = testCaseTest.toMap();
        try {
            MapsHelper.getNotNullObject(map, "testingdsd", "testdandgdsa is not found");
        }catch (RuntimeException e){
            Assert.assertEquals(e.getMessage(),"testdandgdsa is not found");
        }

        Assert.assertEquals(MapsHelper.getNotNullObject(map,"error","error is not found"),"error");

    }
}
