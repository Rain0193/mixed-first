package io.ift.automation.testscaffold.webtest.webUI.testpages;

import io.ift.automation.data.TestData;
import io.ift.automation.testscaffold.webtest.webUI.annotations.webpage.PageSectionObject;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by patrick on 15/3/23.
 *
 * @version $Id$
 */

public class BaiDuHomeBasePage2 extends BasePage {
    @FindBy(id="kw")
    private WebElement search;
    private WebElement submit;
    @PageSectionObject
    private BaiDuHeader header;

    public BaiDuHomeBasePage2(WebDriver driver) {
        super(driver);
    }

    public BaiDuHomeBasePage2(WebDriver driver, TestData data){
        super(driver);
    }
}
