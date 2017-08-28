package io.ift.automation.helpers;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import static org.testng.Assert.*;

/**
 * Created by patrick on 15/8/20.
 */
public class PropertiesHelperTest {

    @Test
    public void testGetSystemFirstProperty() throws Exception {
        String TARGET_ENVIRONMENT_KEY="target.environment";
        String name = PropertiesHelper.getSystemFirstProperty(TARGET_ENVIRONMENT_KEY, "integration");
        Assert.assertEquals(name, "integration");

    }

    @Test
    public void test_GetPassword(){
        String testerUserCode = PropertiesHelper.getProperty("TESTER_USER_CODE");
        System.out.println(testerUserCode);
    }

    @Test
    public void test_GetOpenBrowser(){
        String testerUserCode = PropertiesHelper.getProperty("openbrowser");
        System.out.println(testerUserCode);
    }
}
