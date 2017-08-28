package io.ift.automation.commonflows.activities.nodes;

import io.ift.automation.commonflows.activities.ActivitiesScanner;
import io.ift.automation.commonflows.activities.annotations.ActivityProvider;
import io.ift.automation.commonflows.oms.OmsHelper;
import io.ift.automation.helpers.ReflectionHelper;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by patrick on 15/8/27.
 */
public class ActivityNodes {

    private Map<String,ActivityNode> defaultActivityNodes =initDefaultActivities();
    private Map<String,Map<String,ActivityNode>> allActivityNodes ;
    private static final Logger logger = LogManager.getLogger(ActivityNodes.class.getName());
    private static ActivityNodes instance =new ActivityNodes();

    /**
     * 获取实例
     * @return
     */
    public static ActivityNodes getInstance(){
        if (instance==null){
            instance=new ActivityNodes();
        }
        return instance;
    }

    /**
     * 私有的节点实例化入口,没法调用
     */
    private ActivityNodes(){
        allActivityNodes=scanToGetAllActivityNodes();
    }

    /**
     * 根据流程名称获取所有节点,如果没有节点定义则用默认的
     * @param activityName
     * @return
     */
    public Map<String,ActivityNode> getActivityNodes(String activityName){
        Map<String,ActivityNode> result = allActivityNodes.get(activityName);
        if(result==null){
            return defaultActivityNodes;
        }else{
            return allActivityNodes.get(activityName);
        }
    }

    /**
     * 默认的节点
     * @return
     */
    private Map<String,ActivityNode> initDefaultActivities(){
        Map<String,ActivityNode> maps = Maps.newConcurrentMap();
        maps.put(NodeName.采购部门审批, (privilegeUrlMapping,context) ->
                OmsHelper.getEmployeeByPrivilege(privilegeUrlMapping
                        .get(NodeName.采购部门审批)
                        , context.getCompanyName(), context.getAppCode()).subList(0, 1));
        maps.put(NodeName.财务确认, (privilegeUrlMapping,context) ->
                        OmsHelper.getEmployeeByPrivilege(privilegeUrlMapping.get(NodeName.财务确认)
                                , context.getCompanyName(), context.getAppCode()).subList(0, 1)
        );
        maps.put(NodeName.财务审批, (privilegeUrlMapping,context) ->
                OmsHelper.getEmployeeByPrivilege(privilegeUrlMapping.get(NodeName.财务审批)
                        , context.getCompanyName(), context.getAppCode()).subList(0, 1));

        maps.put(NodeName.经理审批, (privilegeUrlMapping,context) ->
                OmsHelper.getManagerByUserCode(context.getApplicantUserCode()));

        maps.put(NodeName.行政确认, (privilegeUrlMapping,context) ->
                OmsHelper.getEmployeeByPrivilege(privilegeUrlMapping.get(NodeName.行政确认)
                        , context.getCompanyName(), context.getAppCode()).subList(0, 1));

        maps.put(NodeName.行政审批, (privilegeUrlMapping, context) ->
                OmsHelper.getEmployeeByPrivilege(privilegeUrlMapping.get(NodeName.行政确认)
                        , context.getCompanyName(), context.getAppCode()).subList(0, 1));
        return maps;

    }

    /**
     * 注册新的节点到ActionNodes
     * @param activityName
     * @param nodeName
     * @param node
     */
    public void registerNewNode(String activityName,String nodeName,ActivityNode node){
        allActivityNodes.get(activityName).put(nodeName, node);
    }

    /**
     * scan to get all activity nodes, activity node is only for class
     * which implement the ActivityNode.
     * 所有扫描得到的结果格式如下,节点名字一样的情况下,如果有新的实现会覆盖默认实现,否则使用默认的实现
     * {
        {流程名1:{节点名1: 节点的实现实例,节点名2: 节点的实现实例}
        }
        ,{流程名2: {节点名1: 节点的实现实例,节点名2: 节点的实现实例}
        }
        }
     * @return Map, 格式是:activityName:{nodeName:nodeImplementation,nodeName:nodeImplementation}
     */
    private Map<String,Map<String,ActivityNode>> scanToGetAllActivityNodes(){
        Map<String,Map<String,ActivityNode>> result =Maps.newConcurrentMap();
        ActivitiesScanner.getActivityNodes().stream().forEach(item -> {
            ActivityProvider provider = (ActivityProvider) item.getAnnotation(ActivityProvider.class);
            for (String name : provider.activityName()) {
                String nodeName = provider.activityNodeName();
                ActivityNode node = null;
                try {
                    node = (ActivityNode) ReflectionHelper.newInstance(item);
                } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                    logger.error("实例化activity node失败,error_result={}", e);
                }
                if (node != null) {
                    if (result.get(name) == null) {
                        Map<String, ActivityNode> maps = Maps.newConcurrentMap();
                        Map<String, ActivityNode> defaultNodesCopy = Maps.newHashMap(defaultActivityNodes);
                        maps.putAll(defaultNodesCopy);
                        maps.put(nodeName, node);
                        result.put(name, maps);
                    } else {
                        result.get(name).put(nodeName, node);
                    }
                }
            }
        });
        return result;
    }

    public Map<String, ActivityNode> getDefaultActivityNodes() {
        return Maps.newHashMap(defaultActivityNodes);
    }


    public Map<String, Map<String, ActivityNode>> getAllActivityNodes() {
        return Maps.newHashMap(allActivityNodes);
    }

}
