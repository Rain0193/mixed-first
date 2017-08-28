package io.ift.automation.commonflows.activities;

import io.ift.automation.commonflows.activities.annotations.ActivityProvider;
import io.ift.automation.helpers.ClassFinderHelper;
import io.ift.automation.helpers.CollectionsHelper;
import com.google.common.collect.Lists;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * 扫描activities相关的节点
 * Created by patrick on 15/8/28.
 */
public class ActivitiesScanner {

    public static final String ACTIVITY_SCAN_PATH = "com.dooioo.automation.flows";
    private static final String activityNodePath = "com.dooioo.automation.flows.activitynodes";
    private static List<Method> activityMethods = Lists.newArrayList(); //TestAction And urlMapping
    private static List<Class> activityNodes;  // activityNode

    static {

        scan();
    }

    private ActivitiesScanner() {
    }

    /**
     * 扫描指定路径的文件
     */
    private static void scan() {
        List<Class> flows = ClassFinderHelper.getClassListByPackageName(ACTIVITY_SCAN_PATH, false);
        List<Class> nodeList = ClassFinderHelper.getClassListByPackageName(activityNodePath, false);
        for (Class flow : flows) {
            Method[] methods = flow.getDeclaredMethods();
            activityMethods.addAll(CollectionsHelper.filterForList(methods,
                    method -> method.getAnnotation(ActivityProvider.class) != null));
        }

        activityNodes = nodeList.stream()
                .filter(item ->
                        item.getAnnotation(ActivityProvider.class) != null)
                .collect(Collectors.toList());

    }

    /**
     * 获取所有的方法
     * @return
     */
    public static List<Method> getActivityMethods() {
        return activityMethods;
    }

    /**
     * 获取所有的节点
     * @return
     */
    public static List<Class> getActivityNodes() {
        return activityNodes;
    }

}
