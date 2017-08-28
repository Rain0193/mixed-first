package io.ift.automation.drivers;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class WebDriverModeTest {

    @Test
    public void testGetWebDriverMode() throws Exception {
        assertEquals(WebDriverMode.getWebDriverMode("local"),WebDriverMode.LOCAL);
    }

    @Test
    public void testGetWebDriverMode_default() throws Exception {
        assertEquals(WebDriverMode.getWebDriverMode("local1"),WebDriverMode.LOCAL);
    }
}