package io.ift.automation.commonflows.activities;

import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * Created by patrick on 15/9/1.
 */
public class ActivityNodeActionsTest {

    @Test
    public void testGetInstance() throws Exception {
        Assert.assertNotNull(ActivityNodeActions.getInstance());
    }

    @Test
    public void testGetApproveActionByActivityName() throws Exception {

    }

    @Test
    public void testGetRejectActionByActiveName() throws Exception {

    }

    @Test
    public void testGetStartActionByActivityName() throws Exception {

    }

    @Test
    public void testGetRestartActionByActivityName() throws Exception {

    }
}
