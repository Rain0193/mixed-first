package io.ift.automation.testscaffold.webtest.webUI.htmlelements;

import io.ift.automation.testscaffold.BaseWebTest;
import io.ift.automation.testscaffold.webtest.actions.WebTestActionBuilder;
import io.ift.automation.testscaffold.webtest.webUI.mockpages.MockPage1;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Created by patrick on 15/8/17.
 */
public class BasePageTest extends BaseWebTest {

    @Test
    public void testInvokeTest() throws InvocationTargetException, IllegalAccessException {
       MockPage1 page =  WebTestActionBuilder.use(MockPage1.class, driver);
        Method m = (Method) page.getAction("testMethodInvoke");
        m.invoke(page,"testing");
    }
}
