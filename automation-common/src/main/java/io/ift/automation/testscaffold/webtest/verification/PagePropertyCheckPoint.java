package io.ift.automation.testscaffold.webtest.verification;

import io.ift.automation.helpers.webdriver.WebDriverHelper;
import io.ift.automation.testscaffold.CheckPoint;
import io.ift.automation.testscaffold.Where;
import io.ift.automation.testscaffold.webtest.webUI.annotations.webpage.Verification;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.CoreMatchers.containsString;


/**
 * Created by patrick on 16/3/8.
 */
public class PagePropertyCheckPoint implements CheckPoint {
    private Verification verification;
    private WebDriver driver;

    public PagePropertyCheckPoint(Verification verification, WebDriver driver) {
        this.verification = verification;
        this.driver = driver;
    }

    @Override
    public void verify() {
        WebDriverHelper.waitDomReady(driver);
        if (verification.whatProperty().equalsIgnoreCase(ElementPropertyEnum.title.name())) {
            sa.assertThat(driver.getTitle(),
                    containsString(verification.expected()), "检查页面标题");
        }
        if (verification.whatProperty().equalsIgnoreCase(ElementPropertyEnum.url.name())) {
            sa.assertThat(driver.getCurrentUrl(),containsString(verification.expected()), "检查页面连接");
        }
    }

    @Override
    public Where where() {
        return new Where("*") {
            public boolean eval(String locationName) {
                return false;
            }
        };
    }

}
