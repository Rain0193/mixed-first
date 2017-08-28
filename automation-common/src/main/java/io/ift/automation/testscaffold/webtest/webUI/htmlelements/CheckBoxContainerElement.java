package io.ift.automation.testscaffold.webtest.webUI.htmlelements;

import io.ift.automation.drivers.DriverFactory;
import io.ift.automation.helpers.webdriver.WebDriverHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


/**
 *
 * CheckBox Container Element 默认操作为select 方法
 * 该定制元素主要是为了Checkbox选择需要先点击一下checkbox的容器,然后再选择,最后确认选择
 *
 * Created by patrick on 15/10/19.
 */
public class CheckBoxContainerElement extends HtmlElement{
    public static final String SELECT = "select";

    public CheckBoxContainerElement(String name, WebElement element) {
        super(name, element);
    }

    public CheckBoxContainerElement(WebElement element) {
        super(element);
    }

    public void select(String name){
        //click first then select checkbox and submit the checked one
        WebDriverHelper.clickIfPresent(DriverFactory.get(), getWrappedElement(), By.tagName("input"));
        WebElement container = getWrappedElement().findElement(By.xpath("div/div[@checkboxcontainer]"));
        CheckBox checkBox = new CheckBox(container.findElement(By.xpath("label/input[@type='checkbox']")));
        checkBox.selectByVisibleText(name);

        WebDriverHelper.clickIfPresent(DriverFactory.get(),getWrappedElement(),By.linkText("确定"));

    }


}
