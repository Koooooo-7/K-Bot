package com.koy.kbot.plugins.joke;

import com.alibaba.fastjson.JSONObject;
import com.koy.kbot.common.MessageSender;
import com.koy.kbot.common.constant.ApiURLConstant;
import com.koy.kbot.common.util.HttpUtils;
import com.koy.kbot.exception.KBotException;
import com.koy.kbot.holder.GuildMessageReceivedEventHolder;
import com.koy.kbot.plugins.IPlugin;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Description
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/06
 */
@Component
public class Joker implements IPlugin {

    private static final String JOKE_API = ApiURLConstant.JOKE_API;
    private static final String CONTENT_KEY = "joke";

    @Autowired
    MessageSender messageSender;

    @Autowired
    GuildMessageReceivedEventHolder guildMessageReceivedEventHolder;

    private String jokeGenerator() throws IOException {
        JSONObject requestJsonObject = HttpUtils.requestGetJsonObject(JOKE_API);
        return requestJsonObject.get(CONTENT_KEY).toString();

    }


    @Override
    public void handle(String[] args) {
        try {
            String joke = jokeGenerator();
//            MessageEmbed embed = new EmbedBuilder()
//                    .setColor(Color.ORANGE)
//                    .addField("", joke + ":rofl:", false)
//                    .build();
            MessageChannel channel = guildMessageReceivedEventHolder.getChannel();
            messageSender.setString(channel, joke + ":rofl:");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
