package io.ift.automation.helpers.webdriver;

import io.ift.automation.drivers.DriverFactory;
import io.ift.automation.testscaffold.BaseWebCurrencyTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class WebDriverHelperTest extends BaseWebCurrencyTest {

    @Test
    public void testClick() throws Exception {
        WebDriver driver = DriverFactory.get();
        driver.get("http://www.baidu.com");

        System.out.println(WebDriverHelper.present(driver, By.id("su")));
        WebDriverHelper.input(driver,By.id("kw"),"Testing");
        WebDriverHelper.click(driver, By.id("su"));
        driver.quit();
    }

    @Test
    public void testClick1() throws Exception {
        WebDriver driver = DriverFactory.get();
        driver.get("http://www.baidu.com");
        int count =0;
        String value ="";
        while(count<10&&!"complete".equalsIgnoreCase(value)){
            value = WebDriverHelper.getJSEvalValue(driver,"return document.readyState;");
            Thread.sleep(3000L);
            count++;
        }

        System.out.println(value);
    }

    @Test
    public void test_openreport(){
        WebDriverHelper.openReport();
    }
}
