package io.ift.automation.testscaffold.webtest.webUI.htmlelements;

import io.ift.automation.helpers.StringHelper;
import io.ift.automation.logging.TestResultLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by patrick on 15/3/18.
 *
 * @version $Id$
 */


public class CheckBox extends HtmlElement {

    public static final String SET = "set";
    public static final String GETLABELTEXT = "getLabelText";
    public static final String DESELECT = "deselect";
    public static final String GETTEXT = "getText";
    public static final String GETLABEL = "getLabel";
    public static final String GETBUTTONS = "getButtons";
    public static final String GETSELECTEDBUTTON = "getSelectedButton";
    public static final String SELECTBYVALUE = "selectByValue";
    public static final String SELECTBYVISIBLETEXT = "selectByVisibleText";
    public static final String MULTIPLESELECTBYVISIBLETEXT = "multipleSelectByVisibleText";
    public static final String SELECTCHECKBOX = "selectCheckBox";
    public static final String SELECT = "select";

    public CheckBox(String name, WebElement element) {
        super(name, element);
    }

    public CheckBox(WebElement element) {
        super(element);
    }

    public @Nullable WebElement getLabel() {

        try {
            TestResultLogger.log("find label for {}", toString());
            return getWrappedElement().findElement(By.xpath("parent::label"));
        } catch (NoSuchElementException e) {
            TestResultLogger.warn(e);
            return null;
        }

    }

    public
    @Nullable
    String getLabelText() {
        WebElement label = getLabel();
        TestResultLogger.log("find label text for {}", toString());
        return label == null ? null : label.getText();
    }

    public
    @Nullable
    String getText() {
        return getLabelText();
    }

    /**
     * select a checkbox
     */
    public void select() {
        if (!isSelected()) {
            click();
        }
    }

    /**
     * deselect a checkbox
     */
    public void deselect() {

        if (isSelected()) {
            click();
        }
    }

    /**
     * set a Checkbox for checked or not checked
     * @param value: true: checked, false: unchecked
     */
    public void set(boolean value) {

        if (value) {
            select();
        }else{
            deselect();
        }

    }

    /**
     * 获取所有的checkbox
     * @return
     */
    public List<WebElement> getButtons() {
        String checkBoxName = getWrappedElement().getAttribute("name");
        String checkBoxNgModel = getWrappedElement().getAttribute("ng-model");
        String xpath ;
        if(StringHelper.isNoneContentString(checkBoxName)){
            if (StringHelper.isNoneContentString(checkBoxNgModel)){
                xpath = "self::* | following::input[@type = 'checkbox'] | preceding::input[@type = 'checkbox']";
            }else{
                xpath = String.format(
                        "self::* | following::input[@type = 'checkbox' and @ng-model = '%s'] | " +
                                "preceding::input[@type = 'checkbox' and @ng-model = '%s']",
                        checkBoxNgModel, checkBoxNgModel);
            }
        }else{
            xpath = String.format(
                    "self::* | following::input[@type = 'checkbox' and @name = '%s'] | " +
                            "preceding::input[@type = 'checkbox' and @name = '%s']",
                    checkBoxName, checkBoxName);
        }

        return getWrappedElement().findElements(By.xpath(xpath));
    }

    /**
     * 获取选中的按钮
     *
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
     * 根据value attribute 值来选择checkbox button
     *
     * @param text
     */
    public void selectByValue(String text) {
        text=text.trim();
        for (WebElement button : getButtons()) {
            String buttonValue = button.getAttribute("value");
            if (StringHelper.isNotEmptyOrNotBlankString(buttonValue)) {
                buttonValue=buttonValue.trim();
                if (text.equalsIgnoreCase(buttonValue)) {
                    selectCheckBox(button);
                    return;
                }
            }
        }
    }

    /**
     * 根据文本选择
     * @param text
     */
    public void selectByVisibleText(String text) {
        text=text.trim();
        List<WebElement> buttons = getButtons();
        for (WebElement button : buttons) {
            String value = button.getAttribute("value");
            if(value!=null) value =value.trim();
            //input box have own text
            String visibleText;
            try {
                visibleText = button.findElement(By.xpath("parent::label")).getText();
            } catch (Exception e) {
                visibleText = button.getText();
            }
            if(visibleText!=null) visibleText=visibleText.trim();
            if (text.equalsIgnoreCase(value) || text.equalsIgnoreCase(visibleText)) {
                selectCheckBox(button);
                return;
            }
        }
    }

    /**
     * 根据文本选择多个选择框
     * @param text
     */
    public void multipleSelectByVisibleText(String text) {
        text=text.trim();
        String keys[] = text.split("-");
        for (String key : keys) {
            List<WebElement> buttons = getButtons();
            for (WebElement button : buttons) {
                String value = button.getAttribute("value");
                String visibleText = button.findElement(By.xpath("parent::label")).getText();
                if(value!=null) value =value.trim();
                if(visibleText!=null) visibleText=visibleText.trim();
                if (key.equalsIgnoreCase(value) || key.equalsIgnoreCase(visibleText)) {
                    selectCheckBox(button);
                    break;
                }
            }
        }
    }

    /**
     * select a CheckBox
     * @param button
     */
    private void selectCheckBox(WebElement button) {
        if (!button.isSelected()) {
            button.click();
        }
    }

}
