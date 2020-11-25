package com.koy.kbot.plugins.time;

import com.alibaba.fastjson.JSONObject;
import com.koy.kbot.common.MessageSender;
import com.koy.kbot.common.constant.ApiURLConstant;
import com.koy.kbot.common.util.HttpUtils;
import com.koy.kbot.configuration.core.Description;
import com.koy.kbot.configuration.core.Plugin;
import com.koy.kbot.exception.KBotException;
import com.koy.kbot.holder.GuildMessageReceivedEventHolder;
import com.koy.kbot.plugins.IPlugin;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;


/**
 * @Description
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/14
 */
@Plugin(name = "time", call = "time", fastCommand = "t")
@Description(author = "Koy", desc = "Report the current time of the city", example = "time Tokyo")
public class Time implements IPlugin {

    private static final String DEFAULT_CITY = "beijing";

    @Autowired
    MessageSender messageSender;

    @Autowired
    GuildMessageReceivedEventHolder guildMessageReceivedEventHolder;

    @Override
    public void handle(String[] args) {

        // no city specific, send default city time
        if (args.length < 3) {
            doSendDefaultTime();
            return;
        }

        String inputCity = args[2];
        sendTimeByCity(inputCity);

    }

    @Override
    public String command() {
        return "time";
    }

    private void sendTimeByCity(String cityName) {

        String url = ApiURLConstant.TIME_API + cityName;
        JSONObject requestGetJsonObject = HttpUtils.requestGetJsonObject(url);

        try {
            // request failed
            if (requestGetJsonObject == null || !"1".equals(requestGetJsonObject.get("success"))) {
                throw new KBotException("cant get time");
            }
            JSONObject resultJsonObject = requestGetJsonObject.getJSONObject("result");
            String continents = resultJsonObject.getString("continents_en");
            String country = resultJsonObject.getString("contry_en");
            String city = resultJsonObject.getString("city_en");
            String datetime = resultJsonObject.getString("datetime_1");
            String week = resultJsonObject.getString("week_4");

            StringBuilder address = new StringBuilder();
            address.append(city.toUpperCase())
                    .append(".")
                    .append(country.toUpperCase())
                    .append(".")
                    .append(continents.toUpperCase());

            MessageEmbed messageEmbed = new EmbedBuilder()
                    .setColor(Color.PINK)
                    .setTitle("Current Time")
                    .setDescription(datetime)
                    .addField("", week, true)
                    .addField("", address.toString(), true)
                    .build();

            MessageChannel channel = guildMessageReceivedEventHolder.getChannel();
            messageSender.setEmbed(channel, messageEmbed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doSendDefaultTime() {
        sendTimeByCity(DEFAULT_CITY);
    }
}
