package io.ift.automation.testscaffold.webtest.webUI.support;

import io.ift.automation.helpers.ReflectionHelper;
import io.ift.automation.testscaffold.webtest.webUI.annotations.webpage.ElementName;
import io.ift.automation.testscaffold.webtest.webUI.exceptions.HtmlElementsException;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.HtmlElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import java.lang.reflect.*;


/**
 * Created by patrick on 15/3/23.
 *
 * @version $Id$
 */


public final class HtmlElementHelper {
    private HtmlElementHelper(){}


    public static boolean isHtmlElement(Class<?> clazz){
        return HtmlElement.class.isAssignableFrom(clazz);
    }

    public static boolean isHtmlElement(Field field){
        return isHtmlElement(field.getType());
    }

    public static <T> boolean isHtmlElement(T instance){
        return HtmlElement.class.isInstance(instance);
    }

    public static <T> boolean isHtmlElementList(Field field){
        if(!ReflectionHelper.isParameterizedList(field)){
            return false;
        }
        Class listParameterClass = ReflectionHelper.getGenericParameterClass(field);
        return isHtmlElement(listParameterClass);
    }



    public static boolean isRemoteWebElement(WebElement element) {
        return element.getClass().equals(RemoteWebElement.class);
    }

    public static boolean isOnRemoteWebDriver(WebElement element) {
        if (!isRemoteWebElement(element)) {
            return false;
        }

        // Since subclasses of RemoteWebElement were finally removed in Selenium 2.26.0,
        // WebElements on local drivers
        // are also instances of RemoteWebElement class.
        // The only way that we found at the current moment to find out
        // whether WebElement instance is on remote driver is to check the class of RemoteWebElement "parent" filed,
        // which contains WebDriver instance to which this RemoteWebElement belongs.
        // As this field has protected access this is done by reflection.
        // TODO It's is a kind of a dirty and quick fix ,to be improved in future versions.
        RemoteWebElement remoteWebElement = (RemoteWebElement) element;
        try {
            Field elementParentFiled = RemoteWebElement.class.getDeclaredField("parent");
            elementParentFiled.setAccessible(true);
            WebDriver elementParent = (WebDriver) elementParentFiled.get(remoteWebElement);
            return elementParent.getClass().equals(RemoteWebDriver.class);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new HtmlElementsException("Unable to find out if WebElement is on remote driver", e);
        }
    }

    /**
     * get element name ,there is no annotation for field, use the field name as element name
     * @param field
     * @return
     */
    public static String getElementName(Field field) {
        if (field.isAnnotationPresent(ElementName.class)) {
            return field.getAnnotation(ElementName.class).elementName();
        }
        if (field.getType().isAnnotationPresent(ElementName.class)) {
            return field.getType().getAnnotation(ElementName.class).elementName();
        } else {
            return field.getName();
        }
    }

    /**
     * get element name ,there is no annotation for class, use the field name as element name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> String getElementName(Class<T> clazz) {
        if (clazz.isAnnotationPresent(ElementName.class)) {
            return clazz.getAnnotation(ElementName.class).elementName();
        } else {
            return clazz.getSimpleName();
        }
    }

    /**
     * have find by annotation
     * @param field
     * @return
     */
    public static boolean hasFindByAnnotation(Field field){
        return field.getAnnotation(FindBy.class) != null
                || field.getAnnotation(FindBys.class) != null
                || field.getAnnotation(FindAll.class) != null;
    }
}
