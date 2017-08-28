package io.ift.automation.testscaffold.webtest.webUI.elementloader.invocationhandler;

import io.ift.automation.helpers.webdriver.WebDriverHelper;
import io.ift.automation.testscaffold.webtest.webUI.elementloader.locator.CustomerElementByLocator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WebElementNamedProxyHandler implements InvocationHandler {
    private final ElementLocator locator;
    private final String name;
    private static final Logger logger = LogManager.getLogger(WebElementNamedProxyHandler.class.getName());
    //todo
    public WebElementNamedProxyHandler(ElementLocator locator, String name) {
        this.locator = locator;
        this.name = name;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

        try {
            return _invoke(o, method, objects);
        } catch (InvocationTargetException e) {
            logger.info("check if stale element exception: {}",
                    e.getTargetException() instanceof StaleElementReferenceException);
            logger.info("error={} occurred,element is not attached,try to retry to find element {} for method {}", e,
                    o, method.getName());
            WebDriverHelper.waitForSubmit(3000L);
            ((CustomerElementByLocator) locator).setShouldCache(false);
            try {
                return _invoke(o, method, objects);
            } catch (Exception n) {
                logger.info("请检查当前元素{}的定位信息,元素类型是否正确,方法{} 调用是否正确", o, method.getName());
                throw new RuntimeException(e);
            }
        }
    }

    private Object _invoke(Object o, Method method, Object[] objects) throws InvocationTargetException,
            IllegalAccessException {
        logger.debug("start using web element proxy......");
        if ("toString".equals(method.getName())) {
            return name;
        }
        //todo rebuild the by if needed
        ((CustomerElementByLocator) locator).rebuildByIfNeeded();
        WebElement element = locator.findElement();
        if ("getWrappedElement".equals(method.getName())) {
            return element;
        }
        return method.invoke(element, objects);
    }
}
