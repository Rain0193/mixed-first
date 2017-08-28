package io.ift.automation.commonflows;

import io.ift.automation.commonflows.base.api.AccessTokenResponse;
import io.ift.automation.commonflows.models.EmployeeTestData;
import io.ift.automation.helpers.EnvironmentHelper;
import io.ift.automation.helpers.webdriver.WebDriverHelper;
import io.ift.automation.testscaffold.BaseWebTest;
import io.ift.automation.testscaffold.webtest.webUI.elementloader.internal.HtmlElementsFactory;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.InputBox;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.TreeElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.charset.Charset;

public class DooiooLoginHelperTest extends BaseWebTest {
    private static final Logger logger = LogManager.getLogger(DooiooLoginHelperTest.class.getName());

    @Test
    public void testGetAccessToken() throws Exception {
        String token = DooiooLoginHelper.getAccessToken();
        logger.info("token={}", token);
        Assert.assertNotNull(token);
    }

    @Test
    public void testGetAccessTokenResponse() throws Exception {
        AccessTokenResponse token = DooiooLoginHelper.getAccessTokenResponse();
        logger.info("token={}", token);
        Assert.assertNotNull(token);
    }

    @Test
    public void testGetAccessTokenByUser() throws Exception {
        String token = DooiooLoginHelper.getAccessTokenByUser("110863", "PW_654321");
        logger.info("token={}", token);
        Assert.assertNotNull(token);
    }

    @Test
    public void testLogout() throws Exception {
        DooiooLoginHelper.login("110863", "PW_123456", driver);
        driver.get("http://ysjy.dooioo.org/projects/editProject/249");
        TreeElement element = HtmlElementsFactory.createHtmlElementInstance(TreeElement.class,
                                                                            WebDriverHelper
                                                                                .findElement(driver, By.xpath("//duitree[@selected-data='projectDirector']")));
        element.selectByNameAndOrgName("陆敏-陆家嘴公馆B组");
        DooiooLoginHelper.logout(driver);
        WebDriverHelper.get(driver, EnvironmentHelper.getDomainUrl());
    }

    @Test
    public void testGetIntegrationLoginHeader() throws Exception {
        System.out.println(System.getProperty("file.encoding"));
        System.out.println(Charset.defaultCharset());
    }

    @Test
    public void testGetIntegrationLoginPassword() throws Exception {
        String password = DooiooLoginHelper.getIntegrationLoginPassword("110863", "PW_123456");
        Assert.assertNotNull(password);
    }

    @Test
    public void testLoginBySuperPassword() throws Exception {
        DooiooLoginHelper.loginBySuperPassword(new EmployeeTestData("90000"), driver);
    }

    @Test
    public void test_vip(){
        WebDriverHelper.get(driver,"http://vip.dooioo.org/u/TJUIXOCHCW");
        WebDriverHelper.get(driver,"http://vip.dooioo.org/vip/house/11010410281834B6A108858D5C3B2984/edit");
        InputBox priceBox = HtmlElementsFactory.createHtmlElementInstance(InputBox.class,
                                                                          WebDriverHelper.findElement(driver,By.xpath("//input[@ng-model='model.house.handPrice']")));
        priceBox.clearAndInput("10009");
        System.out.println(driver);
    }

}
