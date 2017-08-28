package io.ift.automation.testscaffold.webtest.webUI.htmlelements;

import org.openqa.selenium.WebElement;

/**
 * Created by patrick on 15/6/9.
 *  定制化的控件，需要先点击这行，然后才可以编辑
 * @version $Id: EditableRow.java 1451 2015-06-15 06:35:17Z wuke $
 */


public class EditableRow extends HtmlElement {

    public EditableRow(String name, WebElement element) {
        super(name, element);
    }

    public EditableRow(WebElement element) {
        super(element);
    }
}
