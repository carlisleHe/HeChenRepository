package com.cib.alipayserver.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExecuteResult {
    String name();
    int code() default -1;
    int GRID_EXCEPTION_CODE =520;
    String JSON_ERROR="jsonError";
}
