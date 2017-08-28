package io.ift.automation.testscaffold.webtest.actions;

import io.ift.automation.data.TestData;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.BasePage;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static io.ift.automation.testscaffold.webtest.actions.WebTestActionBuilder.executeUIAction;

/**
 * Created by patrick on 15/4/30.
 *
 * @version $Id$
 */
public class ExecutablePageObject extends BasePage {

    public ExecutablePageObject(WebDriver driver) {
        super(driver);
    }

    public void processUIAction(String flowName,
                                 TestData testData){

        WebTestActionBuilder.executeUIAction(this, flowName, testData);
    }

    public ExecutablePageObject process(String flowName,
                                TestData testData){

        WebTestActionBuilder.executeUIAction(this, flowName, testData);
        return this;
    }

    public ExecutablePageObject process(List<String> elementActions,
                                        TestData testData){

        WebTestActionBuilder.executeUIAction(this, elementActions, testData);
        return this;
    }

}
