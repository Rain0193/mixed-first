package io.ift.automation.testscaffold.webtest.webUI.htmlelements;

import com.google.common.collect.Maps;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by patrick on 15/3/23.
 *
 * @version $Id$
 */


public class BasePage {
    protected WebDriver driver;

    public BasePage() {
        initWebElementsHolder();
    }

    public BasePage(WebDriver driver) {
        this.driver = driver;
        initWebElementsHolder();
    }

    public String getLocationName() {
        return this.getClass().getSimpleName();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    private Map<String, Method> actionHolder = Maps.newHashMap();
    private Map<String, Field> fieldsHolder = Maps.newHashMap();

    private void initWebElementsHolder() {

        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
//                if(method.getAnnotation(CompositeAction.class)!=null){
            actionHolder.put(method.getName(), method);
//                }
        }

        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            fieldsHolder.put(field.getName(), field);
        }

    }

    public Method getAction(String name) {
        return actionHolder.get(name);
    }

    public Field getField(String name) {
        return fieldsHolder.get(name);
    }
}
