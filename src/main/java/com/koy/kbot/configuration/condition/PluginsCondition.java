package com.koy.kbot.configuration.condition;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.HashSet;
import java.util.Map;

/**
 * @Description
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/16
 */
public class PluginsCondition implements Condition {
    @Override
    public boolean matches(@NotNull ConditionContext conditionContext, @NotNull AnnotatedTypeMetadata annotatedTypeMetadata) {

        Map<String, Object> attributes = annotatedTypeMetadata.getAnnotationAttributes(PluginsCondition.class.getName());

        String propertyName = conditionContext.getEnvironment().getProperty("name");
        String value = (String) attributes.get("havingValue");

        HashSet<String> plugins = new HashSet<>();
        int i = 0;
        for (; ; ) {
            String plugin = conditionContext.getEnvironment().getProperty(propertyName + "[" + i++ + "]", String.class);
            if (plugin == null) {
                break;
            }
            plugins.add(plugin.toUpperCase());
        }
        return plugins.contains(value.toUpperCase());
    }
}
