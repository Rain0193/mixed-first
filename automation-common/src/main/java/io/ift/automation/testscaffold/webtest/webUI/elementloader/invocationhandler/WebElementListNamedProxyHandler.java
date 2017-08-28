package io.ift.automation.testscaffold.webtest.webUI.elementloader.invocationhandler;
/**
 * Created by patrick on 15-3-24.
 */

import io.ift.automation.testscaffold.webtest.webUI.elementloader.locator.CustomerElementByLocator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class WebElementListNamedProxyHandler implements InvocationHandler {

    private static final Logger LOGGER = LogManager.getLogger(WebElementListNamedProxyHandler.class);
    private final ElementLocator locator;
    private final String name;

    public WebElementListNamedProxyHandler(ElementLocator locator, String name) {
        this.locator = locator;
        this.name = name;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] objects) throws Throwable {
        if ("toString".equals(method.getName())) {
            return name;
        }
        // todo think about cache issue and performance issue
        ((CustomerElementByLocator) locator).rebuildByIfNeeded();
        List<WebElement> elements = locator.findElements();
        try {
            LOGGER.debug("start find elements");
            return method.invoke(elements, objects);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
