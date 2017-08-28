package io.ift.automation.commonflows.activities;

import io.ift.automation.commonflows.activities.annotations.ActivityProvider;

import com.google.common.collect.Maps;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by patrick on 15/8/31.
 */
public class ActivityNodeActions {

    private Map<String, Map<String, Method>> allApproveAction = Maps.newConcurrentMap();
    private Map<String, Map<String, Method>> allRejectAction = Maps.newConcurrentMap();
    private Map<String, Method> allStartAction = Maps.newConcurrentMap();
    ;
    private Map<String, Method> allReStartAction = Maps.newConcurrentMap();
    private static ActivityNodeActions instance = new ActivityNodeActions();

//    private static final Logger logger = LogManager.getLogger(ActivityNodeActions.class.getName());

    public static ActivityNodeActions getInstance() {
        if (instance == null) {
            instance = new ActivityNodeActions();
        }

        return instance;
    }

    private ActivityNodeActions() {
        getAllApproveAndRejectActions();
    }

    /**
     * 
     * @param activityName
     * @return
     */
    public Map<String, Method> getApproveActionByActivityName(String activityName) {
        return allApproveAction.get(activityName);
    }

    /**
     *
     * @param activityName
     * @return
     */
    public Map<String, Method> getRejectActionByActiveName(String activityName) {
        return allRejectAction.get(activityName);
    }

    /**
     *
     * @param activityName
     * @return
     */
    public Method getStartActionByActivityName(String activityName) {
        return allStartAction.get(activityName);
//        return MapsHelper.getNotNullObject(allStartAction,activityName,activityName+"开始操作没有找到");
    }

    public Method getRestartActionByActivityName(String activityName) {
        return allReStartAction.get(activityName);
    }

    /**
     * 获取同意和拒绝的测试代码
     */
    private void getAllApproveAndRejectActions() {

        ActivitiesScanner.getActivityMethods().stream().forEach(item -> {
            ActivityProvider provider = item.getAnnotation(ActivityProvider.class);
            if (provider.approveActionFor().length > 0 || provider.rejectActionFor().length > 0) {
                for (String activityName : provider.activityName()) {
                    if (provider.approveActionFor().length > 0) {
                        getActionFor(allApproveAction, item, provider.approveActionFor(), activityName);
                    }
                    if (provider.rejectActionFor().length > 0) {
                        getActionFor(allRejectAction, item, provider.rejectActionFor(), activityName);
                    }
                }
            }

            if (provider.startAction()) {
                for (String activityName : provider.activityName()) {
                    allStartAction.put(activityName, item);
                }
            }

            if (provider.restartAction()) {
                for (String activityName : provider.activityName()) {
                    allReStartAction.put(activityName, item);
                }
            }
        });

    }

    /**
     *  根据注解ActionProvider提供的信息,将设定的approve方法,或者reject方法转化为Map,map的格式是:
     *  {审批流名称,{审批节点名称1=审批节点处理函数1,审批节点名称2=审批节点处理函数2}}
     * @param source
     * @param item
     * @param nodesName
     * @param activityName
     */
    private void getActionFor(Map<String, Map<String, Method>> source, Method item,
                              String[] nodesName, String activityName) {
        Map<String, Method> actions = source.get(activityName);
        if (null == actions) {
            actions = Maps.newHashMap();
            source.put(activityName, actions);
        }
        for (String name : nodesName) {
            actions.put(name, item);
        }
    }

}
