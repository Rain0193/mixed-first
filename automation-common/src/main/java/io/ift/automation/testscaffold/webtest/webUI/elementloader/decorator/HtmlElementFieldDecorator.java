package io.ift.automation.testscaffold.webtest.webUI.elementloader.decorator;

import io.ift.automation.testscaffold.webtest.annotations.FindByRefer;
import io.ift.automation.testscaffold.webtest.webUI.annotations.webpage.HtmlElementObject;
import io.ift.automation.testscaffold.webtest.webUI.annotations.webpage.PageSectionObject;
import io.ift.automation.testscaffold.webtest.webUI.elementloader.internal.HtmlElementsFactory;
import io.ift.automation.testscaffold.webtest.webUI.elementloader.ModifiedPageFactory;
import io.ift.automation.testscaffold.webtest.webUI.elementloader.locator.CustomerElementLocatorFactory;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.BasePage;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.HtmlElement;
import com.google.common.collect.Lists;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import org.openqa.selenium.support.ui.Select;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import static io.ift.automation.testscaffold.webtest.webUI.support.HtmlElementHelper.getElementName;
import static io.ift.automation.testscaffold.webtest.webUI.support.HtmlElementHelper.isHtmlElement;
import static io.ift.automation.testscaffold.webtest.webUI.support.HtmlElementHelper.isHtmlElementList;

import io.ift.automation.helpers.ReflectionHelper;

/**
 * Created by patrick on 15/3/23.
 *
 * @version $Id$
 */

@SuppressWarnings("unchecked")
public class HtmlElementFieldDecorator implements FieldDecorator {

    private static List<Class> definedHtmlElementClasses
            = Lists.newArrayList(WebElement.class, HtmlElement.class,
                                 BasePage.class, Select.class);


    private static List<Class> definedHtmlElementAnnotation
            = Lists.newArrayList(PageSectionObject.class,
            HtmlElementObject.class,
            FindBy.class, FindBys.class, FindAll.class, FindByRefer.class);

    private CustomerElementLocatorFactory locatorFactory;

    public HtmlElementFieldDecorator(CustomerElementLocatorFactory locatorFactory) {
        this.locatorFactory = locatorFactory;
    }

    @Override
    public
    @Nullable
    Object decorate(ClassLoader loader, Field field) {
        //todo enhance decorate the fields using more annotation...........
        if (!isDecorateHtmlField(field)) return null;
        String elementName = getElementName(field);
        ElementLocator locator = locatorFactory.createLocator(field);
        // if it annotated with page section object, create page section object(it is a recursive call)
        if (isHtmlElement(field)) {
            //create html element
            return decorateHtmlElement(field, loader, locator, elementName);
        } else if (isHtmlElementList(field)) {
            return decorateHtmlElementList(field, loader, locator, elementName);
        } else if (isGivenClass(WebElement.class, field.getType())) {
            return decorateWebElement(loader, locator, elementName);
        } else if (ReflectionHelper.isGivenClassList(WebElement.class, field)) {
            return decorateWebElementList(loader, locator, elementName);
        } else if (isPageSection(field)) {
            return ModifiedPageFactory.populateElements(locatorFactory, field.getType());
        }

        return null;
    }

    private boolean isPageSection(Field field) {
        return field.getAnnotation(PageSectionObject.class) != null;
    }

    /**
     * 创建WebElementList的
     *
     * @param loader
     * @param locator
     * @param elementName
     * @return
     */
    private Object decorateWebElementList(ClassLoader loader, ElementLocator locator,
                                          String elementName) {
        return HtmlElementsFactory.createNamedProxyForWebElementList(loader, locator, elementName);

    }

    private Object decorateWebElement(ClassLoader loader,
                                      ElementLocator locator, String elementName) {
        return HtmlElementsFactory.createNamedProxyForWebElement(loader, locator, elementName);

    }

    private <T extends HtmlElement> List<T> decorateHtmlElementList(Field field,
                                                                    ClassLoader loader, ElementLocator locator,
                                                                    String elementName) {

        return HtmlElementsFactory
            .createNamedProxyForHtmlElementList(ReflectionHelper.getGenericParameterClass(field),
                                                loader, locator, elementName);
    }

    private <T extends HtmlElement> T decorateHtmlElement(Field field, ClassLoader loader,
                                                          ElementLocator locator, String elementName) {
        // Create element and initialize it with WebElement proxy
        WebElement elementToWrap = HtmlElementsFactory
            .createNamedProxyForWebElement(loader, locator, elementName);
        T htmlElementInstance = HtmlElementsFactory
            .createHtmlElementInstance(field.getType(), elementToWrap);
        htmlElementInstance.setName(elementName);

        HtmlElementDecoratorHandlers.handlerHtmlInstance(htmlElementInstance, elementName, field);

        By by = (By) getFieldValue(locator, "by");
        if (by != null) {
            htmlElementInstance.setBy(by);
        }

        return htmlElementInstance;
    }

    private boolean isDecorateHtmlField(Field field) {

        for (Class elementClass : definedHtmlElementClasses) {
            if (elementClass.isAssignableFrom(field.getType())) return true;

        }
        for (Annotation annotation : field.getDeclaredAnnotations()) {
            if (definedHtmlElementAnnotation.contains(annotation.annotationType())) return true;
        }

        return isHtmlElementList(field) || ReflectionHelper.isGivenClassList(WebElement.class, field);
    }


}
