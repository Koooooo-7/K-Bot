package com.koy.kbot.plugins.weather.obtain.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.koy.kbot.configuration.properties.WeatherProperties;
import com.koy.kbot.entity.weather.WeatherInfo;
import com.koy.kbot.plugins.weather.Weather;
import com.koy.kbot.plugins.weather.obtain.AbstractWeatherObtain;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

/**
 * obtain weather by openWeather api
 *
 * @author sivyer wang
 */
@Component
@ConditionalOnClass(Weather.class)
public class OpenWeatherObtain extends AbstractWeatherObtain {
    /**
     * alias of openWeatherMap
     */
    private String openWeatherMapAlias = "openWeatherMap";

    /**
     * get alias
     *
     * @return alias
     */
    @Override
    public String getAlias() {
        return openWeatherMapAlias;
    }

    /**
     * get format url of obtain weather
     *
     * @param props obtain properties
     * @return formatted url
     */
    @Override
    protected String formatUrl(WeatherProperties.WeatherObtainProperties props,String cityCode) {
        String apiUrl = props.getApiUrl();
        return String.format(apiUrl,cityCode,props.getApiToken());
    }

    /**
     * get weather info by json
     *
     * @param weatherInfoJson weather info json
     * @return weather info
     */
    @Override
    protected WeatherInfo getWeatherInfo(JSONObject weatherInfoJson) {
        JSONArray weather = weatherInfoJson.getJSONArray("weather");
        WeatherInfo info = null;
        if(null != weather){
            //get the first one
            JSONObject mainInfo = weather.getJSONObject(0);
            JSONObject windJson = weatherInfoJson.getJSONObject("wind");
            info = WeatherInfo.builder()
                    .main(mainInfo.getString("main"))
                    .description(mainInfo.getString("description"))
                    .windSpeed(windJson.getDouble("speed"))
                    .build();
        }
        return info;
    }
}
