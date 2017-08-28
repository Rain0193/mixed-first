package io.ift.automation.testscaffold.webtest.webUI.htmlelements;

import io.ift.automation.drivers.DriverFactory;
import io.ift.automation.helpers.StringHelper;
import io.ift.automation.helpers.webdriver.WebDriverHelper;
import io.ift.automation.logging.TestResultLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by patrick on 16/3/14.
 */
public class SelectOptions extends HtmlElement{

    private By selectOptionLocation = By.xpath("//li[@ng-repeat='item in selectOptions']");

    public SelectOptions(String name, WebElement element) {
        super(name, element);
    }

    public SelectOptions(WebElement element) {
        super(element);
    }

    /**
     * For Different Select Options , and need to click
     * @param text
     */
    public void select(String text){
        if(StringHelper.isNoneContentString(text)) return;
        String[] options= text.split("-");
        WebDriverHelper.click(DriverFactory.get(), getWrappedElement());
        for (String option : options) {
            List<WebElement> elements = WebDriverHelper.findElements(DriverFactory.get(),selectOptionLocation);
            for (WebElement element : elements) {
                WebDriverHelper.waitForSubmit(1000L);
                if(element.getText()!=null&&element.getText().replaceAll("室","房")
                        .equalsIgnoreCase(option.replaceAll("室","房"))){
                    TestResultLogger.log(element.getText());
                    WebDriverHelper.click(DriverFactory.get(),element);
                    WebDriverHelper.waitForSubmit(1000L);
                    break;
                }
            }
        }
    }
}
