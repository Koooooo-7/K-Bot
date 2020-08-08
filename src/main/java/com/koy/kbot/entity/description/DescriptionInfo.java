package com.koy.kbot.entity.description;

import lombok.Builder;
import lombok.Data;

/**
 * the entity of description.
 * it usually convert from {@link com.koy.kbot.configuration.core.Description} in {@link com.koy.kbot.beanprocessor.DescriptionBeanProcessor}
 * @author sivyer9303
 */
@Builder
@Data
public class DescriptionInfo {
    /**
     * author of plugin
     */
    String author;
    /**
     * example usage of plugin
     */
    String example;
    /**
     * description of plugin
     */
    String desc;

}
