package io.ift.automation.commonflows.activities.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * activityName: 审批流名称
 * activityNodeName: 审批节点名称
 * startAction: 审批流开始业务流程
 * restartAction: 审批流打回后重新开始业务流程
 * approveActionFor: 分派审核通过的业务动作到不同的业务节点
 * rejectActionFor:分派审核不通过的业务动作到不同的业务节点
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
public @interface ActivityProvider {
    String[] activityName() ;
    String activityNodeName() default "";
    boolean privilege() default false;
    boolean startAction() default false;
    boolean restartAction() default false;
    String[] approveActionFor() default {};
    String[] rejectActionFor() default {};

}
