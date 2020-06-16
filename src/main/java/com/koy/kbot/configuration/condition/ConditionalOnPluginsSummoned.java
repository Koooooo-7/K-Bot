package com.koy.kbot.configuration.condition;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Conditional(PluginsCondition.class)
public @interface ConditionalOnPluginsSummoned {

    String name() default "";

    String havingValue() default "";

    boolean matchIfMissing() default false;



}
