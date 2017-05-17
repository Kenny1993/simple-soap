package org.simpleframework.soap.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * SOAP 注解
 * Created by Why on 2017/3/10.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Soap {
    /**
     * SOAP 服务名
     */
    String value() default "";
}
