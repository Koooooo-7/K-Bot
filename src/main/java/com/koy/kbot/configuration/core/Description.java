package com.koy.kbot.configuration.core;


import java.lang.annotation.*;

/**
 * description for plugins
 *
 * @author sivyer9303
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Description {

    /**
     * author of plugin
     */
    String author();

    /**
     * description of plugin
     */
    String desc();

    /**
     * example usage of plugin
     */
    String example() default "";

}
