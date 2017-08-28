//package com.dooioo.automation.testscaffold.webtest.webUI.htmlelements;
//
//import com.dooioo.automation.logging.TestResultLogger;
//import org.apache.commons.lang3.StringUtils;
//import org.openqa.selenium.By;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.WebElement;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * form is not used
// */
//public class Form extends HtmlElement {
//
//    private static final String TEXT_INPUT_TYPE = "text";
//    private static final String PASSWORD_INPUT_TYPE = "password";
//    private static final String CHECKBOX_TYPE = "checkbox";
//    private static final String RADIO_TYPE = "radio";
//
//    public Form(String name, WebElement element) {
//        super(name, element);
//    }
//
//    public Form(WebElement element) {
//        super(element);
//    }
//
//
//    protected WebElement findElementByKey(String key) {
//        List<WebElement> elements = getWrappedElement().findElements(By.name(key));
//        if (elements.isEmpty()) {
//            return null;
//        }
//        TestResultLogger.log("find element by key {}",key);
//        return elements.get(0);
//    }
//
//    public void fill(Map<String, Object> data) {
//        TestResultLogger.log("fill data:{}",data.toString());
//        for (Map.Entry<String, Object> entry : data.entrySet()) {
//            WebElement elementToFill = findElementByKey(entry.getKey());
//            if (elementToFill != null) {
//                fillElement(elementToFill, data.get(entry.getKey()));
//            }
//        }
//    }
//
//    protected void fillElement(WebElement element, Object value) {
//        if (value == null) {
//            return;
//        }
//
//        if (isInput(element)) {
//            String inputType = element.getAttribute("type");
//            if (inputType.equals(CHECKBOX_TYPE)) {
//                CheckBox checkBox = new CheckBox(super.getName(),element);
//                checkBox.set(Boolean.parseBoolean(value.toString()));
//            } else if (inputType.equals(RADIO_TYPE)) {
//                Radio radio = new Radio(super.getName(),element);
//                radio.selectByValue(value.toString());
//            } else {
//                element.sendKeys(getClearTextInputElementCharSequence(element) + value.toString());
//            }
//        } else if (isSelect(element)) {
//            SelectList select = new SelectList(super.getName(),element);
//            select.selectByValue(value.toString());
//        } else if (isTextArea(element)) {
//            element.sendKeys(getClearTextInputElementCharSequence(element) + value.toString());
//        }
//    }
//    private static String getClearTextInputElementCharSequence(WebElement element) {
//        return StringUtils.repeat(Keys.DELETE.toString() + Keys.BACK_SPACE, element.getText().length());
//    }
//
//    private boolean isInput(WebElement element) {
//        return "input".equals(element.getTagName());
//    }
//
//    private boolean isSelect(WebElement element) {
//        return "select".equals(element.getTagName());
//    }
//
//    private boolean isTextArea(WebElement element) {
//        return "textarea".equals(element.getTagName());
//    }
//
//}
