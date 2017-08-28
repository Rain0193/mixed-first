package io.ift.automation.flows.activitynodes;

import io.ift.automation.commonflows.activities.DefinedActivityName;
import io.ift.automation.commonflows.activities.annotations.ActivityProvider;
import io.ift.automation.commonflows.activities.context.ActivityContext;
import io.ift.automation.commonflows.activities.nodes.ActivityNode;
import io.ift.automation.commonflows.activities.nodes.NodeName;
import io.ift.automation.commonflows.models.EmployeeTestData;
import io.ift.automation.commonflows.oms.OmsHelper;

import java.util.List;
import java.util.Map;

/**
 * Created by patrick on 15/9/2.
 */

@ActivityProvider(activityName = DefinedActivityName.资产报废
        ,activityNodeName = NodeName.经理审批)
public class ManagerActivityNode implements ActivityNode {

    @Override
    public List<EmployeeTestData> approvals(Map<String, String> urlMapping, ActivityContext context) {
        return OmsHelper.getManagerByUserCode(context.getApplicantUserCode());
    }
}
