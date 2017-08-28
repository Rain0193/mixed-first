package io.ift.automation.drivers;

import io.ift.automation.testscaffold.BaseWebTest;
import io.ift.automation.logging.TestResultLogger;
import org.openqa.selenium.By;
import org.testng.annotations.Test;


public class DriverFactoryTest2 extends BaseWebTest {

    @Test
    public void testCreateWebDriver2() throws Exception {
        TestResultLogger.log("Current Thread Id is " + Thread.currentThread().getId());
        TestResultLogger.log("current webdriver is " + driver.hashCode() + driver.toString());
        driver.get("http://www.baidu.com");
        TestResultLogger.log(driver.getCurrentUrl());
        TestResultLogger.log(driver.getTitle());
        driver.findElement(By.id("kw")).sendKeys("test");
        driver.findElement(By.id("su")).click();
    }

    @Test
    public void testGoogle2(){
        TestResultLogger.log("Current Thread Id is " + Thread.currentThread().getId());
        TestResultLogger.log("current webdriver is " + driver.hashCode() + driver.toString());
        driver.get("http://www.gfsoso.com");
        TestResultLogger.log(driver.getCurrentUrl());
        System.out.println(driver.getTitle());
        driver.findElement(By.name("q")).sendKeys("test");
        driver.findElement(By.cssSelector(".btn.btn-fl")).click();
    }

}
