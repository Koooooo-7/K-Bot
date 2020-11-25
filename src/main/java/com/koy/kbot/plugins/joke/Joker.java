package com.koy.kbot.plugins.joke;

import com.alibaba.fastjson.JSON;
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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.io.IOException;

/**
 * @Description
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/06
 */
@Plugin(name = "joker", call = "joker", fastCommand = {"j", "jk"})
@Description(author = "Koy", desc = "Send a joke randomly.", example = "joke")
public class Joker implements IPlugin {

    private static final String JOKE_API = ApiURLConstant.JOKE_API;
    private static final String CONTENT_KEY = "joke";

    @Autowired
    MessageSender messageSender;

    @Autowired
    GuildMessageReceivedEventHolder guildMessageReceivedEventHolder;

    private void jokeGenerator() throws IOException {

        final MessageChannel channel = guildMessageReceivedEventHolder.getChannel();
        HttpUtils.requestGetJsonObject(JOKE_API, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                throw new KBotException(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.body() == null) {
                    throw new KBotException("request content is null");
                }
                JSONObject requestJsonObject = JSON.parseObject(response.body().string());
                String joke = requestJsonObject.get(CONTENT_KEY).toString();

                MessageEmbed messageEmbed = new EmbedBuilder()
                        .setColor(Color.PINK)
                        .setTitle(":rofl: Joke")
                        .addField("", joke, true)
                        .build();
                messageSender.setEmbed(channel, messageEmbed);
            }
        });
    }


    @Override
    public void handle(String[] args) {
        try {
            jokeGenerator();
        } catch (IOException e) {
            e.printStackTrace();
            throw new KBotException("Joker quit his job...");
        }


    }

    @Override
    public String command() {
        return "joke";
    }
}
