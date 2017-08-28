package io.ift.automation.data;

import io.ift.automation.helpers.testmodel.TestCase_Test;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by patrick on 15/9/28.
 */
public class TestDataBuilderTest {

    @Test
    public void testStart() throws Exception {
        TestDataBuilder d = TestDataBuilder.defaultDataType();
        Assert.assertNotNull(d);
    }

    @Test
    public void testStart1() throws Exception {
        TestDataBuilder d = TestDataBuilder.forDataType(TestCase_Test.class);
        Assert.assertNotNull(d);
    }

    @Test
    public void testData() throws Exception {
        TestDataBuilder<TestCase_Test> d = TestDataBuilder.forDataType(TestCase_Test.class);
        d.add("description", "TESTST");
        TestCase_Test a = d.build();
        Assert.assertEquals(a.getDescription(), "TESTST");
    }

    @Test
    public void testBuild() throws Exception {
//        TestDataBuilder t = new TestDataBuilder();
        TestDataBuilder d = TestDataBuilder.defaultDataType();
        TestData data = d.add("description", "TESTST").build();
        Assert.assertEquals(data.get("description"), "TESTST");
    }
}
