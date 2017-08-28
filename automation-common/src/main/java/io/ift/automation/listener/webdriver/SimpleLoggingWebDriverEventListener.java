package io.ift.automation.listener.webdriver;

import io.ift.automation.logging.TestResultLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

/**
 * Created by patrick on 15/3/11.
 *
 * @version $Id$
 */


public class SimpleLoggingWebDriverEventListener implements WebDriverEventListener {
    private static final Logger logger = LogManager.getLogger(SimpleLoggingWebDriverEventListener.class.getName());
    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {
        TestResultLogger.log("go to {}", url);
    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
        TestResultLogger.log("in {} page",url);
    }

    @Override
    public void beforeNavigateBack(WebDriver driver) {
        logger.info("current url {}",driver.getCurrentUrl());
    }

    @Override
    public void afterNavigateBack(WebDriver driver) {
        TestResultLogger.log("current url {} ",driver.getCurrentUrl());
    }

    @Override
    public void beforeNavigateForward(WebDriver driver) {
        logger.info("current url {} ",driver.getCurrentUrl());
    }

    @Override
    public void afterNavigateForward(WebDriver driver) {
        logger.info("current url {} ",driver.getCurrentUrl());
    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        //TestResultLogger.log("current element {} ",by.toString());
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
//        logger.info("current element {} is existing",by.toString());
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        //TestResultLogger.log("current element {} is existing",element.getText());
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        //remove this to avoid unexpected alert
        //TestResultLogger.log("current page url {} is existing",driver.getCurrentUrl());
    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver) {
        //TestResultLogger.log("current element {} is existing",element.getText());
    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver) {
     //   TestResultLogger.log("current element {} is existing",element.getText());
    }

    @Override
    public void beforeScript(String script, WebDriver driver) {
        //TestResultLogger.log("current script {} is existing",script);
    }

    @Override
    public void afterScript(String script, WebDriver driver) {
        //TestResultLogger.log("current script {} is existing",script);
    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {
        logger.error(throwable);
    }
}
