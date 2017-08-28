package io.ift.automation.testscaffold.testcontext;

import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by patrick on 15/8/20.
 */
public class TestContextHolderTest {

    @Test
    public void testRemove() throws Exception {

    }

    @Test
    public void testGet() throws Exception {
        EventFiringWebDriver driver = TestContextHolder.get();
        driver.close();
    }

    @Test
    public void testGetThreadLevelTestContext() throws Exception {

    }

    @Test
    public void testAddCheckPoint() throws Exception {

    }

    @Test
    public void testAddDataFixture() throws Exception {

    }

    @Test
    public void testRollBackData() throws Exception {

    }

    @Test
    public void testGetDocLogger() throws Exception {

    }
}
