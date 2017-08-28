package io.ift.automation.testscaffold.webtest.webUI.elementloader.invocationhandler;

import io.ift.automation.drivers.DriverFactory;
import io.ift.automation.testscaffold.webtest.webUI.elementloader.locator.CustomerElementByLocator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class WebElementNamedProxyHandlerTest {
    private WebElement tested;


    @Test
    public void testInvoke() throws Exception {

        Field field = this.getClass().getDeclaredField("tested");
        System.out.println(field.getDeclaringClass());
        ElementLocator locator = new CustomerElementByLocator(DriverFactory.get(), field);

        InvocationHandler handler = new WebElementNamedProxyHandler(locator, "test");
        WebElement element = (WebElement) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{WebElement.class, WrapsElement.class, Locatable.class}, handler);
        System.out.println(element);
        System.out.println(element.toString());

    }
}
