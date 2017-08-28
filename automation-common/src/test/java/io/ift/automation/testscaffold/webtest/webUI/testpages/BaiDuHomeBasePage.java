package io.ift.automation.testscaffold.webtest.webUI.testpages;

import io.ift.automation.data.TestData;
import io.ift.automation.testscaffold.webtest.webUI.annotations.webpage.PageSectionObject;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.BasePage;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.HtmlElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * Created by patrick on 15/3/23.
 *
 * @version $Id$
 */

public class BaiDuHomeBasePage extends BasePage {
    @FindBy(id="kw")
    private HtmlElement search;
    private HtmlElement submit;
    @PageSectionObject
    private BaiDuHeader header;

    public BaiDuHomeBasePage(WebDriver driver) {
        super(driver);
    }

    public BaiDuHomeBasePage(WebDriver driver, TestData data){
        super(driver);
    }
    public HtmlElement getSearch() {
        return search;
    }

    public void setSearch(HtmlElement search) {
        this.search = search;
    }

    public HtmlElement getSubmit() {
        return submit;
    }

    public void setSubmit(HtmlElement submit) {
        this.submit = submit;
    }

    public BaiDuHeader getHeader() {
        return header;
    }

    public void setHeader(BaiDuHeader header) {
        this.header = header;
    }
}
