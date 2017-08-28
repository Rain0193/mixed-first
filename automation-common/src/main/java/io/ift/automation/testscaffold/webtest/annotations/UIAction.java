package io.ift.automation.testscaffold.webtest.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by patrick on 15/3/21.
 * 单个页面测试流程描述
 * @version $Id$
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
public @interface UIAction {
    String processName() ;
    String[] elementActionDescription(); //Description should be element_name action_name
}
