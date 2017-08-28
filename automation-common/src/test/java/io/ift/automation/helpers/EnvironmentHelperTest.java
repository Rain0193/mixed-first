package io.ift.automation.helpers;

import io.ift.automation.AppName;
import io.ift.automation.logging.TestResultLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EnvironmentHelperTest {

    private static final Logger logger = LogManager.getLogger(EnvironmentHelperTest.class.getName());

    @Test
    public void testGetTargetEnvironmentName() throws Exception {
        Assert.assertTrue(EnvironmentHelper.getTargetEnvironmentName().contains("integration"));
    }

    @Test
    public void testGetDbURL() throws Exception {
        Assert.assertTrue(EnvironmentHelper.getDbURL().contains("sql"));
    }

    @Test
    public void testGetDbDriver() throws Exception {
        Assert.assertTrue(EnvironmentHelper.getDbDriver().contains("SQLServerDriver"));
    }

    @Test
    public void testGetDbUserName() throws Exception {
        Assert.assertTrue(EnvironmentHelper.getDbUserName().contains("k"));
    }

    @Test
    public void testGetDbPassword() throws Exception {
        logger.info(EnvironmentHelper.getDbPassword());
        Assert.assertTrue(EnvironmentHelper.getDbPassword().contains("1"));
    }

    @Test
    public void testGetSerivceRootPath() throws Exception {
        logger.info(EnvironmentHelper.getServiceRootPath());
        Assert.assertTrue(EnvironmentHelper.getServiceRootPath().contains("dooioo"));
    }

    @Test
    public void testGetDomainPath() throws Exception {
        logger.info(EnvironmentHelper.getServiceRootPath());
        System.out.println(EnvironmentHelper.get("zichan").getDomainUrl());
        Assert.assertTrue(EnvironmentHelper.get("zichan").getDomainUrl().contains("http://zichan.dooioo"));
    }

    @Test
    public void buildEnviromentBySystemProperties(){
        TestResultLogger.log("Target" + System.getProperty("target.environment"));
        System.out.println(EnvironmentHelper.get(AppName.ZICHAN.toString()).getDomainUrl());
    }

    @Test
    public void testDefaultDomainPath() throws Exception {
        logger.info(EnvironmentHelper.getServiceRootPath());
        System.out.println(EnvironmentHelper.getDomainUrl());
        Assert.assertTrue(EnvironmentHelper.get("ZICHAN").getDomainUrl().contains("http://zichan.dooioo"));
    }
}
