package io.ift.automation.testscaffold.webtest.webUI.elementloader.internal;

import io.ift.automation.helpers.ReflectionHelper;
import io.ift.automation.helpers.webdriver.WebDriverHelper;
import io.ift.automation.testscaffold.webtest.webUI.elementloader.invocationhandler.HtmlElementListNamedProxyHandler;
import io.ift.automation.testscaffold.webtest.webUI.elementloader.invocationhandler.WebElementListNamedProxyHandler;
import io.ift.automation.testscaffold.webtest.webUI.elementloader.invocationhandler.WebElementNamedProxyHandler;
import io.ift.automation.testscaffold.webtest.webUI.exceptions.HtmlElementsException;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.HtmlElement;

import com.dooioo.automation.testscaffold.webtest.webUI.htmlelements.*;
import com.google.common.collect.Lists;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Created by patrick on 15/3/24.
 *
 * @version $Id$
 */

@SuppressWarnings("unchecked")
public class HtmlElementsFactory {

    private HtmlElementsFactory() {
    }

    /**
     * Create Page Instance but not populate the elements information
     *
     * @param pageObjectClass
     * @param driver
     * @param <T>
     * @return
     */
    public static <T> T createPageObjectInstance(Class<T> pageObjectClass,
                                                 WebDriver driver) {
        try {
            return ReflectionHelper.newInstance(pageObjectClass, driver);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new HtmlElementsException(e);
        }
    }

    public static <T> T createPageObjectInstance(Class<T> pageObjectClass,
                                                 Object... arguments) {
        try {
            return ReflectionHelper.newInstance(pageObjectClass, arguments);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new HtmlElementsException(e);
        }
    }

    public static WebElement createNamedProxyForWebElement(ClassLoader loader, ElementLocator locator, String name) {
        InvocationHandler handler = new WebElementNamedProxyHandler(locator, name);
        return (WebElement) Proxy.newProxyInstance(loader,
                new Class[]{WebElement.class, WrapsElement.class
                        , Locatable.class}, handler);
    }

    @SuppressWarnings("unchecked")
    public static List<WebElement> createNamedProxyForWebElementList(ClassLoader loader, ElementLocator locator,
                                                                     String name) {
        InvocationHandler handler = new WebElementListNamedProxyHandler(locator, name);
        return (List<WebElement>) Proxy.newProxyInstance(loader, new Class[]{List.class}, handler);
    }

    /**
     * 创建HtmlElement
     *
     * @param type
     * @param elementToWrap
     * @param <T>
     * @return
     */
    public static <T extends HtmlElement> T createHtmlElementInstance(Class<?> type,
                                                                      WebElement elementToWrap) {
        try {
            return (T) ReflectionHelper.newInstance(type, elementToWrap);

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new HtmlElementsException(e);
        }
    }


    /**
     * 创建HtmlElement 的Lists
     *
     * @param type
     * @param by
     * @param driver
     * @param <T>
     * @return
     */
    public static <T extends HtmlElement> List<T> createHtmlElements(Class<?> type, By by, WebDriver driver) {
        List<WebElement> elements = WebDriverHelper.findElements(driver, by);
        List<T> result = Lists.newArrayList();
        for (WebElement element : elements) {
            try {
                result.add((T) ReflectionHelper.newInstance(type, element));
            } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                throw new HtmlElementsException(e);
            }
        }
        return result;
    }


    /**
     * 创建HtmlElement 的Lists
     *
     * @param type: HtmlElement类型
     * @param relativeLocator : 相对父元素的xpath
     * @param driver: webdriver
     * @param <T> : HtmlElement类型
     * @return: HtmlElement 的Lists
     */
    public static <T extends HtmlElement> List<T> createHtmlElements(Class<?> type, WebElement parent,By relativeLocator, WebDriver driver) {
        List<WebElement> elements = WebDriverHelper.findElements(driver,parent,relativeLocator);
        List<T> result = Lists.newArrayList();
        for (WebElement element : elements) {
            try {
                result.add((T) ReflectionHelper.newInstance(type, element));
            } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                throw new HtmlElementsException(e);
            }
        }
        return result;
    }

    /**
     * 创建PageSection, nothing different
     *
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T createPageSectionObjectInstance(Class<?> type) {
        try {
            return (T) ReflectionHelper.newInstance(type);
        } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new HtmlElementsException(e);
        }
    }

    /**
     * 创建
     *
     * @param elementClass
     * @param loader
     * @param locator
     * @param name
     * @param <T>
     * @return
     */
    public static <T extends HtmlElement> List<T> createNamedProxyForHtmlElementList(Class<T> elementClass,
                                                                                     ClassLoader loader,
                                                                                     ElementLocator locator,
                                                                                     String name) {
        InvocationHandler handler = new HtmlElementListNamedProxyHandler<T>(elementClass, locator, name);
        return (List<T>) Proxy.newProxyInstance(loader, new Class[]{List.class}, handler);
    }


}
