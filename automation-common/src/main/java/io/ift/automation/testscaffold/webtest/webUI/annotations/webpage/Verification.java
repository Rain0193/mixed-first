package io.ift.automation.testscaffold.webtest.webUI.annotations.webpage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by patrick on 16/3/8.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface Verification {
    String expected() ;
    String whatProperty();
    String pattern() default "";
}
