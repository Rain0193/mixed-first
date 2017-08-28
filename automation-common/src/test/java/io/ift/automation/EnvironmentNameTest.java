package io.ift.automation;

import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * Created by patrick on 15/7/2.
 *
 * @version $Id: EnvironmentNameTest.java 1771 2015-08-21 06:02:12Z wuke $
 */
public class EnvironmentNameTest {

    @Test
    public void testGetEnvironmentName() throws Exception {
        Assert.assertEquals(Environment.EnvironmentName.INTEGRATION.toString(),"INTEGRATION");
    }
}
