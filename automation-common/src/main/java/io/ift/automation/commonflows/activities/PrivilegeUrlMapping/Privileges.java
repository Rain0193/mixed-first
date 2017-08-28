package io.ift.automation.commonflows.activities.PrivilegeUrlMapping;

import io.ift.automation.commonflows.Constants;
import io.ift.automation.commonflows.activities.ActivitiesScanner;
import io.ift.automation.commonflows.activities.DefinedActivityName;
import io.ift.automation.commonflows.activities.annotations.ActivityProvider;
import io.ift.automation.commonflows.activities.nodes.NodeName;
import io.ift.automation.helpers.ReflectionHelper;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 权限访问Mapping
 * Created by patrick on 15/8/28.
 */
public class Privileges {

    private Map<String, Map<String, String>> privilegeMapping = Maps.newHashMap();
    private static Privileges instance = new Privileges();

    public Privileges() {
        initDefaultPrivileges();
        generateAllUrlMapping();
    }

    public static Privileges getInstance() {
        if (instance == null) {
            instance = new Privileges();
        }
        return instance;
    }

    /**
     * 初始化默认节点权限
     */
    private void initDefaultPrivileges() {

        Map<String, String> map = Maps.newHashMap();
        map.put(NodeName.验收部门, Constants.SCRAP_CHECKED);
        map.put(NodeName.财务审批, Constants.SCRAP_FAPPROVAL);
        map.put(NodeName.行政确认, Constants.SCRAP_ADAPPROVAL);
        privilegeMapping.put(DefinedActivityName.资产报废
                , map);
    }

    public void register(String activityName, Map<String, String> privileges) {
        privilegeMapping.put(activityName, privileges);
    }

    public Map<String, String> getPrivilegesByActivityName(String activityName) {
        return privilegeMapping.get(activityName);
    }

    private void generateAllUrlMapping() {

        ActivitiesScanner.getActivityMethods().stream()
                .forEach(item -> {
                    ActivityProvider a = item.getAnnotation(ActivityProvider.class);
                    if (a.privilege()) {
                        for (String s : a.activityName()) {
                            Object o = ReflectionHelper.invokeMethod(null, item);
                            if (o != null) {
                                privilegeMapping.put(s, (Map<String, String>) o);
                            }
                        }
                    }
                });
        //todo think about how to init from a class
    }


}
