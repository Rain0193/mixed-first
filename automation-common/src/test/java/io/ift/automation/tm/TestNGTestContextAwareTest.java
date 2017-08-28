package io.ift.automation.tm;

import io.ift.automation.tm.testmodel.TestCase;
import io.ift.automation.tm.testmodel.TestSuite;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by patrick on 15/11/18.
 */
public class TestNGTestContextAwareTest {

    @Test
    public void testGenerateTestResult() throws Exception {
        TestNGTestContextAware a = new TestNGTestContextAware();
        TestNGTestContextAware.VueData data = new TestNGTestContextAware.VueData();
        TestSuite ts1 = new TestSuite();
        ts1.setSuiteName("test1");
        TestCase t1 = new TestCase();
        t1.setTestClassName("test1");
        t1.setTestDescription("test test");
        t1.setTestMethodName("testing");
        t1.setStatus(1);
        t1.addScreenShots("test.jpg");
        t1.getLogs().add("this is testlogs");
        ts1.addTestCase(t1);
        TestSuite ts2 = new TestSuite();
        ts2.setSuiteName("test2");
        ts2.addTestCase(t1);
        TestSuite ts3 = new TestSuite();
        ts3.setSuiteName("test3");
        ts3.addTestCase(t1);

        data.getData().testsuites.add(ts1.generateTemplateData());
        data.getData().testsuites.add(ts2.generateTemplateData());
        data.getData().testsuites.add(ts3.generateTemplateData());

//        System.out.println(String.format("new Vue(%s);",JSONHelper.toJSONString(data)));
//        a.generateTestResult(data);

    }
}
