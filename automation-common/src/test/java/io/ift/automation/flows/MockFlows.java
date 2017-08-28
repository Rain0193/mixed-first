package io.ift.automation.flows;

import io.ift.automation.commonflows.Constants;
import io.ift.automation.commonflows.activities.DefinedActivityName;
import io.ift.automation.commonflows.activities.annotations.ActivityProvider;
import io.ift.automation.commonflows.activities.nodes.NodeName;
import io.ift.automation.data.TestData;
import io.ift.automation.testscaffold.TestAction;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.util.Map;

/**
 * Created by patrick on 15/8/31.
 */
public class MockFlows {

    private static final Logger logger = LogManager.getLogger(MockFlows.class.getName());

    @ActivityProvider(activityName = "test",privilege = true)
    public static Map<String,String> urlMapping(){
        Map<String,String> map = Maps.newHashMap();
        map.put(NodeName.验收部门 + 1, Constants.SCRAP_CHECKED);
        map.put(NodeName.财务审批+1,Constants.SCRAP_FAPPROVAL);
        map.put(NodeName.行政确认+1,Constants.SCRAP_ADAPPROVAL);
        return map;
    }

    @ActivityProvider(activityName = DefinedActivityName.资产报废,startAction = true)
    public static TestAction createStartAction(WebDriver driver, TestData testData){
        return () -> logger.info("start_action is created");
    }

    @ActivityProvider(activityName = DefinedActivityName.资产报废,restartAction= true)
    public static TestAction createReStartAction(WebDriver driver,TestData testData){
        return () -> logger.info("restart_action is created");
    }

    @ActivityProvider(activityName = DefinedActivityName.资产报废,approveActionFor = NodeName.助理确认)
    public static TestAction createApproveAction(){
        return ()->logger.info("approve action is created");
    }

    @ActivityProvider(activityName = DefinedActivityName.资产报废,rejectActionFor= NodeName.助理确认)
    public static TestAction createRejectAction(){
        return ()->logger.info("reject action is created");
    }

    @ActivityProvider(activityName = {"test2", DefinedActivityName.资产报废},
            approveActionFor = {NodeName.经理审批})
    public static TestAction createGenericApproveAction(){
        return ()->logger.info("approve action is created");
    }

    @ActivityProvider(activityName = {"test3",DefinedActivityName.资产申请}
            ,rejectActionFor= {NodeName.助理确认,"TestNode"})
    public static TestAction createGenericRejectAction(){
        return ()->logger.info("reject action is created");
    }

}
