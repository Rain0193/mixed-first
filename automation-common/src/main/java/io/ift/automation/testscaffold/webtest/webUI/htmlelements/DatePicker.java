package io.ift.automation.testscaffold.webtest.webUI.htmlelements;

import io.ift.automation.drivers.DriverFactory;
import io.ift.automation.helpers.DateHelper;
import io.ift.automation.helpers.StringHelper;
import io.ift.automation.helpers.webdriver.WebDriverHelper;
import org.apache.commons.lang3.tuple.Triple;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Created by patrick on 15/6/9.
 *
 * @version $Id: DatePicker.java 2838 2016-04-06 05:16:50Z wuke $
 */


public class DatePicker extends HtmlElement {
    public static final String SELECTDATE = "selectDate";
    public static final String INPUT= "input";

    public DatePicker(String name, WebElement element) {
        super(name, element);
    }

    public DatePicker(WebElement element) {
        super(element);
    }

    /**
     * 选择日期
     *
     * @param date
     */
    public void selectDate(String date) {//2013-12-09

        WebDriverHelper.click(DriverFactory.get(), getWrappedElement());

        Triple t = DateHelper.convertDate(date);
        WebElement datePickerDiv = getWrappedElement().findElement(By.xpath("following::div"));

        WebElement selectorYear = datePickerDiv.findElement(By.xpath("//select[contains(@class,'selYear')]"));
        Select year = new Select(selectorYear);
        year.selectByVisibleText(t.getLeft().toString());
        WebElement selectorMon;
        try {
            selectorMon = datePickerDiv.findElement(By.xpath("//select[contains(@class,'selMonth')]"));
            if(selectorMon.isDisplayed()){
                Select month = new Select(selectorMon);
                month.selectByVisibleText(t.getMiddle().toString());
            }else{
                List<WebElement> day = datePickerDiv.findElements(By.xpath("//*[contains(@class,'selectable_day')]"));
                for (WebElement webElement : day) {
                    if (webElement.getText().equalsIgnoreCase(t.getMiddle().toString() + "月")) {
                        webElement.click();
                        break;
                    }
                }
            }
            if (StringHelper.isNotEmptyOrNotBlankString(t.getRight().toString())) {
                List<WebElement> day = datePickerDiv.findElements(By.xpath("//*[contains(@class,'selectable_day')]"));
                for (WebElement webElement : day) {
                    if (webElement.getText().equalsIgnoreCase(t.getRight().toString())) {
                        webElement.click();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            //do nothing for handle other
            List<WebElement> day = datePickerDiv.findElements(By.xpath("//*[contains(@class,'selectable_day')]"));
            for (WebElement webElement : day) {
                if (webElement.getText().equalsIgnoreCase(t.getMiddle().toString() + "月")) {
                    webElement.click();
                    break;
                }
            }
        }

    }

    public void input(String date){
        WebElement inputbox  = this.getWrappedElement().findElement(By.xpath("input"));
        DriverFactory.get().executeScript(
                "arguments[0].removeAttribute('readonly','readonly')",inputbox);
        WebDriverHelper.input(DriverFactory.get(),inputbox,date);
    }
}
