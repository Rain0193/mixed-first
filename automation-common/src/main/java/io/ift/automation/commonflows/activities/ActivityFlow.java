package io.ift.automation.commonflows.activities;

import io.ift.automation.commonflows.DooiooLoginHelper;
import io.ift.automation.commonflows.activities.checkpoints.DefaultActivityCantProcessCheckPoint;
import io.ift.automation.commonflows.activities.checkpoints.DefaultActivityProcessCheckPoint;
import io.ift.automation.commonflows.activities.context.ActivityContext;
import io.ift.automation.commonflows.activities.nodes.ActivityNodeMode;
import io.ift.automation.commonflows.models.EmployeeTestData;
import io.ift.automation.data.TestData;
import io.ift.automation.drivers.DriverFactory;
import io.ift.automation.helpers.ReflectionHelper;
import io.ift.automation.logging.TestResultLogger;
import io.ift.automation.testscaffold.TestAction;
import com.google.common.collect.Maps;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created by patrick on 15/8/27.
 *
 * @author patrick
 * @version $Id: $Id
 */
public class ActivityFlow {

    private WebDriver driver;
    private TestData testData;
    private Map<String, TestAction> handlers = Maps.newHashMap();

    private ActivityProviders activityProvider;

    /**
     * <p>build.</p>
     *
     * @param driver a {@link org.openqa.selenium.WebDriver} object.
     * @param testData a {@link TestData} object.
     * @param provider a {@link ActivityProviders} object.
     * @return a {@link ActivityFlow} object.
     */
    public static ActivityFlow build(WebDriver driver, TestData testData, ActivityProviders provider) {
        return new ActivityFlow(driver, testData, provider);
    }

    /**
     * <p>build.</p>
     *
     * @param driver a {@link org.openqa.selenium.WebDriver} object.
     * @param testData a {@link TestData} object.
     * @param context a {@link ActivityContext} object.
     * @return a {@link ActivityFlow} object.
     */
    public static ActivityFlow build(WebDriver driver, TestData testData, ActivityContext context) {
        return new ActivityFlow(driver, testData, new ActivityProviders(context));
    }


    private ActivityFlow(WebDriver driver, TestData testData, ActivityProviders activityProvider) {
        this.activityProvider = activityProvider;
        this.testData = testData;
        this.driver = driver;
        initActivityNodeHandlers();
    }

    /**
     * <p>addDefaultCantProcessCP.</p>
     *
     * @param pageClass a {@link java.lang.Class} object.
     * @return a {@link ActivityFlow} object.
     */
    public ActivityFlow addDefaultCantProcessCP(Class pageClass){
        DriverFactory
            .getThreadLevelTestContext().addCheckPoint(new DefaultActivityCantProcessCheckPoint(activityProvider,
                                                                                                driver, pageClass));
        return this;
    }

    /**
     * <p>addDefaultProcessCP.</p>
     *
     * @param pageClass a {@link java.lang.Class} object.
     * @return a {@link ActivityFlow} object.
     */
    public ActivityFlow addDefaultProcessCP(Class pageClass){
        DriverFactory.getThreadLevelTestContext().addCheckPoint(new DefaultActivityProcessCheckPoint(activityProvider,
                                                                                                     testData, pageClass));
        return this;
    }


//    public ActivityFlow process() {
//
//        for (Map.Entry<String, String> nodeActionDesc : activityProvider.activityStepDescription().entrySet()) {
//            TestResultLogger.log("开始执行={},模式={}",nodeActionDesc.getKey(),nodeActionDesc.getValue());
//            activityProvider.getContext().setCurrentNodeName(nodeActionDesc.getKey());
//            handlers.get(nodeActionDesc.getValue()).execute();
//            TestResultLogger.log("执行={},模式={} 结束", nodeActionDesc.getKey(), nodeActionDesc.getValue());
//        }
//
//        return this;
//    }

    /**
     * <p>process.</p>
     *
     * @return a {@link ActivityFlow} object.
     */
    public ActivityFlow process() {
        for (String s : activityProvider.activitySteps()) {

            String[] steps= s.split(":");
            TestResultLogger.log("开始执行={},模式={}", steps[0], steps[1]);
            activityProvider.getContext().setCurrentNodeName(steps[0]);
            handlers.get(steps[1]).execute();
            TestResultLogger.log("执行={},模式={} 结束", steps[0], steps[1]);

        }
        return this;
    }

    private boolean processTestAction(EmployeeTestData employee, Method testAction) {
        DooiooLoginHelper.login(employee, driver);
        try {
            ReflectionHelper.invokeMethod(null, testAction
                    , driver, testData);
        } catch (Exception e) {
            return false;
        }
        DooiooLoginHelper.logout(driver);
        return true;
    }

    /**
     * init activity node handlers
     */
    private void initActivityNodeHandlers() {

        handlers.put(ActivityNodeMode.START.name(), ()
                -> {
            activityProvider.getContext().setProcessedEmployee(activityProvider.startEmployee());
            activityProvider.getContext().setCurrentStatus(ActivityNodeMode.START);
            processTestAction(activityProvider.startEmployee(), activityProvider.getStartAction());
        });

        handlers.put(ActivityNodeMode.APPROVE.name(), () -> {
            String nodeName = activityProvider.currentNode();
            List<EmployeeTestData> employees = activityProvider.getNodeApprovals(nodeName);
            for (EmployeeTestData employee : employees) {
                activityProvider.getContext().setProcessedEmployee(employee);
                activityProvider.getContext().setCurrentStatus(ActivityNodeMode.APPROVE);
                processTestAction(employee, activityProvider.getApproveActionByNode(nodeName));
            }
        });

        handlers.put(ActivityNodeMode.CANTAPPROVE.name(), () -> {
            String nodeName = activityProvider.currentNode();
            List<EmployeeTestData> employees = activityProvider.getNodeApprovals(nodeName);
            EmployeeTestData employee = employees.get(0);
            activityProvider.getContext().setProcessedEmployee(employee);
            activityProvider.getContext().setCurrentStatus(ActivityNodeMode.CANTAPPROVE);
            processTestAction(employee, activityProvider.getApproveActionByNode(nodeName));
        });

        handlers.put(ActivityNodeMode.CANTREJECT.name(), () -> {
            String nodeName = activityProvider.currentNode();
            List<EmployeeTestData> employees = activityProvider.getNodeApprovals(nodeName);
            EmployeeTestData employee = employees.get(0);
            activityProvider.getContext().setProcessedEmployee(employee);
            activityProvider.getContext().setCurrentStatus(ActivityNodeMode.CANTREJECT);
            processTestAction(employee, activityProvider.getApproveActionByNode(nodeName));
        });

        handlers.put(ActivityNodeMode.RESTART.name(), ()
                -> processTestAction(activityProvider.startEmployee()
                , activityProvider.getRestartAction()));

        handlers.put(ActivityNodeMode.REJECT.name(), () -> {
            String nodeName = activityProvider.currentNode();
            List<EmployeeTestData> employees = activityProvider.getNodeApprovals(nodeName);
            if (employees.size() == 1) {
                activityProvider.getContext().setProcessedEmployee(employees.get(0));
                activityProvider.getContext().setCurrentStatus(ActivityNodeMode.REJECT);
                processTestAction(employees.get(0), activityProvider.getRejectActionByNode(nodeName));

            } else if (employees.size() > 1) { //default the second approvals reject the node,quick and dirty solution
                activityProvider.getContext().setProcessedEmployee(employees.get(0));
                activityProvider.getContext().setCurrentStatus(ActivityNodeMode.APPROVE);
                processTestAction(employees.get(0), activityProvider.getApproveActionByNode(nodeName));

                activityProvider.getContext().setProcessedEmployee(employees.get(1));
                activityProvider.getContext().setCurrentStatus(ActivityNodeMode.REJECT);
                processTestAction(employees.get(1), activityProvider.getRejectActionByNode(nodeName));
            }
        });
    }


}
