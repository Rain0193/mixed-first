package io.ift.automation.commonflows;

import io.ift.automation.commonflows.models.EmployeeTestData;
import io.ift.automation.data.TestData;
import io.ift.automation.helpers.StringHelper;
import io.ift.automation.testscaffold.TestAction;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.openqa.selenium.WebDriver;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 此类用来构建工作流
 * Created by patrick on 15/8/17.
 */
public class WorkFlowBuilder {

    private WorkFlowExecutor instance;
    private WebDriver driver;
    private TestData testData;

    private WorkFlowBuilder(){

    }

    public static WorkFlowBuilder build(WebDriver driver,TestData testData){
        WorkFlowBuilder builder = new WorkFlowBuilder();
        builder.instance =new WorkFlowExecutor();
        builder.driver=driver;
        builder.testData=testData;
        return builder;
    }

    public WorkFlowBuilder add(TestAction action, List<List<EmployeeTestData>>...approvals){
        for (List<List<EmployeeTestData>> approval : approvals) {
            this.instance.actionMap.put(approval, action);
        }

        return this;
    }

    public void process(){

        this.instance.process(driver,testData);
    }

    public WorkFlowExecutor getInstance() {
        return instance;
    }

    public static class WorkFlowExecutor{

        private Map<List<List<EmployeeTestData>>,TestAction> actionMap = Maps.newLinkedHashMap();
        private LinkedList<String> approvalsChain;

        public WorkFlowExecutor() {
            approvalsChain = Lists.newLinkedList();
        }

        protected void buildApprovalsChain(){

           Set<List<List<EmployeeTestData>>> allEmployees = actionMap.keySet();
            for (List<List<EmployeeTestData>> allEmployee : allEmployees) {
                for (List<EmployeeTestData> employees : allEmployee) {
                    approvalsChain.addAll(employees.stream()
                            .map(EmployeeTestData::getUserCode)
                            .collect(Collectors.toList()));
                }
            }
        }

        public void process(WebDriver driver,TestData testData){
            buildApprovalsChain();

            for (Map.Entry<List<List<EmployeeTestData>>, TestAction> action : actionMap.entrySet()) {
                for (List<EmployeeTestData> functionOrg : action.getKey()) {
                    for (EmployeeTestData employee : functionOrg) {
                        setApprovalsToTestData(testData, employee);
                        DooiooLoginHelper.login(employee, driver);
                        action.getValue().execute();
                        //quick and dirty to pass current employee use code
                        DooiooLoginHelper.logout(driver);
                    }
                }
            }
        }

        protected void setApprovalsToTestData(TestData testData, EmployeeTestData employee) {
            testData.set("approvedEmployee", employee.getUserCode());
            approvalsChain.pop();
            if(approvalsChain.isEmpty()) {
                testData.set("nextApproval",StringHelper.EMPTY);
            }else{
                testData.set("nextApproval", approvalsChain.getFirst());
            }
        }

        public Map<List<List<EmployeeTestData>>, TestAction> getActionMap() {
            return actionMap;
        }

        public void setActionMap(Map<List<List<EmployeeTestData>>, TestAction> actionMap) {
            this.actionMap = actionMap;
        }
    }
}
