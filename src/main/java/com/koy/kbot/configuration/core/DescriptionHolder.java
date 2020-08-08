package com.koy.kbot.configuration.core;

import com.koy.kbot.entity.description.DescriptionInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * the holder of description.
 * init in {@link com.koy.kbot.beanprocessor.DescriptionBeanProcessor}
 * use in {@link com.koy.kbot.plugins.introduction.Introduction}
 *
 * @author sivyer9303
 */
@Component
public class DescriptionHolder {
    /**
     * description info map
     */
    private static final ConcurrentMap<String, DescriptionInfo> DESCRIPTION_MAP = new ConcurrentHashMap(16);

    /**
     * put description into map
     */
    public static void putDescription(Description description,Plugin plugin){
        DescriptionInfo info = formatAnnotations(description);
        DESCRIPTION_MAP.putIfAbsent(plugin.name(), info);
    }

    /**
     * get descriptions
     */
    public static String getDescriptions(){
        StringBuilder stringBuilder = new StringBuilder();
        DESCRIPTION_MAP.forEach((name,info) -> {
            stringBuilder.append(name)
                    .append(":")
                    .append(info.getDesc())
                    .append(",")
                    .append("example usage is '")
                    .append(info.getExample())
                    .append("'")
                    .append("\n");
        });
        return stringBuilder.toString();
    }

    /**
     * build DescriptionInfo by description and plugin
     * @param description see {@link Description}
     * @return DescriptionInfo
     */
    private static DescriptionInfo formatAnnotations(@NotNull Description description) {
        return DescriptionInfo.builder()
                .author(description.author())
                .desc(description.desc())
                .example(description.example())
                .build();
    }
}
