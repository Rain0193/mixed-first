package io.ift.automation.testscaffold.webtest.webUI.annotations.webpage;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by patrick on 15/9/30.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface PreconditionElement {
    String actionName() default "";
    String elementXpathLocator() default "";
}
