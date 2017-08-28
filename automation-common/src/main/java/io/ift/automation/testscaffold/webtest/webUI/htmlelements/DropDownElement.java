package io.ift.automation.testscaffold.webtest.webUI.htmlelements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by patrick on 15/7/3.
 *  需要先点击一下才触发下拉框的选项
 * @version $Id: DropDownElement.java 2641 2016-03-07 03:34:30Z guoxh $
 */


public class DropDownElement extends HtmlElement {
    public static final String SELECTBYLINK = "selectByLink";
    public static final String SELECTBYLINKID = "selectByLinkId";
    public static final String SELECTFORPOPSELECT = "selectForPopSelect";
    public static final String SELECT = "select";

    public DropDownElement(String name, WebElement element) {
        super(name, element);
    }

    public DropDownElement(WebElement element) {
        super(element);
    }

    /**
     * 需要先点击一下才触发下拉框
     * 下拉框选择, 下拉框可能是多个选项,也可能是一些link文字
     * @param text
     */
    public void select(String text){
        getWrappedElement().click();
        List<WebElement> numberTypes = getWrappedElement().findElements(By.xpath(".//input[@type='number']"));
        if(!numberTypes.isEmpty()){ //处理有多个选项的控件，如房源页面上的楼层选择
            String inputs[]=text.split("-");
            for (String input : inputs) {
                List<WebElement> options = getWrappedElement().findElements(By.xpath(".//a[not(contains(@style,'display: none;'))]"));
                options.stream().filter(option -> option.getText().contains(input)).forEach(WebElement::click);
            }
        }else{
            List<WebElement> options = getWrappedElement().findElements(By.tagName("a"));
            options.stream().filter(option -> option.getText().contains(text)).forEach(org.openqa.selenium.WebElement::click);
        }

    }

    /**
     * 需要先点击一下才触发下拉框
     * 下拉弹出选择框,进行选择
     * @param text
     */
    public void selectForPopSelect(String text){
        getWrappedElement().click();
        List<WebElement> options = getWrappedElement().findElements(By.xpath("..//a[not(contains(@style,'display: none;'))]"));
        for (WebElement option : options) {
            if(option.getText().contains(text)){
                option.click();
                return;
            }
        }
    }

    /**
     * 根据下拉框名字的链接选择,需要先点击一下才触发下拉框
     * @param text
     */
    public void selectByLink(String text){
        getWrappedElement().click();
        List<WebElement> options = getWrappedElement().findElements(By.xpath(String.format("//a[text()='%s']",text)));
        for (WebElement option : options) {
            if(option.getText().contains(text)){
                option.click();
                return;
            }
        }
    }

    /**
     * 根据下拉框名字的链接选择,需要先点击一下才触发下拉框
     * @param text
     */
    public void selectByLinkId(String text){
        getWrappedElement().click();
        List<WebElement> options = getWrappedElement().findElements(By.id(text));
        for (WebElement option : options) {
            if(option.getText().contains(text)){
                option.click();
                return;
            }
        }
    }
}
