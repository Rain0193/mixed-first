package io.ift.automation.testscaffold.webtest.webUI.htmlelements;

import org.openqa.selenium.WebElement;

/**
 * Created by patrick on 15/3/18.
 *
 * @version $Id$
 */


public class Link extends HtmlElement {


    public Link(String name, WebElement element) {
        super(name, element);
    }

    public Link(WebElement element) {
        super(element);
    }

    /**
     * 获取link的链接
     * @return
     */
    public String getLink() {
        return getWrappedElement().getAttribute("href");
    }

}
