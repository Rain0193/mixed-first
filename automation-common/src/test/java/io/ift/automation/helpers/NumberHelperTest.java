package io.ift.automation.helpers;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by patrick on 15/11/25.
 */
public class NumberHelperTest {

    @Test
    public void testParseDouble2Digital() throws Exception {
        Assert.assertEquals(new Double("1.03"), NumberHelper.parseDouble2Digital("1.026"));
    }

    @Test
    public void testParseDoubleToGivenScale() throws Exception {
        Assert.assertEquals(new Double("1.023"),NumberHelper.parseDoubleToGivenScale("1.023",3));
    }
}
