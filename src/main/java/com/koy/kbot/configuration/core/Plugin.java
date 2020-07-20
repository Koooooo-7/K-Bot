package com.koy.kbot.configuration.core;

import java.lang.annotation.*;

/**
 * @Description  must add @Inherited to avoid losing annotation by CGlib
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/07/18
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Plugin {

    // the plugin name
    String name() default "";

    // how to call the plugin normally
    String call();

    // fast commands
    String[] fastCommand() default {};
}
