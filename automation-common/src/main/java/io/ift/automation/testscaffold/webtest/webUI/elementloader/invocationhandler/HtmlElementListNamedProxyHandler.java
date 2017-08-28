package io.ift.automation.testscaffold.webtest.webUI.elementloader.invocationhandler;

import io.ift.automation.testscaffold.webtest.webUI.elementloader.internal.HtmlElementsFactory;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.HtmlElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unchecked")
public class HtmlElementListNamedProxyHandler<T extends HtmlElement> implements InvocationHandler {
    private final Class<T> htmlElementClass;
    private final ElementLocator locator;
    private final String name;

    public HtmlElementListNamedProxyHandler(Class<T> htmlElementClass, ElementLocator locator, String name) {
        this.htmlElementClass = htmlElementClass;
        this.locator = locator;
        this.name = name;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        if ("toString".equals(method.getName())) {
            return name;
        }
        List<T> htmlElements = new LinkedList();
        List<WebElement> elements = locator.findElements();
        int elementNumber = 0;
        for (WebElement element : elements) {
            T htmlElement = HtmlElementsFactory.createHtmlElementInstance(htmlElementClass, element);
            String htmlElementName = String.format("%s [%d]", name, elementNumber);
            htmlElement.setName(htmlElementName);
            htmlElements.add(htmlElement);
            elementNumber++;
            //todo add post html instance processor
        }

        try {
            return method.invoke(htmlElements, objects);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
