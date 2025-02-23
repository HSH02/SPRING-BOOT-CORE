package com.example.springcore.aop.example2;

import java.lang.annotation.*;

/**
 * 사용자 정의 어노테이션
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Trackable {
    String value() default "";
}
