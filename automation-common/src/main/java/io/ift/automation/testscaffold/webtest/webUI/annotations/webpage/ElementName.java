package io.ift.automation.testscaffold.webtest.webUI.annotations.webpage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by patrick on 15/3/24.
 *  预留注解名字，没有实际使用
 * @version $Id$
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface ElementName {
    String elementName() default "";
}
