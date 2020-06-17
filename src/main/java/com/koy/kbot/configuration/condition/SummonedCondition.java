package com.koy.kbot.configuration.condition;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.HashSet;
import java.util.Map;

/**
 * @Description the solver of ConditionalOnSummoned
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/16
 */
public class SummonedCondition implements Condition {
    @Override
    public boolean matches(@NotNull ConditionContext conditionContext, @NotNull AnnotatedTypeMetadata annotatedTypeMetadata) {

        Map<String, Object> attributes = annotatedTypeMetadata.getAnnotationAttributes(SummonedCondition.class.getName());

        boolean matchIfMissing = (boolean) attributes.get("matchIfMissing");
        if (matchIfMissing) {
            return true;
        }

        // get the value of name property
        String propertyName = conditionContext.getEnvironment().getProperty("name");
        // get the value of havingValue property
        String value = (String) attributes.get("havingValue");

        HashSet<String> plugins = new HashSet<>();
        int i = 0;
        for (; ; ) {
            // get all values from the <propertyName> list in yaml config file
            String plugin = conditionContext.getEnvironment().getProperty(propertyName + "[" + i++ + "]", String.class);
            if (plugin == null) {
                break;
            }
            plugins.add(plugin.toUpperCase());
        }
        return plugins.contains(value.toUpperCase());
    }
}
