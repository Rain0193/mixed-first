package io.ift.automation.testscaffold;

import io.ift.automation.assertion.SoftAssertion;
import io.ift.automation.drivers.DriverFactory;
import io.ift.automation.helpers.CollectionsHelper;
import io.ift.automation.helpers.webdriver.WebDriverHelper;
import io.ift.automation.logging.TestResultLogger;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by patrick on 15/4/2.
 * Deprecated it as it is not used so much
 * @version $Id$
 */

@Deprecated
public class TestActionsExecutor {

    protected List<TestAction> testSteps = Lists.newArrayList();
    protected List<CheckPoint> checkPoints = Lists.newArrayList();
    protected SoftAssertion testResult = new SoftAssertion();
    protected List<TestAction> afterActions = Lists.newArrayList();
    protected List<Function> hookableFunctionList = Lists.newArrayList();

    public static TestActionsExecutor build(TestAction...actions){
        TestActionsExecutor builder =  new TestActionsExecutor();
        builder.beforeAddSteps();
        builder.addTestSteps(actions);
        return builder;
    }

    private void beforeAddSteps(){}
    /**
     * 执行所有加入到testSteps里面的TestAction，
     * TestAction的颗粒度可大可小：可以是一个点击，可以是一连串的页面操作，可以是一个Flow
     * 也可以是一个API的call
     */
    public TestActionsExecutor execute() {

        for (TestAction testStep : testSteps) {
            TestResultLogger.log("start {} Testing.....", testStep.getTestActionName());
            testStep.execute();
            for (Function function : hookableFunctionList) { // transform some test data; may be useless
                TestResultLogger.log("start hook {} ",testStep.getTestActionName());
                function.apply(testStep);
            }
            processCheck(testStep);
            WebDriverHelper.waitForSubmit(4000L);
        }

        return this;
    }

    /**
     * 根据检查点检查
     * @param testStep
     */
    protected TestActionsExecutor processCheck(TestAction testStep) {
        CheckPoint cp = getCheckPoint(testStep);
        if(cp!=null){
            if(testStep.getTestContext()!=null){
                TestResultLogger.log("setting test context: {}",testStep.getTestContext().toString());
                cp.setContext(testStep.getTestContext());
                cp.verify();
            }
            testResult.addTestResult(cp.getTestResult());
            // setting the thread level flag for execution,if the flag is true, then exit all the testing flows
            if(cp.isExist()) DriverFactory.getThreadLevelTestContext().setNotQuit(false);
        }

        return this;
    }

    /**
     * 得到测试结果
     * @return SoftAssertion
     */
    public SoftAssertion getTestResult() {
        return testResult;
    }


    /**
     * 增加测试的步骤
     * @param steps
     * @return TestActionExecutor
     */
    public TestActionsExecutor addTestSteps(TestAction...steps){
        CollectionsHelper.addAll(testSteps,steps);
        return this;
    }

//    /**
//     * 增加
//     * @param after
//     * @return
//     */
//    public TestActionsExecutor addAfterActions(TestAction...after){
//        CollectionsHelper.addAll(afterActions,after);
//        return this;
//    }

    /**
     * 更具TestAction获取检查点
     * @param testAction
     * @return CheckPoint
     */
    private CheckPoint getCheckPoint(TestAction testAction){

     return  CollectionsHelper.filter(checkPoints, new Predicate<CheckPoint>() {
         @Override
         public boolean apply(CheckPoint checkPoint) {
             TestResultLogger.log("start verify checkpoint after {}",testAction.getTestActionName());
             return checkPoint.where().eval(testAction.getTestActionName());
         }
     });
    }

    /**
     * 复位ActionBuilder里面的动作
     */
    public void resetActionBuilder(){
        testSteps = Lists.newArrayList();
        checkPoints = Lists.newArrayList();
        testResult = new SoftAssertion();
        afterActions = Lists.newArrayList();
    }

    public TestActionsExecutor addCheckPoints(CheckPoint...checkPoints){
        CollectionsHelper.addAll(this.checkPoints,checkPoints);
        return this;
    }

    public List<TestAction> getTestSteps() {
        return testSteps;
    }

    public void setTestSteps(List<TestAction> testSteps) {
        this.testSteps = testSteps;
    }

    public List<CheckPoint> getCheckPoints() {
        return checkPoints;
    }

    public void setCheckPoints(List<CheckPoint> checkPoints) {
        this.checkPoints = checkPoints;
    }

    public void setTestResult(SoftAssertion testResult) {
        this.testResult = testResult;
    }

    public List<TestAction> getAfterActions() {
        return afterActions;
    }

    public void setAfterActions(List<TestAction> afterActions) {
        this.afterActions = afterActions;
    }

    public List<Function> getHookableFunctionList() {
        return hookableFunctionList;
    }

    public void setHookableFunctionList(List<Function> hookableFunctionList) {
        this.hookableFunctionList = hookableFunctionList;
    }
}
