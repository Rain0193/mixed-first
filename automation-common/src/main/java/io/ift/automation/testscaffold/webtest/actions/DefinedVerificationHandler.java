package io.ift.automation.testscaffold.webtest.actions;

import io.ift.automation.drivers.DriverFactory;
import io.ift.automation.testscaffold.CheckPoint;
import io.ift.automation.testscaffold.webtest.verification.ElementPropertyCheckPoint;
import io.ift.automation.testscaffold.webtest.verification.PagePropertyCheckPoint;
import io.ift.automation.testscaffold.webtest.webUI.annotations.webpage.Verification;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by patrick on 16/3/8.
 */
public class DefinedVerificationHandler {

    public static <T extends BasePage> void handlerDefinedPageVerification(T page) {
        Verification verification = page.getClass().getAnnotation(Verification.class);
        if (verification == null) return;
        PagePropertyCheckPoint checkPoint = new PagePropertyCheckPoint(verification, page.getDriver());
        verifyDefinedCheckPoint(checkPoint);
    }

    private static void verifyDefinedCheckPoint(CheckPoint checkPoint) {
        DriverFactory.getThreadLevelTestContext().addDefinedCheckPoint(checkPoint);
        checkPoint.verify();
    }

    public static void handlerDefinedElementVerification(Object element, Verification v, WebDriver driver) {
        if (v == null) return;
        if(element instanceof  WebElement){ //only handle WebElement not for List<WebElement>
            ElementPropertyCheckPoint checkPoint = new ElementPropertyCheckPoint(v, (WebElement) element, driver);
            verifyDefinedCheckPoint(checkPoint);
        }
    }
}
