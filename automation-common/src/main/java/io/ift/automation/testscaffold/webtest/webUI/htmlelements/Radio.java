package io.ift.automation.testscaffold.webtest.webUI.htmlelements;

import io.ift.automation.helpers.StringHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by patrick on 15/3/18.
 *
 * @version $Id$
 */


public class Radio extends HtmlElement {

    public static final String GETBUTTONS = "getButtons";
    public static final String GETSELECTEDBUTTON = "getSelectedButton";
    public static final String HASSELECTEDBUTTON = "hasSelectedButton";
    public static final String SELECTBYVALUE = "selectByValue";
    public static final String SELECTBYINDEX = "selectByIndex";
    public static final String SELECTBYVISIBLETEXT = "selectByVisibleText";
    public static final String SELECTIFPRESENT = "selectIfPresent";
    public static final String SELECTBUTTON = "selectButton";

    public Radio(String name, WebElement element) {
        super(name, element);
    }

    public Radio(WebElement element) {
        super(element);
    }

    /**
     * 获取同一级别的Radio信息,需要radio的名字有一定的相似性
     * @return
     */
    public List<WebElement> getButtons() {
        //todo handle with index
        String radioName = getWrappedElement().getAttribute("name");

        String xpath;
        if (radioName == null) {
            xpath = "self::* | following::input[@type = 'radio'] | preceding::input[@type = 'radio']";
        } else {
            xpath = String.format(
                    "self::* | following::input[@type = 'radio' and contains(@name ,'%s')] | " +
                            "preceding::input[@type = 'radio' and contains(@name,'%s')]",
                    radioName, radioName);
        }

        return getWrappedElement().findElements(By.xpath(xpath));
    }

    /**
     * 获取选中的按钮
     * @return
     */
    public WebElement getSelectedButton() {
        for (WebElement button : getButtons()) {
            if (button.isSelected()) {
                return button;
            }
        }

        throw new NoSuchElementException("No selected button");
    }

    /**
     * 已经又选中的按钮
     * @return
     */
    public boolean hasSelectedButton() {
        for (WebElement button : getButtons()) {
            if (button.isSelected()) {
                return true;
            }
        }

        return false;
    }

    /**
     * 根据value attribute 值来选择radio button
     * @param value
     */
    public void selectByValue(String value) {
        value=value.trim();
        for (WebElement button : getButtons()) {
            String buttonValue = button.getAttribute("value");
            if(StringHelper.isNotEmptyOrNotBlankString(buttonValue)){
                if (value.equals(buttonValue)) {
                    selectButton(button);
                    return;
                }
            }
        }
        throw new NoSuchElementException(String.format("Cannot locate radio button with value: %s", value));
    }

    /**
     * 根据index来选择radio
     * @param index
     */
    public void selectByIndex(int index) {

        List<WebElement> buttons = getButtons();

        if (index < 0 || index >= buttons.size()) {
            throw new NoSuchElementException(String.format("Cannot locate radio button with index: %d", index));
        }

        selectButton(buttons.get(index));
    }

    /**
     * select visible text or value
     * @param text
     */
    public void selectByVisibleText(String text) {
        text=text.trim();
        List<WebElement> buttons = getButtons();
        WebElement selectedButton =null;
        for (WebElement button : buttons) {
            String value = button.getAttribute("value");
            if(value!=null) value = value.trim();
            String visibleText=button.getText(); //move self contained text into high level
            if(StringHelper.isNoneContentString(visibleText)){
                try{
                    visibleText = button.findElement(By.xpath("parent::label | following::label")).getText();
                }catch (Exception e){
                }
            }

            if(visibleText!=null) visibleText=visibleText.trim();
            if(text.equalsIgnoreCase(value)||text.equalsIgnoreCase(visibleText)){
                selectedButton=button;
                break;
            }
           }

        if(selectedButton!=null) {
            selectButton(selectedButton);
        }else{
            throw new NoSuchElementException(String.format("Cannot locate radio button with text: %s", text));
        }
    }

    public void selectIfPresent(String text){
        text=text.trim();
        try {
            if (!getWrappedElement().isDisplayed()) return;
        }catch (Exception e){
            //ignore error
        }
        selectByVisibleText(text);
    }
    private void selectButton(WebElement button) {
        if (!button.isSelected()) {
            button.click();
        }
    }
}
