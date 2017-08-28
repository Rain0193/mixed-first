package io.ift.automation.testscaffold.webtest.webUI.elementloader.locator;

import io.ift.automation.data.TestData;
import io.ift.automation.helpers.StringHelper;
import io.ift.automation.helpers.webdriver.WebDriverHelper;
import io.ift.automation.testscaffold.webtest.annotations.FindByRefer;
import io.ift.automation.testscaffold.webtest.webUI.UIdescription.ElementDescription;
import com.google.common.base.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.Annotations;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by patrick on 15/3/24.
 * 支持Selenium 默认的annotation by等去找元素
 *
 * @version $Id$
 */


public class CustomerElementByLocator implements ElementLocator {

    private final WebDriver driver; //webdriver
    private boolean shouldCache = true;
    protected By by;
    private WebElement cachedElement;
    private List<WebElement> cachedElementList;
    private Function<WebDriver, WebElement> findElementFunction;
    private Function<WebDriver, List<WebElement>> findElementsFunction;
    private static final Logger logger = LogManager.getLogger(CustomerElementByLocator.class.getName());
    private String referTo; //todo add refer to into locator
    private Field field;
    private boolean isByRebuilt = false;
    private TestData testData;

    /**
     * locator for by annotations
     *
     * @param driver
     * @param field
     */
    public CustomerElementByLocator(WebDriver driver,
                                    Field field) {
        this(driver, new Annotations(field));
        this.field = field;
        this.referTo = field.getAnnotation(FindByRefer.class) == null ?
                       StringHelper.EMPTY : field.getAnnotation(FindByRefer.class).referTo();
        initDefaultFindElementFunctions();
    }

    /**
     * locator for page object description
     *
     * @param driver
     * @param field
     * @param description
     */
    public CustomerElementByLocator(WebDriver driver,
                                    Field field, ElementDescription description) {
        this(driver, new Annotations(field), description);
        initDefaultFindElementFunctions();
    }

    public CustomerElementByLocator(WebDriver searchContext, Annotations annotations) {
        this.driver = searchContext;
        this.by = annotations.buildBy();
    }

    public CustomerElementByLocator(WebDriver searchContext, Annotations annotations
            , ElementDescription description) {
        this.driver = searchContext;
        this.by = description.buildBy();
    }

    private void initDefaultFindElementFunctions() {
        findElementFunction = new Function<WebDriver, WebElement>() {
            @Nullable
            @Override
            public WebElement apply(@Nullable WebDriver input) {
                return WebDriverHelper.findElement(input, by);
            }
        };

        findElementsFunction = new Function<WebDriver, List<WebElement>>() {
            @Nullable
            @Override
            public List<WebElement> apply(@Nullable WebDriver input) {
                return WebDriverHelper.findElements(input, by);
            }
        };
    }

    @Override
    public WebElement findElement() {

        if (cachedElement != null && shouldCache) {
            return cachedElement;
        }
        logger.debug("start find webElement {}", this.by);
        WebElement element = findElementFunction.apply(driver);
        logger.debug("end find webElement {}", this.by);
        if (shouldCache) {
            cachedElement = element;
        } else {
            this.shouldCache = true; //set to default value : true
        }
        return element;
    }

    @Override
    public List<WebElement> findElements() {
        if (cachedElementList != null && shouldCache) {
            return cachedElementList;
        }
        List<WebElement> elements = findElementsFunction.apply(driver);
        if (shouldCache) {
            cachedElementList = elements;
        } else {
            this.shouldCache = true; //set to default value : true
        }
        return elements;
    }

    public By getBy() {
        return by;
    }


    public void setShouldCache(boolean shouldCache) {
        this.shouldCache = shouldCache;
    }

    public boolean rebuildByIfNeeded() {

        if (StringHelper.isEmpty(this.referTo)) return false;
        if (isByRebuilt) return true;
        FindBy findBy = this.field.getAnnotation(FindBy.class);
        String data = null;
        if (testData != null) {
            data = this.testData.get(this.referTo);
        }
        if (data == null) return false;
        String replacedReferTo = "\\{" + referTo + "\\}";
        if (!"".equals(findBy.className())) {
            this.by = By.className(findBy.className().replaceAll(replacedReferTo, data.toString()));
        } else if (!"".equals(findBy.css())) {
            this.by = By.cssSelector(findBy.css().replaceAll(replacedReferTo, data.toString()));
        } else if (!"".equals(findBy.id())) {
            this.by = By.id(findBy.id().replaceAll(replacedReferTo, data.toString()));
        } else if (!"".equals(findBy.linkText())) {
            this.by = By.linkText(findBy.linkText().replaceAll(replacedReferTo, data.toString()));
        } else if (!"".equals(findBy.name())) {
            this.by = By.name(findBy.name().replaceAll(replacedReferTo, data.toString()));
        } else if (!"".equals(findBy.partialLinkText())) {
            this.by = By.partialLinkText(findBy.partialLinkText().replaceAll(replacedReferTo, data.toString()));
        } else if (!"".equals(findBy.tagName())) {
            this.by = By.tagName(findBy.tagName().replaceAll(replacedReferTo, data.toString()));
        } else if (!"".equals(findBy.xpath())) {
            this.by = By.xpath(findBy.xpath().replaceAll(replacedReferTo, data.toString()));
        }
        this.isByRebuilt = true;
        return this.isByRebuilt;
    }

    public void setTestData(TestData testData) {
        this.testData = testData;
    }
}
