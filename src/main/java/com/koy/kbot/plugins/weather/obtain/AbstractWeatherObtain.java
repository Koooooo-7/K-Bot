package com.koy.kbot.plugins.weather.obtain;

import com.alibaba.fastjson.JSONObject;
import com.koy.kbot.common.util.HttpUtils;
import com.koy.kbot.configuration.properties.WeatherProperties;
import com.koy.kbot.entity.weather.WeatherInfo;
import org.apache.commons.codec.binary.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Set;

/**
 * abstract class of common weather obtain
 *
 * @author sivyer wang
 */
public abstract class AbstractWeatherObtain implements WeatherObtain{

    @Autowired
    private WeatherProperties props;

    /**
     * get format url of obtain weather
     *
     * @param props obtain properties
     * @param cityCode city code
     * @return formatted url
     */
    protected abstract String formatUrl(WeatherProperties.WeatherObtainProperties props,String cityCode);

    /**
     * get weather info by json
     * @param weatherInfoJson weather info json
     * @return weather info
     */
    protected abstract WeatherInfo getWeatherInfo(JSONObject weatherInfoJson);

    /**
     * to obtain weather info
     *
     * @param cityCode city code
     * @return weather info
     */
    @Override
    public WeatherInfo obtain(String cityCode) {
        WeatherProperties.WeatherObtainProperties propsByAlias = getPropsByAlias();
        WeatherInfo info = null;
        if(null != propsByAlias){
            String url = formatUrl(propsByAlias,cityCode);
            JSONObject weatherInfoJson = HttpUtils.requestGetJsonObject(url);
            info =  getWeatherInfo(weatherInfoJson);
        }
        return info;
    }

    /**
     * get WeatherObtainProperties by alias
     * @return WeatherObtainProperties
     */
    protected WeatherProperties.WeatherObtainProperties getPropsByAlias(){
        WeatherProperties.WeatherObtainProperties prop = null;
        Set<Map.Entry<String, WeatherProperties.WeatherObtainProperties>> entries = props.getProps().entrySet();
        for(Map.Entry<String, WeatherProperties.WeatherObtainProperties> entry: entries){
            if(StringUtils.equals(entry.getKey(), getAlias())){
                prop = entry.getValue();
                break;
            }
        }
        return prop;
    }
}
