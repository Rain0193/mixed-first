package io.ift.automation.testscaffold.webtest.webUI.elementloader;

import io.ift.automation.testscaffold.BaseWebTest;
import io.ift.automation.helpers.ReflectionHelper;
import io.ift.automation.testscaffold.webtest.webUI.testpages.BaiDuHomeBasePage;

import org.apache.commons.beanutils.BeanUtils;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.testng.Assert.*;

public class ModifiedPageFactoryTest extends BaseWebTest {


    @Test
    public void testCreatePageObject() throws Exception {
        driver.get("http://www.baidu.com");
        BaiDuHomeBasePage page= ModifiedPageFactory.createPageObject(driver, BaiDuHomeBasePage.class);
        page.getSearch().sendKeys("test");
        page.getSearch().submit();
        assertNotNull(page);
    }

    @Test
    public void testCreatePageObject_2() throws Exception {
        driver.get("http://www.baidu.com");
        BaiDuHomeBasePage page= ModifiedPageFactory.createPageObject(driver, BaiDuHomeBasePage.class);
        page.getSearch().sendKeys("test");
        page.getSearch().submit();
        assertNotNull(page);
    }

    @Test
    public void testCreatePageObject_Reflection() throws Exception {
        driver.get("http://www.baidu.com");
        BaiDuHomeBasePage page= ModifiedPageFactory.createPageObject(driver, BaiDuHomeBasePage.class);
        Object o = ReflectionHelper.getFieldValue(page, "search");
        Field f = ReflectionHelper.getAccessibleField(page,"search");
        Object o1 = BeanUtils.getProperty(page,"header.news");

        if(o==null){
            ModifiedPageFactory.populateElementsToInstance(driver, null);
        }

        Method mm = ReflectionHelper.getAccessibleMethodByName(o,"input");
        if(mm!=null){
            mm.invoke(o,"test");
        }
//        page.getSearch().sendKeys("test");
//        page.getSearch().submit();
        assertNotNull(page);
    }



    @Test
    public void testCreatePageObject_xml() throws Exception {
//        driver.get("http://www.baidu.com");
//        BaiDuHomePageXML page= ModifiedPageFactory.createPageObject(driver, BaiDuHomePageXML.class);
//        page.getSearch().sendKeys("test");
//        page.getSearch().submit();
//        assertNotNull(page);
    }


}
