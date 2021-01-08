package com.koy.kbot.beanprocessor;

import com.koy.kbot.configuration.core.Description;
import com.koy.kbot.holder.DescriptionHolder;
import com.koy.kbot.configuration.core.Plugin;
import com.koy.kbot.plugins.IPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * bean processor of Description annotation.
 * to add Description annotation field that descrip a plugin's info to Collection.
 * the Description field will use in introduction plugin to descrip other plugins.
 *
 * @author sivyer9303
 */
@Component
public class DescriptionBeanProcessor implements BeanPostProcessor {

    @Autowired
    private DescriptionHolder descriptionHolder;

    /**
     * before bean initialization,put plugin info into map
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (bean instanceof IPlugin) {
            Description description = bean.getClass().getAnnotation(Description.class);
            Plugin plugin = bean.getClass().getAnnotation(Plugin.class);
            if (null != description && null != plugin) {
                descriptionHolder.putDescription(description, plugin);
            }
        }
        return bean;
    }
}
