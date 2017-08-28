package io.ift.automation.testscaffold;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by patrick on 15/8/17.
 */
public class WhereTest {

    @Test
    public void testEval() throws Exception {
        Assert.assertFalse(new Where("Application.submit").eval("tese"));
    }
}
