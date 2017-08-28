package io.ift.automation.testscaffold.webtest.webUI.htmlelements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by patrick on 16/4/26.
 */
public class Selector extends HtmlElement{
    public Selector(WebElement element) {
        super(element);
    }
    public Selector(String name, WebElement element) {
        super(name, element);
    }

    //for jy-selector
    public void select(String name){
        this.getWrappedElement().click();
        List<WebElement> items = this.getWrappedElement()
                .findElement(By.xpath("following::div")).findElements(By.xpath("child::div"));
        for (WebElement item : items) {
            if(item.getText().equalsIgnoreCase(name)){
                item.click();
                return;
            }
        }
    }
}
