package io.ift.automation.commonflows.activities;

import io.ift.automation.commonflows.activities.PrivilegeUrlMapping.Privileges;
import io.ift.automation.commonflows.activities.context.ActivityContext;
import io.ift.automation.commonflows.activities.nodes.ActivityNode;
import io.ift.automation.commonflows.activities.nodes.ActivityNodeMode;
import io.ift.automation.commonflows.activities.nodes.ActivityNodes;
import io.ift.automation.commonflows.models.EmployeeTestData;
import io.ift.automation.helpers.PropertiesHelper;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created by patrick on 15/8/27.
 */
public class ActivityProviders {

    private ActivityContext context;
    private Map<String,String> privilegeUrlMapping;
    private Map<String,ActivityNode> actionNodes ;
    private Map<String, Method> approveActions;
    private Map<String, Method> rejectActions;
    private Method startAction;
    private Method restartAction;
    private static final Logger logger = LogManager.getLogger(ActivityProviders.class.getName());

    /**
     * 根据上下文创建ActivityProvider
     * @param context
     */
    public ActivityProviders(ActivityContext context) {
        this.context = context;
        this.privilegeUrlMapping = Privileges.getInstance().getPrivilegesByActivityName(context.getActivityName());
        this.actionNodes=ActivityNodes.getInstance().getActivityNodes(context.getActivityName());
        this.approveActions=ActivityNodeActions.getInstance()
                .getApproveActionByActivityName(context.getActivityName());
        this.rejectActions=ActivityNodeActions.getInstance()
                .getRejectActionByActiveName(context.getActivityName());
        this.startAction =ActivityNodeActions.getInstance()
                .getStartActionByActivityName(context.getActivityName());
        this.restartAction= ActivityNodeActions.getInstance()
                .getRestartActionByActivityName(context.getActivityName());

        //test if it is loaded will, fail fast
        List<String> errorMessage = Lists.newArrayList();
        if(PropertiesHelper.getProperty("prevalidate_activitynode","true").equalsIgnoreCase("true")){
            for (String s : context.getActivityActionList()){
                String[] steps= s.split(":");
                errorMessage.addAll(validateUsedAction(steps[0],steps[1]));
            }
        }

        if(errorMessage.size()>0) {
            for (String s : errorMessage) {
                logger.error("工作流{},没有配置成功,错误信息={}",context.getActivityName(),s);
            }

        }
        assert errorMessage.size()==0;

    }

    /**
     * 验证工作流的配置是否正确
     * @param nodeName
     * @param nodeMode
     * @return
     */
    private List<String> validateUsedAction(String nodeName,String nodeMode){
        List<String> errorMessage = Lists.newArrayList();
        if(nodeMode.equalsIgnoreCase(ActivityNodeMode.START.toString())){
            if(startAction==null) errorMessage.add(context.getActivityName()+"开始操作没有设置,请检查相应的流程代码");
        }

        if(nodeMode.equalsIgnoreCase(ActivityNodeMode.RESTART.toString())){
            if(restartAction==null) errorMessage.add(context.getActivityName()+"重新操作没有设置,请检查相应的流程代码");
        }

        if(nodeMode.equalsIgnoreCase(ActivityNodeMode.APPROVE.toString())||
                nodeMode.equalsIgnoreCase(ActivityNodeMode.CANTAPPROVE.toString())||
                nodeMode.equalsIgnoreCase(ActivityNodeMode.CANTREJECT.toString())){
            Method m = getApproveActionByNode(nodeName);
            if(m==null){
                errorMessage.add(context.getActivityName()+"."+nodeName+"没有配置审批成功的操作");
            }
            try {
                actionNodes.get(nodeName).approvals(privilegeUrlMapping(), context);
            }catch (Exception e){
                errorMessage.add(nodeName+"获取审批人失败,error_result="+e);
            }
        }

        if(nodeMode.equalsIgnoreCase(ActivityNodeMode.REJECT.toString())){
            Method m = getRejectActionByNode(nodeName);
            if(m==null){
                errorMessage.add(context.getActivityName()+"."+nodeName+"没有配置打回成功的操作");
            }
            try {
                actionNodes.get(nodeName).approvals(privilegeUrlMapping(), context);
            }catch (Exception e){
                errorMessage.add(nodeName+"获取审批人失败,error_result="+e);
            }
        }

        return errorMessage;
    }

    /**
     * 审批流开始人
     * @return
     */
    public EmployeeTestData startEmployee(){
        return new EmployeeTestData(context.getStartEmployee());
    }

    /**
     * 审批流重新开始人,一般和开始人一样
     * @return
     */
    public EmployeeTestData restartEmployee(){
        return new EmployeeTestData(context.getStartEmployee());
    }

    public Map<String,String> privilegeUrlMapping(){
        return this.privilegeUrlMapping;
    }

    public Map<String,ActivityNode> getActivityNodes(){
       return this.actionNodes;
    }

    public  Map<String, Method> getApproveActions(){
        return this.approveActions;
    }

    public  Map<String, Method> getRejectActions(){
        return this.rejectActions;
    }

    public Method getStartAction(){

        return this.startAction;
    }

    public Method getRestartAction(){
        return this.restartAction;
    }

    public ActivityContext getContext() {
        return context;
    }

    public void setContext(ActivityContext context) {
        this.context = context;
    }

    /**
     * 获取ActivityNode
     * @param nodeName
     * @return
     */
    public ActivityNode getActivityNode(String nodeName){
        ActivityNode node = this.actionNodes.get(nodeName);
//        if(node==null) throw new RuntimeException(context.getActivityName()+"."+nodeName+"没有配置相应的审批节点");
        return node;
    }

    /**
     * 获取节点审批通过的Action
     * @param nodeName
     * @return
     */
    public Method getApproveActionByNode(String nodeName){
         Method m = this.approveActions.get(nodeName);
//        if(m==null) throw new RuntimeException(context.getActivityName()+"."+nodeName+"没有配置审批成功的操作");
        return m;
    }

    /**
     * 获取节点审批不通过的Action
     * @param nodeName
     * @return
     */
    public Method getRejectActionByNode(String nodeName){
        Method m = this.rejectActions.get(nodeName);
//        if(m==null) throw new RuntimeException(context.getActivityName()+"."+nodeName+"没有配置审批拒绝的操作");
        return m;
    }

//    public Map<String,String> activityStepDescription(){
//        return context.getActivityActionMapping();
//    }

    /**
     * 所有审批节点
     * @return
     */
    public List<String> activitySteps(){
        return context.getActivityActionList();
    }

    /**
     * 获取但个审批节点的审批人
     * @param nodeName
     * @return
     */
    public List<EmployeeTestData> getNodeApprovals(String nodeName){
       return this.getActivityNode(nodeName)
                .approvals(privilegeUrlMapping(), context);

    }

    /**
     * 当前节点
     * @return
     */
    public String currentNode(){
        return this.context.getCurrentNodeName();
    }
}
