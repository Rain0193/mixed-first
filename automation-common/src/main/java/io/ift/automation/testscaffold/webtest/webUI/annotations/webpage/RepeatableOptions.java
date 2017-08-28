package io.ift.automation.testscaffold.webtest.webUI.annotations.webpage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by patrick on 15/6/26.
 *
 * @version $Id: RepeatableOptions.java 1869 2015-09-23 01:31:59Z wuke $
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface RepeatableOptions {
    int initCount() default 0;
    String repeatTriggerXpathLocator() default "";
    String offsetXPathForRepeatableRoot() default "";
}
