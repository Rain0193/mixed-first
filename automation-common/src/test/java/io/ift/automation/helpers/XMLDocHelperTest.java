package io.ift.automation.helpers;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class XMLDocHelperTest {

    @Test
    public void testBuild() throws Exception {
       XMLDocHelper helper = XMLDocHelper.build("pages/baiduHomePageResource.xml");
       assertNotNull(helper);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testBuild_exception() throws Exception {
        XMLDocHelper helper = XMLDocHelper.build("pages/aiduHomePageResource.xml");
        assertNotNull(helper);
    }

    @Test
    public void testGetText() throws Exception {
        assertEquals(XMLDocHelper.build("pages/BaiduHomePageResource.xml").getText("keyword"),"id=\"kw\"");
    }

    @Test
    public void testGetAttribute() throws Exception {
        assertEquals(XMLDocHelper.build("pages/BaiduHomePageResource.xml").getAttribute("keyword","desc"),"keyword");
    }

    @Test
    public void testGetAttributes() throws Exception {
        assertEquals(XMLDocHelper.build("pages/BaiduHomePageResource.xml").
                getAttributes("keyword").get("desc"),"keyword");
    }

    @Test
    public void testGetElementNameAndTextMap() throws Exception {
        assertEquals(XMLDocHelper.build("pages/BaiduHomePageResource.xml").getNameAndTextForAllElements()
                .get("keyword"),"id=\"kw\"");
    }

}
