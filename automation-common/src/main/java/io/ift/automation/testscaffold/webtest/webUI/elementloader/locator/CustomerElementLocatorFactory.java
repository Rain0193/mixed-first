package io.ift.automation.testscaffold.webtest.webUI.elementloader.locator;
/**
 * Created by patrick on 15-3-24.
 */

import io.ift.automation.data.TestData;
import io.ift.automation.helpers.ReflectionHelper;
import io.ift.automation.testscaffold.webtest.webUI.UIdescription.ElementDescription;
import io.ift.automation.testscaffold.webtest.webUI.annotations.webpage.GivenLocator;
import io.ift.automation.testscaffold.webtest.webUI.exceptions.HtmlElementsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class CustomerElementLocatorFactory implements ElementLocatorFactory {

    private static final Logger LOGGER = LogManager.getLogger(CustomerElementLocatorFactory.class);
    private WebDriver driver;
    private Map<String, ElementDescription> elementDescriptions;
    private TestData testData;

    public CustomerElementLocatorFactory(WebDriver driver) {
        this.driver = driver;
    }

    public CustomerElementLocatorFactory(WebDriver driver,TestData testData) {
        this.driver = driver;
        this.testData=testData;
    }


    public CustomerElementLocatorFactory(WebDriver driver, Map<String
            , ElementDescription> elementDescriptions) {
        this.driver = driver;
        this.elementDescriptions = elementDescriptions;
    }
    public CustomerElementLocatorFactory(WebDriver driver, Map<String
            , ElementDescription> elementDescriptions,TestData testData) {
        this.driver = driver;
        this.testData=testData;
        this.elementDescriptions = elementDescriptions;
    }

    @Override
    public ElementLocator createLocator(Field field) {
        GivenLocator givenLocator = field.getAnnotation(GivenLocator.class);
        if (givenLocator != null) {
            try {
                LOGGER.info("using locator {} for field {}", givenLocator.locator().getSimpleName(), field.getName());
                if (elementDescriptions != null) {
                    return (ElementLocator) ReflectionHelper.
                            newInstance(givenLocator.locator(), driver, field
                                    , elementDescriptions.get(field.getName()));
                } else {
                    return (ElementLocator) ReflectionHelper.newInstance(givenLocator.locator(), driver, field);
                }

            } catch (IllegalAccessException
                    | InstantiationException
                    | NoSuchMethodException
                    | InvocationTargetException e) {
                LOGGER.error(e);
            }
            //fail over to use default element locator if give class is not existing
            return createElementLocatorByDescription(field);
        } else {
            return createElementLocatorByDescription(field);
        }
    }

    private ElementLocator createElementLocatorByDescription(Field field) {
        LOGGER.debug("finding locator CustomerElementByLocator");
        CustomerElementByLocator locator =null;
        if (elementDescriptions != null) {
            if (elementDescriptions.get(field.getName()) == null) {
                throw new HtmlElementsException("please check you element description file, there is no matched elements for " + field.getName());
            }
            locator= new CustomerElementByLocator(driver, field, elementDescriptions.get(field.getName()));
            locator.setTestData(this.testData);
        } else {
            locator=new CustomerElementByLocator(driver, field);
            locator.setTestData(this.testData);
        }
        return locator;
    }

    public void setTestData(TestData testData) {
        this.testData = testData;
    }
}
