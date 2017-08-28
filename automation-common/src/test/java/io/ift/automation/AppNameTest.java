package io.ift.automation;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by patrick on 15/8/11.
 *
 * @version $Id: AppNameTest.java 1714 2015-08-13 05:17:20Z wuke $
 */
public class AppNameTest {

    @Test
    public void testGetName() throws Exception {
        Assert.assertEquals(AppName.FANGYUAN.getName(),"FANGYUAN");
    }
}
