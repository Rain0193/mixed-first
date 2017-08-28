package io.ift.automation.testscaffold.webtest.webUI.UIdescription;

import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.*;

public class ElementDescriptionTest {

    @Test
    public void testGetElementDescriptionFromResource() throws Exception {
       Map map = ElementDescription.getElementDescriptionFromResource("pages/BaiduHomePageResource.xml");
        assertNotNull(map);
    }
}