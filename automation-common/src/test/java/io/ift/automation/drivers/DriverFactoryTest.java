package io.ift.automation.drivers;

import io.ift.automation.helpers.webdriver.WebDriverHelper;
import io.ift.automation.testscaffold.BaseWebTest;
import io.ift.automation.logging.TestResultLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;


public class DriverFactoryTest extends BaseWebTest {

    @Test
    public void testCreateWebDriver() throws Exception {
        TestResultLogger.log("Current Thread Id is " + Thread.currentThread().getId());
        TestResultLogger.log("current webdriver is " + driver.hashCode() + driver.toString());
        driver.get("http://www.baidu.com");
        TestResultLogger.log(driver.getCurrentUrl());
        TestResultLogger.log(driver.getTitle());
        driver.findElement(By.id("kw")).sendKeys("test");
        driver.findElement(By.id("su")).click();
    }

    @Test
    public void testGoogle(){
        TestResultLogger.log("Current Thread Id is " + Thread.currentThread().getId());
        TestResultLogger.log("current webdriver is " + driver.hashCode() + driver.toString());
        driver.get("http://www.baidu.com");
        TestResultLogger.log(driver.getCurrentUrl());
        System.out.println(driver.getTitle());
        WebDriverHelper.input(driver,By.name("q"),"test");
        WebDriverHelper.click(driver, By.cssSelector(".btn.btn-fl"));
    }

}
