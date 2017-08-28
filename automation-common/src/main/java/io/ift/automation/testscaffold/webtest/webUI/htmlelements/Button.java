package io.ift.automation.testscaffold.webtest.webUI.htmlelements;

import io.ift.automation.drivers.DriverFactory;
import io.ift.automation.helpers.CollectionsHelper;
import com.google.common.base.Predicate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by patrick on 15/3/18.
 *
 * @version $Id$
 */


public class Button extends HtmlElement {

    public static final String CLICKBYID="clickById";

    public Button(String name, WebElement element) {
        super(name, element);
    }

    public Button(WebElement element) {
        super(element);
    }

    /**
     * click by defined element id
     * @param id
     */
    public void clickById(String id){
        List<WebElement> elements = DriverFactory.get().findElements(super.getBy());
        WebElement element = CollectionsHelper.filter(elements, webElement -> {
            return id.equals(webElement.getAttribute("id"));
        });

        element.click();
    }
}
