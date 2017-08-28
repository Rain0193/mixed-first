package io.ift.automation.testscaffold.webtest.webUI.elementloader;

import io.ift.automation.data.TestData;
import io.ift.automation.testscaffold.webtest.webUI.elementloader.internal.HtmlElementsFactory;
import io.ift.automation.testscaffold.webtest.webUI.testpages.BaiDuHomeBasePage;
import io.ift.automation.testscaffold.webtest.webUI.testpages.BaiDuHomePageXML;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.annotation.Nullable;

public class HtmlElementsFactoryTest {

    @Test
    public void testCreatePageObjectInstance() throws Exception {
       WebDriver driver = new FirefoxDriver();
       BaiDuHomeBasePage page = HtmlElementsFactory.
               createPageObjectInstance(BaiDuHomeBasePage.class, driver);
        Assert.assertNotNull(page);
        driver.quit();
    }

    @Test
    public void testCreatePageObjectInstance_2() throws Exception {
        WebDriver driver = new FirefoxDriver();
        BaiDuHomePageXML page = HtmlElementsFactory.
                createPageObjectInstance(BaiDuHomePageXML.class, driver);
        Assert.assertNotNull(page);
        driver.quit();
    }

    @Test
    public void testCreateHtmlElementObject() throws Exception {

    }

    @Test
    public void testCreatePageObjectInstance1() throws Exception {
        WebDriver driver = new FirefoxDriver();
        BaiDuHomeBasePage page = HtmlElementsFactory.
                createPageObjectInstance(BaiDuHomeBasePage.class, driver, new TestData() {
                    @Nullable
                    @Override
                    public String get(String... names) {
                        return null;
                    }
                });
        Assert.assertNotNull(page);
        driver.quit();
    }

}