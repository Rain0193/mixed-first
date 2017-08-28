package io.ift.automation.testscaffold.webtest.webUI.testpages;

import io.ift.automation.testscaffold.webtest.webUI.annotations.webpage.PageDescription;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.BasePage;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.HtmlElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * Created by patrick on 15/3/23.
 *
 * @version $Id$
 */

@PageDescription(descriptionLocation = "pages/BaiduHomePageResource.xml")
public class BaiDuHomePageXML extends BasePage {
    @FindBy(id="kw")
    private HtmlElement search;
    private HtmlElement submit;
    private BaiDuHeader header;

    public BaiDuHomePageXML(WebDriver driver) {
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
}
