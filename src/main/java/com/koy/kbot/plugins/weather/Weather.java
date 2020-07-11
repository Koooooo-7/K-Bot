package com.koy.kbot.plugins.weather;

import com.koy.kbot.common.MessageSender;
import com.koy.kbot.entity.weather.WeatherInfo;
import com.koy.kbot.holder.GuildMessageReceivedEventHolder;
import com.koy.kbot.plugins.IPlugin;
import com.koy.kbot.plugins.weather.obtain.IWeatherObtain;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.util.List;

/**
 * weather plugin
 * handle weather command
 *
 * @author sivyer wang
 */
public class Weather implements IPlugin {

    @Autowired
    MessageSender messageSender;

    @Autowired
    GuildMessageReceivedEventHolder guildMessageReceivedEventHolder;

    @Autowired
    private List<IWeatherObtain> obtains;

    @Override
    public void handle(String[] args) {
        if(null == args || args.length < 3){
            return;
        }
        //city code maybe composed of multi string
        StringBuilder cityCodeBuilder = new StringBuilder();
        for(int i = 0 , length = args.length ; i < length ; i++){
            if(i >= 2){
                cityCodeBuilder.append(args[i]).append(" ");
            }
        }
        String cityCode = cityCodeBuilder.toString();
        WeatherInfo info = null;
        for(int i = 0 , size = obtains.size() ; null == info && i < size ; i++){
            IWeatherObtain weatherObtain = obtains.get(i);
            //get the first matched
            info = weatherObtain.obtain(cityCode);
        }
        StringBuilder weatherDescription = new StringBuilder();
        if(null != info){
            weatherDescription.append(cityCode)
                    .append(" is ")
                    .append(info.getMain())
                    .append(" now, ")
                    .append(info.getDescription())
                    .append(", ")
                    .append(" wind speed is ")
                    .append(info.getWindSpeed())
                    .append("m/s");
            MessageEmbed messageEmbed = new EmbedBuilder()
                    .setColor(Color.PINK)
                    .setTitle("Weather info")
                    .setDescription(weatherDescription)
                    .build();

            MessageChannel channel = guildMessageReceivedEventHolder.getChannel();
            messageSender.setEmbed(channel, messageEmbed);
        }else {
            weatherDescription.append("sorry! I can't get weather info");
            MessageEmbed messageEmbed = new EmbedBuilder()
                    .setColor(Color.PINK)
                    .setTitle("Weather info")
                    .setDescription(weatherDescription)
                    .build();
            MessageChannel channel = guildMessageReceivedEventHolder.getChannel();
            messageSender.setEmbed(channel, messageEmbed);
        }
    }

    @Override
    public String command() {
        return "weather";
    }
}
