package io.ift.automation.commonflows.web.flows;

import io.ift.automation.commonflows.web.pages.LoginPage;
import io.ift.automation.data.TestData;
import io.ift.automation.helpers.EnvironmentHelper;
import io.ift.automation.testscaffold.webtest.actions.WebTestActionBuilder;
import io.ift.automation.testscaffold.webtest.actions.BaseWebTestAction;
import io.ift.automation.testscaffold.webtest.webUI.elementloader.ModifiedPageFactory;
import org.openqa.selenium.WebDriver;

/**
 * Created by patrick on 15/4/27.
 * login flows
 * @version $Id$
 */


public class LoginWebFlow extends BaseWebTestAction {

    public LoginWebFlow(WebDriver driver, TestData testData) {
        super(driver, testData);
    }

    @Override
    public void execute() {
        driver.get(EnvironmentHelper.getDomainUrl());
        WebTestActionBuilder.executeUIAction(ModifiedPageFactory
                        .createPageObject(driver, LoginPage.class),
                "登陆", testData);
    }
}
