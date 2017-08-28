package io.ift.automation.testscaffold.webtest.webUI.htmlelements;

import org.openqa.selenium.WebElement;

/**
 * Created by patrick on 15/3/18.
 *
 * @version $Id$
 */


public class Image extends HtmlElement {

    public static final String GETALT = "getAlt";
    public static final String GETSOURCE = "getSource";

    public Image(String name, WebElement element) {
        super(name, element);
    }

    public Image(WebElement element) {
        super(element);
    }

    public String getSource() {
        return getWrappedElement().getAttribute("src");
    }

    public String getAlt() {
        return getWrappedElement().getAttribute("alt");
    }
}
