package io.ift.automation.helpers.refectiontest;

import io.ift.automation.helpers.ReflectionHelper;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

/**
 * Created by patrick on 15/8/28.
 */
public class StaticMethodTest {

    @Test
    public void test_static_method(){
        Method[] methods = TestStatic.class.getDeclaredMethods();
        for (Method method : methods) {
            String test = (String) ReflectionHelper.invokeMethod(null, method);
            System.out.println(test);
        }
    }
}
