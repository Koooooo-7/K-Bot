package com.koy.kbot.plugins.weather.obtain;

import com.koy.kbot.entity.weather.WeatherInfo;

/**
 * weather obtain interface
 *
 * @author sivyer wang
 */
public interface IWeatherObtain {

    /**
     * to obtain weather info
     *
     * @param cityCode city code
     * @return weather info
     */
    WeatherInfo obtain(String cityCode);

    /**
     * get alias
     * @return alias
     */
    String getAlias();

}
