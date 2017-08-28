package io.ift.automation.testscaffold.webtest.webUI.annotations.webpage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by patrick on 15/3/21.
 * 预留注解，没有使用
 * @version $Id$
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface PageSectionObject {
    String descriptionLocation() default "";
}
