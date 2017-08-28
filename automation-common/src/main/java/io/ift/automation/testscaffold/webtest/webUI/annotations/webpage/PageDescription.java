package io.ift.automation.testscaffold.webtest.webUI.annotations.webpage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by patrick on 15/3/18.
 * 用来指定描述页面的XML/YAML文件的位置
 * @version $Id$
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface PageDescription {
    String descriptionLocation() default "";
}
