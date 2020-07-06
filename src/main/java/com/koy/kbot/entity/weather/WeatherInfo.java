package com.koy.kbot.entity.weather;

import lombok.Builder;
import lombok.Data;

/**
 * entity of weather
 *
 * @author sivyer wang
 */
@Data
@Builder
public class WeatherInfo {
    /**
     * main weather
     */
    private String main;
    /**
     * description of weather
     */
    private String description;
    /**
     * the speed of wind
     */
    private Double windSpeed;
}
