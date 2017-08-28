package io.ift.automation.commonflows.activities.context;

import io.ift.automation.commonflows.activities.nodes.ActivityNodeMode;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by patrick on 15/9/1.
 */
public class ActivityNodeModeTest {

    @Test
    public void testIsApprove() throws Exception {
        Assert.assertTrue(ActivityNodeMode.isApprove("approve"));
        Assert.assertFalse(ActivityNodeMode.isApprove("reject"));
    }

    @Test
    public void testGetNode(){
        Assert.assertEquals(ActivityNodeMode.getNodeModel("approve"),ActivityNodeMode.APPROVE);
        Assert.assertNotEquals(ActivityNodeMode.getNodeModel("reject"), ActivityNodeMode.APPROVE);
        Assert.assertNotEquals(ActivityNodeMode.getNodeModel(""), ActivityNodeMode.APPROVE);
    }
}
