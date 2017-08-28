package io.ift.automation.testscaffold.webtest.webUI.htmlelements;

import io.ift.automation.drivers.DriverFactory;
import io.ift.automation.helpers.webdriver.WebDriverHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by patrick on 15/10/19.
 */
public class TagElement extends HtmlElement{
    public static final String SELECT = "select";

    public TagElement(String name, WebElement element) {
        super(name, element);
    }

    public TagElement(WebElement element) {
        super(element);
    }

    /**
     * 选择给定的tag标签
     * @param tags: 给定tag标签
     */
    public void select(String tags){
        String[] tagArray = tags.split("-");
        List<WebElement> elements = WebDriverHelper.findElements(DriverFactory.get(), this.getBy());
        for (String tag : tagArray) {
            for (WebElement element : elements) {
                if(tag.equalsIgnoreCase(element.getText())){
                    element.click();
                    break;
                }
            }
        }
    }
}
