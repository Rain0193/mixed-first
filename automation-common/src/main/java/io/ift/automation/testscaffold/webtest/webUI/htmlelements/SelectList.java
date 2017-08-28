package io.ift.automation.testscaffold.webtest.webUI.htmlelements;

import io.ift.automation.drivers.DriverFactory;
import io.ift.automation.helpers.CollectionsHelper;
import io.ift.automation.testscaffold.WebTestException;
import com.google.common.base.Predicate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by patrick on 15/3/18.
 * 定制化的Select List，不同于原生的Selenium Select，select list也是WebElement的一种
 *
 * @version $Id$
 */


public class SelectList extends HtmlElement {

    public static final String GETSELECT = "getSelect";
    public static final String GETOPTIONS = "getOptions";
    public static final String GETALLSELECTEDOPTIONS = "getAllSelectedOptions";
    public static final String GETFIRSTSELECTEDOPTION = "getFirstSelectedOption";
    public static final String HASSELECTEDOPTION = "hasSelectedOption";
    public static final String SELECTBYVISIBLETEXT = "selectByVisibleText";
    public static final String SELECTBYINDEX = "selectByIndex";
    public static final String SELECTFIRST = "selectFirst";
    public static final String SELECTBYGIVENATTRIBUTEVALUE = "selectByGivenAttributeValue";
    public static final String SELECTBYVALUE = "selectByValue";
    public static final String DESELECTALL = "deselectAll";
    public static final String DESELECTBYVALUE = "deselectByValue";
    public static final String DESELECTBYINDEX = "deselectByIndex";
    public static final String DESELECTBYVISIBLETEXT = "deselectByVisibleText";
    public static final String ISMULTIPLE = "isMultiple";

    public SelectList(String name, WebElement element) {
        super(name, element);
    }

    public SelectList(WebElement element) {
        super(element);
    }

    private org.openqa.selenium.support.ui.Select getSelect() {
       try {
           return new org.openqa.selenium.support.ui.Select(getWrappedElement());
       } catch (Exception e){
           throw new WebTestException("找不到select list 元素:" + super.getBy());
       }
    }

    public boolean isMultiple() {
        return getSelect().isMultiple();
    }

    public List<WebElement> getOptions() {
        return getSelect().getOptions();
    }

    public List<WebElement> getAllSelectedOptions() {
        return getSelect().getAllSelectedOptions();
    }

    public WebElement getFirstSelectedOption() {
        return getSelect().getFirstSelectedOption();
    }

    public boolean hasSelectedOption() {
        for (WebElement option : getOptions()) {
            if (option.isSelected()) {
                return true;
            }
        }

        return false;
    }

    /**
     * 根据下拉列表的显示值选择
     *
     * @param text
     */
    public void selectByVisibleText(String text) {
        //System.out.println(getWrappedElement().getAttribute("name"));
        //handle the duplicated model binding
        //todo think about failover action?
        if (getWrappedElement().isDisplayed()) {
            getSelect().selectByVisibleText(text);
        } else {
            List<WebElement> lists = DriverFactory
                .get().findElements(By.name(getWrappedElement().getAttribute("name")));
            WebElement e = CollectionsHelper.filter(lists, input -> {
                return input.isDisplayed();

            });
            setWrappedElement(e);
            getSelect().selectByVisibleText(text);
        }

    }

    /**
     * 根据下拉列表的index选择
     *
     * @param index 下拉列表的index
     */
    public void selectByIndex(int index) {
        getSelect().selectByIndex(index);
    }

    /**
     * 根据下拉列表的第一个元素
     */
    public void selectFirst() {
        if (getOptions().size() > 1) {
            getOptions().get(1).click();
        } else {
            getOptions().get(0).click();
        }

    }

    public void selectByGivenAttributeValue(String attr, String value) {

        List<WebElement> options = getSelect().getOptions();
        options.stream().filter(option -> option.getAttribute(attr).equalsIgnoreCase(value))
                .filter(option -> !option.isSelected())
                .forEach(org.openqa.selenium.WebElement::click);
    }

    /**
     * 根据value属性选择值
     *
     * @param value option tag中的value属性的值
     */
    public void selectByValue(String value) {
        getSelect().selectByValue(value);
    }

    public void deselectAll() {
        getSelect().deselectAll();
    }

    public void deselectByValue(String value) {
        getSelect().deselectByValue(value);
    }

    public void deselectByIndex(int index) {
        getSelect().deselectByIndex(index);
    }

    public void deselectByVisibleText(String text) {
        getSelect().deselectByVisibleText(text);
    }
}
