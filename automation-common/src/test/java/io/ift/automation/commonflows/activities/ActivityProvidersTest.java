package io.ift.automation.commonflows.activities;

import io.ift.automation.commonflows.Constants;
import io.ift.automation.commonflows.activities.context.ActivityContext;
import io.ift.automation.commonflows.activities.nodes.ActivityNode;
import io.ift.automation.commonflows.activities.nodes.ActivityNodeMode;
import io.ift.automation.commonflows.activities.nodes.NodeName;
import io.ift.automation.commonflows.models.EmployeeTestData;
import com.google.common.collect.Maps;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;


/**
 * Created by patrick on 15/8/28.
 */
public class ActivityProvidersTest {

    ActivityContext context = new ActivityContext();
    ActivityProviders provider;

    @BeforeTest
    public void setUp(){
        context.setCurrentNodeName(NodeName.助理确认);
        context.setStartEmployee("110863");
        context.setProcessedEmployee(new EmployeeTestData("110863"));
        context.setCurrentStatus(ActivityNodeMode.CANTAPPROVE);
        context.setActivityName(DefinedActivityName.资产报废);
        context.setCurrentNodeName("test");
        provider=new ActivityProviders(context);

    }

    @Test
    public void testStartEmployee() throws Exception {
        Assert.assertEquals(provider.startEmployee().getUserCode(), "110863");
    }

    @Test
    public void testRestartEmployee() throws Exception {
        Assert.assertEquals(provider.restartEmployee().getUserCode(),"110863");
    }

    @Test
    public void testPrivilegeUrlMapping() throws Exception {
        Map<String, String> map = Maps.newHashMap();
        map.put(NodeName.验收部门, Constants.SCRAP_CHECKED);
        map.put(NodeName.财务审批, Constants.SCRAP_FAPPROVAL);
        map.put(NodeName.行政确认, Constants.SCRAP_ADAPPROVAL);
        Assert.assertEquals(provider.privilegeUrlMapping(), map);
    }

    @Test
    public void testGetActivityNodes() throws Exception {
       Map<String,ActivityNode> nodes= provider.getActivityNodes();
       Assert.assertEquals(nodes.size(),6);
    }

    @Test
    public void testGetApproveActions() throws Exception {
        Assert.assertEquals(provider.getApproveActions().size(),2);
        for (Map.Entry<String, Method> entry : provider.getApproveActions().entrySet()) {
            System.out.printf(entry.getKey()+"="+entry.getValue().getName());
        }
    }

    @Test
    public void testGetRejectActions() throws Exception {
        Assert.assertEquals(provider.getRejectActions().size(),1);
        for (Map.Entry<String, Method> entry : provider.getRejectActions().entrySet()) {
            System.out.printf(entry.getKey()+"="+entry.getValue().getName());
        }
    }

    @Test
    public void testGetStartAction() throws Exception {
        Assert.assertNotNull(provider.getStartAction());
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testGetStartAction_exception() throws Exception {
        ActivityProviders provider1= new ActivityProviders(new ActivityContext());
        provider1.getStartAction();
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testGetRestartAction_exception() throws Exception {
        ActivityProviders provider1= new ActivityProviders(new ActivityContext());
        provider1.getRestartAction();
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testGetActivityNode_exception() throws Exception {
       ActivityNode node = provider.getActivityNode(NodeName.助理确认);
       Assert.assertNotNull(node);
    }

    @Test
    public void testGetActivityNode() throws Exception {
        ActivityNode node = provider.getActivityNode(NodeName.行政审批);
        Assert.assertNotNull(node);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testGetApproveActionByNode_expception() throws Exception {
        Method nodeAction = provider.getApproveActionByNode(NodeName.行政审批);
        Assert.assertNotNull(nodeAction.getName());
    }

    @Test
    public void testGetApproveActionByNode() throws Exception {
        Method nodeAction = provider.getApproveActionByNode(NodeName.助理确认);
        Assert.assertNotNull(nodeAction.getName());
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testGetRejectActionByNode_exception() throws Exception {
        Method nodeAction = provider.getRejectActionByNode(NodeName.行政审批);
        Assert.assertNotNull(nodeAction.getName());
    }

    @Test
    public void testGetRejectActionByNode() throws Exception {
        Method nodeAction = provider.getRejectActionByNode(NodeName.助理确认);
        Assert.assertNotNull(nodeAction.getName());
    }

    @Test
    public void testActivityStepDescription() throws Exception {
      List<String> map =  provider.activitySteps();
        Assert.assertNotNull(map);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testGetNodeApprovals_exception() throws Exception {
       provider.getNodeApprovals(NodeName.助理确认);
    }

    @Test
    public void testGetNodeApprovals() throws Exception {
        List<EmployeeTestData> employee = provider.getNodeApprovals(NodeName.经理审批);
        Assert.assertTrue(employee.size()>=1);
    }
    @Test
    public void testCurrentNode() throws Exception {
       Assert.assertEquals(provider.currentNode(),"test");
    }
}
