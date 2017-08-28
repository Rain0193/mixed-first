package io.ift.automation.testscaffold.webtest.webUI.elementloader;

import io.ift.automation.data.TestData;
import io.ift.automation.helpers.FileHelper;
import io.ift.automation.helpers.StringHelper;
import io.ift.automation.testscaffold.webtest.webUI.UIdescription.ElementDescription;
import io.ift.automation.testscaffold.webtest.webUI.annotations.webpage.PageDescription;
import io.ift.automation.testscaffold.webtest.webUI.elementloader.decorator.HtmlElementFieldDecorator;
import io.ift.automation.testscaffold.webtest.webUI.elementloader.internal.HtmlElementsFactory;
import io.ift.automation.testscaffold.webtest.webUI.elementloader.locator.CustomerElementLocatorFactory;
import io.ift.automation.testscaffold.webtest.webUI.htmlelements.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.Map;

/**
 * Created by patrick on 15/3/23.
 *
 * @version $Id$
 */


public class ModifiedPageFactory {

    private static final String PD_PATH = "pages/";

    private ModifiedPageFactory() {
    }

    /**
     * 创建一个 webpage 实例，同时初始化所有的页面的初始元素
     *
     * @param driver
     * @param webPage
     * @return
     */
    public static <T extends BasePage> T createPageObject(WebDriver driver, Class<T> webPage) {
        /**
         1. load Data from resource files xml/yaml
         2. 调用initElement 方法init elements
         */
        PageDescription po = webPage.getAnnotation(PageDescription.class);
        if (po == null) {
            return createPOWithOutPageDescriptionAnnotation(driver, webPage);
        } else {
            return createPOWithPageDescriptionAnnotation(driver, webPage, po,null);
        }
    }

    /**
     * 创建一个 webpage 实例，同时初始化所有的页面的初始元素
     *
     * @param driver
     * @param webPage
     * @return
     */
    public static <T extends BasePage> T createPageObject(WebDriver driver, Class<T> webPage, TestData testData) {
        /**
         1. load Data from resource files xml/yaml
         2. 调用initElement 方法init elements
         */
        PageDescription po = webPage.getAnnotation(PageDescription.class);
        if (po == null) {
            return createPOWithOutPageDescriptionAnnotation(driver, webPage,testData);
        } else {
            return createPOWithPageDescriptionAnnotation(driver, webPage, po,testData);
        }
    }

    @Deprecated
    private static <T extends BasePage> T createPOWithPageDescriptionAnnotation(WebDriver driver,
                                                                                Class<T> webPage, PageDescription po
                                                                                ,TestData data) {
        Map<String, ElementDescription> elementDescriptions = ElementDescription
                .getElementDescriptionFromResource(getPageObjectResourcePath(po, webPage));
        return populateElements(driver, webPage, elementDescriptions,data);
    }

    private static <T> T createPOWithOutPageDescriptionAnnotation(WebDriver driver, Class<T> webPage) {
        //init element with ElementLocatorFactory
        return populateElements(driver, webPage,null);

    }

    private static <T> T createPOWithOutPageDescriptionAnnotation(WebDriver driver, Class<T> webPage, TestData data) {
        //init element with ElementLocatorFactory
        return populateElements(driver, webPage,data);

    }

    public static <T extends BasePage> void populateElementsToInstance(WebDriver driver, T elementObject) {
        PageDescription po = elementObject.getClass().getAnnotation(PageDescription.class);
        if (po != null) {
            Map<String, ElementDescription> elementDescriptions = ElementDescription
                    .getElementDescriptionFromResource(getPageObjectResourcePath(po, elementObject.getClass()));
            HtmlElementFieldDecorator
                fieldDecorator = new HtmlElementFieldDecorator(new CustomerElementLocatorFactory(driver, elementDescriptions));
            PageFactory.initElements(fieldDecorator, elementObject);
        } else {
            HtmlElementFieldDecorator fieldDecorator = new HtmlElementFieldDecorator(new CustomerElementLocatorFactory(driver));
            PageFactory.initElements(fieldDecorator, elementObject);
        }

    }

    public static <T> T populateElements(WebDriver driver, Class<T> elementObject, TestData data) {
        T instance = HtmlElementsFactory.createPageObjectInstance(elementObject, driver);
        HtmlElementFieldDecorator fieldDecorator = getHtmlElementFieldDecorator(driver, data);
        PageFactory.initElements(fieldDecorator, instance);
        return instance;
    }

    private static HtmlElementFieldDecorator getHtmlElementFieldDecorator(WebDriver driver, TestData data) {
        HtmlElementFieldDecorator fieldDecorator = null;
        if (data == null) {
            fieldDecorator = new HtmlElementFieldDecorator(new CustomerElementLocatorFactory(driver));
        } else {
            fieldDecorator = new HtmlElementFieldDecorator(new CustomerElementLocatorFactory(driver, data));
        }
        return fieldDecorator;
    }

    public static <T> T populateElements(CustomerElementLocatorFactory factory, Class<T> pageSection) {
        T instance = HtmlElementsFactory.createPageSectionObjectInstance(pageSection);
        HtmlElementFieldDecorator fieldDecorator = new HtmlElementFieldDecorator(factory);
        PageFactory.initElements(fieldDecorator, instance);
        return instance;
    }

    public static <T> T populateElements(WebDriver driver, Class<T> elementObject
            , Map<String, ElementDescription> elementDescriptions, TestData data) {

        T instance = HtmlElementsFactory.createPageObjectInstance(elementObject, driver);
        HtmlElementFieldDecorator fieldDecorator = new HtmlElementFieldDecorator(
                new CustomerElementLocatorFactory(driver, elementDescriptions, data));
        PageFactory.initElements(fieldDecorator, instance);
        return instance;
    }

    @Deprecated
    private static <T> String getPageObjectResourcePath(PageDescription po, Class<T> webPage) {
        String path = po.descriptionLocation();
        if (StringHelper.isEmpty(path)) {
            String className = webPage.getClass().getSimpleName();
            if (FileHelper.existsInClasspath(PD_PATH + className + ".xml")) {
                path = PD_PATH + className + ".xml";
            } else {
                if (FileHelper.existsInClasspath(PD_PATH + className + ".yaml")) {
                    path = PD_PATH + className + ".yaml";
                } else {
                    throw new RuntimeException("please provide element description file " +
                            "or remove the page object annotation for page " + className);
                }
            }
        }

        return path;
    }


}
