package io.ift.automation.testscaffold.webtest.webUI.htmlelements;

import io.ift.automation.drivers.DriverFactory;
import io.ift.automation.helpers.CollectionsHelper;
import io.ift.automation.helpers.StringHelper;
import io.ift.automation.helpers.webdriver.WebDriverHelper;
import io.ift.automation.logging.TestResultLogger;
import com.google.common.base.Predicate;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by patrick on 15/3/18.
 *
 * @version $Id$
 * @author patrick
 */
public class InputBox extends HtmlElement {

    public static final String INPUTIFPRESENT = "inputIfPresent";
    public static final String INPUTANDSELECT = "inputAndSelect";
    /**
     * <p>Constructor for InputBox.</p>
     *
     * @param name a {@link java.lang.String} object.
     * @param element a {@link org.openqa.selenium.WebElement} object.
     */
    public InputBox(String name, WebElement element) {
        super(name, element);
    }

    /**
     * <p>Constructor for InputBox.</p>
     *
     * @param element a {@link org.openqa.selenium.WebElement} object.
     */
    public InputBox(WebElement element) {
        super(element);
    }

    /**
     * 输入所有的文本框
     *
     * @param text a {@link java.lang.String} object.
     */
    @Deprecated
    public void inputAllSameBox(String text) {
        if (text == null) throw new RuntimeException("没有数据输入，请检查你的输入数据，位置是" + this.toString());
        String[] temp = text.replaceAll(",", "-").split("-");
        String ngModel = getWrappedElement().getAttribute("ng-model");
        TestResultLogger.log("开始输入{}", text);
        if (StringHelper.isNotEmptyOrNotBlankString(ngModel)) {
            List<WebElement> displayedElements = CollectionsHelper.filterForList(
                DriverFactory.get().findElements(
                    By.xpath(String.format("//*[not(contains(@style,'display: none;'))]//input[@ng-model='%s']", ngModel)))
                    , input -> {
                        return input.isDisplayed();
                    });
            for (int i = 0; i < temp.length; i++) {
                WebDriverHelper.input(DriverFactory.get(),displayedElements.get(i),temp[i]);
                if(i+1<temp.length){
                    displayedElements.get(i+1).click();
                    WebDriverHelper.waitForSubmit(1000L);
                }
                //ng-blur event fired, need to refresh the elements
                displayedElements = CollectionsHelper.filterForList(DriverFactory.get().findElements(
                        By.xpath(String.format("//*[not(contains(@style,'display: none;'))]//input[@ng-model='%s']", ngModel)))
                        , input -> {
                            return input.isDisplayed();
                        });
            }
        } else {
            getWrappedElement().sendKeys(text);
        }
    }

    /**
     * 输入框如果存在则输入
     *
     * @param inputs a {@link java.lang.String} object.
     */
    public void inputIfPresent(String inputs){
        try{
            if(!getWrappedElement().isDisplayed()||
                    !getWrappedElement().isEnabled()||isReadyOnly()) return;
        }catch (Exception e){
            //ignore all exceptions
            return;
        }
//        catch (NoSuchElementException | ElementNotVisibleException | TimeoutException e){
//            return;
//        }
        clearAndInput(inputs);
    }

    /**
     * input and select
     * 主要正对AutoComplete的输入框
     *
     * @param inputs a {@link java.lang.String} object.
     */
    public void inputAndSelect(String inputs){

        List<WebElement> elements = DriverFactory.get().findElements(getBy());
        for (WebElement element : elements) {
            if(element.isDisplayed()&&element.isEnabled()){
                this.setWrappedElement(element);
                clearAndInput(inputs);
                break;
            }
        }
       WebDriverHelper.waitForSubmit(5000L);
        List<WebElement> links = WebDriverHelper.findElements(DriverFactory.get(),getWrappedElement()
                ,By.xpath("following::div/a | following-sibling::div/a |following-sibling::a" ));
        for (WebElement link : links) {
            String text = link.getText();
//            TestResultLogger.warn("linked text is "+text);
            if(text.equalsIgnoreCase(inputs)){
                TestResultLogger.warn(text+" is found");
                WebDriverHelper.click(DriverFactory.get(),link);
                return;
            }
        }

        WebDriverHelper.click(DriverFactory.get(),links.get(0));
    }


    /**
     * 输入如果输入框存在
     *
     * @param inputs a {@link java.lang.String} object.
     */
    @Deprecated
    public void inputIfVisible(String inputs){

        List<WebElement> elements = DriverFactory.get().findElements(getBy());
        for (WebElement element : elements) {
            if(element.isDisplayed()&&element.isEnabled()){
                this.setWrappedElement(element);
                input(inputs);
                break;
            }
        }
    }

    /**
     * input for readonly inputbox
     * @param content
     */
    public void inputReadOnly(String content){
        DriverFactory.get().executeScript(
                "arguments[0].removeAttribute('readonly','readonly')",this.getWrappedElement());
        WebDriverHelper.input(DriverFactory.get(),this.getWrappedElement(),content);
    }
}
