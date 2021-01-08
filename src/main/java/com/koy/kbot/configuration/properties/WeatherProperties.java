package com.koy.kbot.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * some properties of weather plugin
 *
 * @author sivyer wang
 */
@ConfigurationProperties(prefix = "weather")
@Component
@Data
public class WeatherProperties {
    /**
     * properties map
     */
    Map<String, WeatherObtainProperties> props = new LinkedHashMap<>();

    @Data
    public static class WeatherObtainProperties {
        /**
         * api url
         */
        String apiUrl;
        /**
         * api token
         */
        String apiToken;
    }
}
