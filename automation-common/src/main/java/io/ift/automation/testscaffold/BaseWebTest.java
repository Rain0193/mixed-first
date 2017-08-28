package io.ift.automation.testscaffold;

import io.ift.automation.commonflows.DooiooLoginHelper;
import io.ift.automation.commonflows.models.EmployeeTestData;
import io.ift.automation.data.DataFixture;
import io.ift.automation.drivers.DriverFactory;
import io.ift.automation.listener.testng.SimpleWebDriverScreenShotTestListener;
import io.ift.automation.testscaffold.testcontext.TestContextHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.TestRunner;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by patrick on 15/3/10.
 *
 * @version $Id$
 */


public class BaseWebTest {

    protected EventFiringWebDriver driver;
    private AbstractWebDriverEventListener eventListener;
    protected String priority;
    private static final Logger logger = LogManager.getLogger(BaseWebCurrencyTest.class.getName());
    /**
     * 将TestNG context 中的listener 放到WebDriver的监听器中
     *
     * @param context
     */
    private void initEventListener(ITestContext context) {
        for (ITestListener listener : ((TestRunner) context).getTestListeners()) {
            if (listener instanceof SimpleWebDriverScreenShotTestListener) {
                eventListener = (SimpleWebDriverScreenShotTestListener) listener;
                return;
            }
        }
//        throw new RuntimeException("SimpleWebDriverScreenShotTestListener is not configured");
    }

    /**
     * 将TestNG context 中的listener 放到WebDriver的监听器中
     *
     * @param context
     * @return
     */
    private AbstractWebDriverEventListener getEventListener(ITestContext context) {
        if (eventListener == null) {
            initEventListener(context);
        }

        return eventListener;
    }

    @BeforeSuite(alwaysRun = true)
    public void initTestContext(ITestContext testContext) {
        priority = testContext.getSuite().getParameter("priority");
        //todo init test context from testng file

    }

    /**
     * 初始化 webdriver ,在每一个测试前都会做
     *
     * @param context
     */
    @BeforeMethod(alwaysRun = true)
    public void init(ITestContext context) {
        logger.info("start driver......");
        driver = DriverFactory.get();
        logger.info("Thread is " + Thread.currentThread().getName() + ";" + "ThreadId:" + Thread.currentThread().getId()
                + " and driver is " + driver);
        if (getEventListener(context) != null) {
            driver.register(getEventListener(context));
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    /**
     * 退出webdriver
     *
     * @param context
     */
    @AfterMethod(alwaysRun = true)
    public void after(ITestContext context) {
        try {
            logger.info("quit webdriver!");
            logger.info("test driver is for test case {}", context.getName());
            DriverFactory.get().quit();
        } finally {
            logger.info("start rollback the db changes");
            DriverFactory.getThreadLevelTestContext().getDataFixtures().forEach(DataFixture::rollBack);
            DriverFactory.remove();
            TestContextHolder.remove();
        }

    }

    protected WebDriver loginAndReturnDriver(EmployeeTestData submitter,TestAction...handlers) {
        WebDriver driver = DriverFactory.get();
        DooiooLoginHelper.login(submitter, driver,handlers);
        return driver;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}

