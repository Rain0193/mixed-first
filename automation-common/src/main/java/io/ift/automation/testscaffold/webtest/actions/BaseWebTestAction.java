package io.ift.automation.testscaffold.webtest.actions;

import io.ift.automation.data.TestData;
import io.ift.automation.testscaffold.TestAction;
import org.openqa.selenium.WebDriver;

/**
 * Created by patrick on 15/4/3.
 *
 * @version $Id$
 */

public abstract class BaseWebTestAction  implements TestAction {

    protected WebDriver driver;
    protected TestData testData ;

    public BaseWebTestAction(WebDriver driver, TestData testData) {
        this.driver = driver;
        this.testData= testData;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public TestData getTestData() {
        return testData;
    }

    public void setTestData(TestData testData) {
        this.testData = testData;
    }


}
