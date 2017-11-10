package com.tcvideo.core.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 皮肤注解
 *
 * Created by caiteng on 2017-09-15.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface skin {

    public enum Color{RED,GREEN,BLUE,BLACK,YELLOW,WHILE}

    Color skinColor() default Color.WHILE;
}

