package io.ift.automation.drivers;

import io.ift.automation.testscaffold.BaseWebCurrencyTest;
import io.ift.automation.testscaffold.testcontext.TestContextHolder;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class DriverFactoryTest_Concurrency extends BaseWebCurrencyTest {

    @Test(invocationCount = 5,threadPoolSize = 5)
    public void testGet() throws Exception {
        WebDriver driver = TestContextHolder.get();
        driver.get("http://www.baidu.com");
        driver.quit();
    }

    @Test(invocationCount = 5,threadPoolSize = 5)
    public void testConcurrency() throws Exception {
        TestContextHolder.get().get("http://www.baidu.com");
        TestContextHolder.get().quit();
    }
}
