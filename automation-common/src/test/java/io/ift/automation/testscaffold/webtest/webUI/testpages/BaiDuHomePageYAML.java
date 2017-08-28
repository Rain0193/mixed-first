package io.ift.automation.testscaffold.webtest.webUI.testpages;

import io.ift.automation.testscaffold.webtest.webUI.annotations.webpage.PageDescription;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.HtmlElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by patrick on 15/3/23.
 *
 * @version $Id$
 */

@PageDescription(descriptionLocation = "baidu.yaml")
public class BaiDuHomePageYAML {
    @FindBy(id="kw")
    private HtmlElement search;
    private HtmlElement submit;
    private BaiDuHeader header;

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
