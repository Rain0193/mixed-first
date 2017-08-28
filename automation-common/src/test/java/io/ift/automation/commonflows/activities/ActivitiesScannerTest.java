package io.ift.automation.commonflows.activities;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by patrick on 15/9/1.
 */
public class ActivitiesScannerTest {

    @Test
    public void testGetActivityMethods() throws Exception {
        Assert.assertTrue(ActivitiesScanner.getActivityMethods().size()>=1);
    }

    @Test
    public void testGetActivityNodes() throws Exception {
        Assert.assertTrue(ActivitiesScanner.getActivityNodes().size()>=1);
    }
}
