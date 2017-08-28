package io.ift.automation.flows.activitynodes;

import io.ift.automation.commonflows.activities.annotations.ActivityProvider;
import io.ift.automation.commonflows.activities.context.ActivityContext;
import io.ift.automation.commonflows.activities.nodes.ActivityNode;
import io.ift.automation.commonflows.models.EmployeeTestData;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;


/**
 * Created by patrick on 15/8/31.
 */

@ActivityProvider(activityName = "test",activityNodeName = "MockFinanceTest")
public class MockFinanceNode implements ActivityNode {
    @Override
    public List<EmployeeTestData> approvals(Map<String, String> urlMapping,
                                            ActivityContext context) {
        return Lists.newArrayList(new EmployeeTestData("121345"));
    }
}
