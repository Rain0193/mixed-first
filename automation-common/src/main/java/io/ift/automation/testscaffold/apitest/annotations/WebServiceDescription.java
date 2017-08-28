package io.ift.automation.testscaffold.apitest.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by patrick on 15/8/13.
 *
 * @version $Id: WebServiceDescription.java 2048 2015-11-23 05:24:21Z wuke $
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Deprecated
public @interface WebServiceDescription {
    String url() ;
    String[] queryParameter() default {} ;
    String[] pathParameter() default {};
    boolean hasBody() default false;
    String contentType() default "";
    String acceptType() default "";
}
