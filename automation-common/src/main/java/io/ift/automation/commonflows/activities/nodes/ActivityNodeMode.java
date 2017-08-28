package io.ift.automation.commonflows.activities.nodes;


import io.ift.automation.helpers.CollectionsHelper;

import javax.annotation.Nonnull;

/**
 * Created by patrick on 15/8/27.
 */
public enum ActivityNodeMode {
    APPROVE,REJECT,START,RESTART,CANTAPPROVE,CANTREJECT;

    /**
     * 是否是Approve的Action
     * @param actionName
     * @return
     */
    public static boolean isApprove(@Nonnull String actionName){
        if(actionName.equalsIgnoreCase(APPROVE.toString())) return true;
        return false;
    }

    /**
     * 获取节点模式
     * @param modelStatus
     * @return
     */
    public static ActivityNodeMode getNodeModel(@Nonnull String modelStatus){

        return CollectionsHelper.filter(ActivityNodeMode.values(),(item)->
            item.name().equalsIgnoreCase(modelStatus)
        );
    }
}
