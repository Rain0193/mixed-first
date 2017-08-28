package io.ift.automation.drivers;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class BrowserTypeTest {

    @Test
    public void testGetBrowserType() throws Exception {
        assertEquals(BrowserType.getBrowserType("chrome"),BrowserType.Chrome);
    }

    @Test
    public void testGetBrowserType_default() throws Exception {
        assertEquals(BrowserType.getBrowserType("chromeTT"),BrowserType.Chrome);
    }


    @Test
    public void testGetBrowserType_null() throws Exception {
        assertEquals(BrowserType.getBrowserType(null),BrowserType.Chrome);
    }
}