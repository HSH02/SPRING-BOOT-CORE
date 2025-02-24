package com.example.springcore.aop.example6;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// @Inherited 없이 정의 – 즉, 자식 클래스는 상속받지 않습니다.
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Trackable {
    String value() default "";
}
