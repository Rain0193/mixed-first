package io.ift.automation.testscaffold.webtest.webUI.htmlelements;

import io.ift.automation.logging.TestResultLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by patrick on 15/6/16.
 *
 * @version $Id: ListPictureElement.java 1771 2015-08-21 06:02:12Z wuke $
 */


public class ListPictureElement extends HtmlElement {

    public ListPictureElement(String name, WebElement element) {
        super(name, element);
    }

    public ListPictureElement(WebElement element) {
        super(element);
    }

    public void select(){
        List<WebElement> elements = getAllList();
        for (WebElement element : elements) {
            WebElement checkBox = element.findElement(By.cssSelector("[type=checkbox]"));
            if(!checkBox.isSelected()){
                TestResultLogger.log("开始选择图片");
                checkBox.click();
                return;
            }
        }
    }

    public void deSelect(){
        List<WebElement> elements = getAllList();
        for (WebElement element : elements) {
            WebElement checkBox = element.findElement(By.cssSelector("[type=checkbox]"));
            if(checkBox.isSelected()){
                TestResultLogger.log("开始选择图片");
                checkBox.click();
                return;
            }
        }
    }

    public void selectAll(){
        List<WebElement> elements = getAllList();
        for (WebElement element : elements) {
            WebElement checkBox = element.findElement(By.cssSelector("[type=checkbox]"));
            if(!checkBox.isSelected()){
                TestResultLogger.log("开始选择图片");
                checkBox.click();
            }
        }
    }

    public void deSelectAll(){
        List<WebElement> elements = getAllList();
        for (WebElement element : elements) {
            WebElement checkBox = element.findElement(By.cssSelector("[type=checkbox]"));
            if(checkBox.isSelected()){
                TestResultLogger.log("开始选择图片");
                checkBox.click();
            }
        }
    }

    private List<WebElement> getAllList(){
        return getWrappedElement().findElements(By.cssSelector("li"));
    }


}
