package io.ift.automation.helpers.webdriver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.*;

/**
 * Created by patrick on 15/3/2.
 *
 * @version $Id$
 */

public final class ModifiedExpectedConditions {
    private static final Logger logger = LogManager.getLogger(ModifiedExpectedConditions.class.getName());

    private ModifiedExpectedConditions() {
    }

    /**
     * return element is the element is visible
     *
     * @param element
     * @return
     */
    private static WebElement elementIfVisible(WebElement element) {
        return element.isDisplayed() ? element : null;
    }

    /**
     * 查找元素
     *
     * @param by
     * @param driver
     * @return
     */
    private static WebElement findElement(By by, WebDriver driver) {

        try {
            return driver.findElement(by);
        } catch (NoSuchElementException e) {
            logger.warn("can't find element {}", by);
            throw e;
        } catch (WebDriverException e) {
            logger.warn("WebDriverException={}", e);
            throw e;
        }
    }

    /**
     * 在父容器里面找元素
     *
     * @param by
     * @param parent
     * @return
     */
    private static WebElement findElementFromParent(By by, WebElement parent) {
        try {
            return parent.findElement(by);
        } catch (NoSuchElementException e) {
            logger.warn("can't find element {},exception={}", by, e);
            return null;
        } catch (WebDriverException e) {
            logger.warn("WebDriverException={}", e);
            return null;
        }
    }

    /**
     * 从父容器中找元素列表
     *
     * @param by
     * @param parent
     * @return
     */
    private static List<WebElement> findElements(By by, WebElement parent) {

        try {
            return parent.findElements(by);
        } catch (WebDriverException e) {
            logger.warn("find element failed, WebDriverException ={}", e);
            return null;
        }
    }

    /**
     * element在父容器中可见
     *
     * @param parent
     * @param locator
     * @return
     */
    public static ExpectedCondition<WebElement> visibilityOfElementLocated(final WebElement parent,
                                                                           final By locator) {
        return new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver webDriver) {
                try {
                    return elementIfVisible(findElementFromParent(locator, parent));
                } catch (StaleElementReferenceException e) {
                    logger.warn("exception={}", e);
                    return null;
                }
            }

            public String toString() {
                return "visibility of element located by " + locator;
            }
        };
    }

    /**
     * element在父容器中可点击
     *
     * @param parent
     * @param locator
     * @return
     */
    public static ExpectedCondition<WebElement> elementToBeClickable(final WebElement parent, final By locator) {
        return new ExpectedCondition<WebElement>() {

            public ExpectedCondition<WebElement> visibilityOfElementLocated = visibilityOfElementLocated(parent, locator);

            @Override
            public String toString() {
                return "element to be clickable: " + locator;
            }

            @Override
            public WebElement apply(WebDriver webDriver) {
                WebElement element = visibilityOfElementLocated.apply(webDriver);
                try {
                    if (element != null && element.isEnabled()) {
                        return element;
                    } else {
                        return null;
                    }
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }
        };
    }

    /**
     * catch all exception
     *
     * @param element
     * @return
     */
    public static ExpectedCondition<WebElement> elementToBeClickable(final WebElement element) {
        return new ExpectedCondition<WebElement>() {

            public ExpectedCondition<WebElement> visibilityOfElementLocated =
                    ExpectedConditions.visibilityOf(element);

            @Override
            public String toString() {
                return "element to be clickable: " + element.toString();
            }

            @Override
            public WebElement apply(WebDriver webDriver) {
                try {
                    WebElement element = visibilityOfElementLocated.apply(webDriver);
                    if (element != null && element.isEnabled()) {
                        return element;
                    } else {
                        return null;
                    }
                } catch (Exception e) {
                    return null;
                }
            }
        };
    }

    public static ExpectedCondition<WebElement> presenceOfElementLocated(final WebElement parent,
                                                                         final By locator) {
        return new ExpectedCondition<WebElement>() {
            @Override
            public String toString() {
                return "present of element located by :" + locator;
            }

            @Override
            public WebElement apply(WebDriver webDriver) {

                return findElementFromParent(locator, parent);
            }

        };
    }

    public static ExpectedCondition<Boolean> invisibilityOfElementLocated(final WebElement parent,
                                                                          final By locator) {
        return new ExpectedCondition<Boolean>() {

            public Boolean apply(WebDriver driver) {
                try {
                    if(findElementFromParent(locator,parent)!=null){
                        return !findElementFromParent(locator, parent).isDisplayed();
                    }
                    return true;
                } catch (java.util.NoSuchElementException e) {
                    // Returns true because the element is not present in DOM. The
                    // try block checks if the element is present but is invisible.
                    return true;
                } catch (StaleElementReferenceException e) {
                    // Returns true because stale element reference implies that element
                    // is no longer visible.
                    return true;
                }
            }

            public String toString() {
                return "element to no longer be visible: " + locator;
            }
        };
    }

    public static ExpectedCondition<List<WebElement>> presenceOfAllElementsLocatedBy(
            final WebElement parent, final By locator) {
        return new ExpectedCondition<List<WebElement>>() {

            public List<WebElement> apply(WebDriver driver) {
                List<WebElement> elements = findElements(locator, parent);
                return !elements.isEmpty()? elements : Collections.EMPTY_LIST;
            }


            public String toString() {
                return "presence of any elements located by " + locator;
            }
        };
    }

    public static ExpectedCondition<Boolean> elementSelectionStateToBe(final WebElement parent,
                                                                       final By locator, final boolean selected) {
        return new ExpectedCondition<Boolean>() {

            public Boolean apply(WebDriver driver) {
                try {
                    WebElement element = findElementFromParent(locator, parent);
                    return element.isSelected() == selected;
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            public String toString() {
                return String.format("element found by %s to %sbe selected",
                        locator, (selected ? "" : "not "));
            }
        };
    }

    public static ExpectedCondition<Boolean> textToBePresentInElement(final WebElement parent,
                                                                      final By locator, final String text) {

        return new ExpectedCondition<Boolean>() {

            public Boolean apply(WebDriver driver) {
                try {
                    String elementText = findElementFromParent(locator, parent).getText();
                    return elementText.contains(text);
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            public String toString() {
                return String.format("text ('%s') to be present in element found by %s",
                        text, locator);
            }
        };
    }


    public static ExpectedCondition<Boolean> invisibilityOfElementWithText(final WebElement parent,
                                                                           final By locator, final String text) {
        return new ExpectedCondition<Boolean>() {

            public Boolean apply(WebDriver driver) {
                try {
                    return !findElementFromParent(locator, parent).getText().equals(text);
                } catch (NoSuchElementException e) {
                    // Returns true because the element with text is not present in DOM. The
                    // try block checks if the element is present but is invisible.
                    return true;
                } catch (StaleElementReferenceException e) {
                    // Returns true because stale element reference implies that element
                    // is no longer visible.
                    return true;
                }
            }

            public String toString() {
                return String.format("element containing '%s' to no longer be visible: %s",
                        text, locator);
            }
        };
    }

    public static ExpectedCondition<Boolean> attributeValueToBePresentInElement(
            final By locator, final String attributeName, final String attributeValue) {

        return new ExpectedCondition<Boolean>() {

            public Boolean apply(WebDriver driver) {
                try {
                    String elementText = findElement(locator, driver).getAttribute(attributeName);
                    if (elementText != null) {
                        return elementText.contains(attributeValue);
                    } else {
                        return false;
                    }
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            public String toString() {
                return String.format(
                        "Attribute value ('%s') of the attribute name ('%s') of element located by %s",
                        attributeValue, attributeName, locator);
            }
        };
    }

    public static ExpectedCondition<Boolean> attributeValueToBePresentInElement(
            final WebElement parent, final By locator, final String attributeName,
            final String attributeValue) {

        return new ExpectedCondition<Boolean>() {

            public Boolean apply(WebDriver driver) {
                try {
                    String elementText = findElementFromParent(locator, parent).getAttribute(attributeName);
                    if (elementText != null) {
                        return elementText.contains(attributeValue);
                    } else {
                        return false;
                    }
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            public String toString() {
                return String.format(
                        "Attribute value ('%s') of the attribute name ('%s') of element located by %s",
                        attributeValue, attributeName, locator);
            }
        };
    }
}
