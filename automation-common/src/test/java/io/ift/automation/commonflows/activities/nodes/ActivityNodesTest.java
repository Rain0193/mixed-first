package io.ift.automation.commonflows.activities.nodes;

import io.ift.automation.flows.activitynodes.MockFinanceNode;
import io.ift.automation.flows.activitynodes.MockFinanceNodeApplyForMultiple;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.*;

/**
 * Created by patrick on 15/8/31.
 */
public class ActivityNodesTest {

    @Test
    public void testGetInstance() throws Exception {
        ActivityNodes instance = ActivityNodes.getInstance();
        Assert.assertNotNull(instance);
    }

    @Test
    public void testGetActivityNodes_null() throws Exception {
        ActivityNodes instance = ActivityNodes.getInstance();
        Assert.assertEquals(instance.getActivityNodes("test__1"), instance.getDefaultActivityNodes());
    }

    @Test
    public void testRegisterNewNode() throws Exception {
        ActivityNodes instance = ActivityNodes.getInstance();
        Map<String, ActivityNode> result = instance.getDefaultActivityNodes();
        result.put("MockFinanceTest", new MockFinanceNode());
        result.put(NodeName.助理确认, new MockFinanceNodeApplyForMultiple());
        Assert.assertEquals(instance.getActivityNodes("test").size(), result.size());
    }

    @Test(dependsOnMethods = "testRegisterNewNode")
    public void testRegisterNewNode_1() throws Exception {
        ActivityNodes instance = ActivityNodes.getInstance();
        Map<String, ActivityNode> result1 = instance.getDefaultActivityNodes();
        result1.put(NodeName.助理确认, new MockFinanceNodeApplyForMultiple());
        Assert.assertEquals(instance.getActivityNodes("test1").size(), result1.size());
        instance.getAllActivityNodes().size();
        instance.registerNewNode("test1", "test00", (urlMapping, context) -> null);
        Assert.assertEquals(instance.getActivityNodes("test1").size(), result1.size()+1);
    }
}
