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
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.TestRunner;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

/**
 * Created by patrick on 15/5/27.
 *
 * @version $Id$
 */


public class BaseWebCurrencyTest {
    private AbstractWebDriverEventListener eventListener;
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

    /**
     * 初始化 webdriver ,在每一个测试前都会做
     *
     * @param context
     */
    @BeforeMethod(alwaysRun = true)
    public void init(ITestContext context) {
        logger.info("start driver......");
        logger.info("Thread is " + Thread.currentThread().getName() + ";" + "ThreadId:" + Thread.currentThread().getId()
                    + " and driver is " + DriverFactory.get());
        if (getEventListener(context) != null) {
            DriverFactory.get().register(getEventListener(context));
        }
        DriverFactory.get().manage().window().maximize();
        DriverFactory.get().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
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
//            DriverFactory.getThreadLevelTestContext().getWebTestResult().getFinalResult();
//            DriverFactory.get().quit();
//            TestContextHolder.remove();
//            DriverFactory.remove();
            //DriverFactory.getThreadLevelTestContext().getDataFixtures().forEach(DataFixture::rollBack);
        } finally {
            logger.info("start rollback the db changes");
            DriverFactory.getThreadLevelTestContext().getDataFixtures().forEach(DataFixture::rollBack);
            DriverFactory.get().quit();
            TestContextHolder.remove();
            DriverFactory.remove();
        }
    }

    protected WebDriver loginAndReturnDriver(EmployeeTestData submitter,TestAction...handlers) {
        WebDriver driver = DriverFactory.get();
        DooiooLoginHelper.login(submitter, driver,handlers);
        return driver;
    }
}
