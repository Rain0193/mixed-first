package io.ift.automation.commonflows.activities.context;

import io.ift.automation.commonflows.activities.nodes.ActivityNodeMode;
import io.ift.automation.commonflows.models.EmployeeTestData;
import io.ift.automation.data.TestData;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;

/**
 * Created by patrick on 15/8/27.
 */
public class ActivityContext extends TestData {

    private List<String> activityActionList = Lists.newArrayList();
    private String startEmployee;
    private String appCode;
    private String activityName;
    private String companyName="德佑"; //default name
    private String currentNodeName;
    private EmployeeTestData processedEmployee;
    private ActivityNodeMode currentStatus;
    private String taskTableName;
    private String taskHistoryTableName;
    private String taskKeyName;


    public static ActivityContext build(String activityName){
        ActivityContext context = new ActivityContext();
        context.activityName = activityName;
        context.activityActionList.add("START:START");
        return context;
    }

    public ActivityContext appendActivity(String nodeName,ActivityNodeMode mode){
        this.activityActionList.add(nodeName+":"+mode.toString());
        return this;
    }

    public String getApplicantUserCode() {
        return startEmployee;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCurrentNodeName() {
        return currentNodeName;
    }

    public void setCurrentNodeName(String currentNodeName) {
        this.currentNodeName = currentNodeName;
    }

    public EmployeeTestData getProcessedEmployee() {
        return processedEmployee;
    }

    public void setProcessedEmployee(EmployeeTestData processedEmployee) {
        this.processedEmployee = processedEmployee;
    }

    public ActivityNodeMode getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(ActivityNodeMode currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getStartEmployee() {
        return startEmployee;
    }

    public void setStartEmployee(String startEmployee) {
        this.startEmployee = startEmployee;
    }

    public String getTaskTableName() {
        return taskTableName;
    }

    public void setTaskTableName(String taskTableName) {
        this.taskTableName = taskTableName;
    }

    public String getTaskHistoryTableName() {
        return taskHistoryTableName;
    }

    public void setTaskHistoryTableName(String taskHistoryTableName) {
        this.taskHistoryTableName = taskHistoryTableName;
    }

    public String getTaskKeyName() {
        return taskKeyName;
    }

    public void setTaskKeyName(String taskKeyName) {
        this.taskKeyName = taskKeyName;
    }

    public List<String> getActivityActionList() {
        return activityActionList;
    }

    public void setActivityActionList(List<String> activityActionList) {
        this.activityActionList = activityActionList;
    }
}
