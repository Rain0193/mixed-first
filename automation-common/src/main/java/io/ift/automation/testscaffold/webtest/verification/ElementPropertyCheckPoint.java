package io.ift.automation.testscaffold.webtest.verification;

import io.ift.automation.helpers.StringHelper;
import io.ift.automation.testscaffold.CheckPoint;
import io.ift.automation.testscaffold.Where;
import io.ift.automation.testscaffold.webtest.webUI.annotations.webpage.Verification;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by patrick on 16/3/8.
 */
public class ElementPropertyCheckPoint implements CheckPoint {
    private final Verification verification;
    private final WebDriver driver;
    private final WebElement htmlElement;

    public ElementPropertyCheckPoint(Verification verification, WebElement htmlElement,
                                     WebDriver driver) {
        this.verification = verification;
        this.driver = driver;
        this.htmlElement = htmlElement;
    }

    @Override
    public void verify() {
        String property = htmlElement.getAttribute(verification.whatProperty());
        if (property != null) {
            //todo pattern check
            String result = StringHelper.extract(property, verification.expected());
            sa.assertTrue(StringHelper.isNotEmptyOrNotBlankString(result),
                    "检查属性内容,期望:" + verification.expected() + "实际:" + property);
        }
    }

    @Override
    public Where where() {
        return new Where("*") {
            @Override
            public boolean eval(String locationName) {
                return false;
            }
        };
    }
}
