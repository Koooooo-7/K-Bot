package com.koy.kbot.plugins.introduction;

import com.koy.kbot.common.MessageSender;
import com.koy.kbot.configuration.core.Plugin;
import com.koy.kbot.holder.GuildMessageReceivedEventHolder;
import com.koy.kbot.plugins.IPlugin;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Plugin(name = "intro" , call = "intro" , fastCommand = {"intro" , "hi" , "hey"})
@Slf4j
public class Introduction implements IPlugin {
    /**
     * introduction
     */
    private static volatile String INTRODUCTION = null;

    /**
     * the default title of message
     */
    private static final String DEFAULT_MESSAGE_TITLE = "INTRODUCTION";

    @Autowired
    MessageSender messageSender;

    @Autowired
    GuildMessageReceivedEventHolder guildMessageReceivedEventHolder;

    @Override
    public void handle(String[] args){
        //this plugin dont need args , so no matter what args is , just send the same message
        if(StringUtils.isEmpty(INTRODUCTION)){
            getIntroduction();
        }
        MessageEmbed messageEmbed = new EmbedBuilder()
                .setColor(Color.PINK)
                .setTitle(DEFAULT_MESSAGE_TITLE)
                .setDescription(INTRODUCTION)
                .build();
        messageSender.setEmbed(guildMessageReceivedEventHolder.getChannel(),messageEmbed);
    }

    @Override
    public String command() {
        return "intro";
    }

    /**
     * try to get introduction info
     * By default, classpath:/introduction.md will be the introduction
     * try to change introduction txt location and introduction txt details to change the introduction info
     */
    private void getIntroduction() {
        // TODO add more properties to customize introduction
        try {
            File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "introduction.md");
            String introduction = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            if(!StringUtils.isEmpty(introduction)){
                INTRODUCTION = introduction;
            }
        } catch (IOException e) {
            log.info("get introduction string error , please check file is exists and correct");
            e.printStackTrace();
        }
    }
}
