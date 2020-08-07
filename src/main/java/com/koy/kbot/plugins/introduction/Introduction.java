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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * introduction plugin to show introduction of bot
 */
@Plugin(name = "introduction" , call = "intro" , fastCommand = {"intro" , "hi" , "hey"})
@Slf4j
public class Introduction implements IPlugin {
    /**
     * introduction
     */
    private static volatile String introductionText = null;

    /**
     * the final result when plugin cant get or format introduction
     */
    private static final String NO_INTRODUCTION = "sorry , i dont know where is my introduction, (╯﹏╰)b";

    /**
     * default title of message
     */
    private static final String DEFAULT_MESSAGE_TITLE = "INTRODUCTION";
    /**
     * location of bot introduction
     */
    @Value("${k-bot.introduction.location:classpath:introduction.md}")
    private String introductionLocation;
    /**
     * to avoid obtain introduction repeated invoke
     */
    private static volatile boolean introductionObtainDone = false;

    @Autowired
    MessageSender messageSender;

    @Autowired
    GuildMessageReceivedEventHolder guildMessageReceivedEventHolder;


    @Override
    public void handle(String[] args){
        //this plugin dont need args , so no matter what args is , just send the same message
        MessageEmbed messageEmbed = null;
        if(!introductionObtainDone){
            getIntroduction(introductionLocation);
        }
        if(StringUtils.isEmpty(introductionText)){
            //tried to get ,but fail
            messageEmbed = new EmbedBuilder()
                    .setColor(Color.PINK)
                    .setTitle(DEFAULT_MESSAGE_TITLE)
                    .setDescription(NO_INTRODUCTION)
                    .build();
        }else {
            messageEmbed = new EmbedBuilder()
                    .setColor(Color.PINK)
                    .setTitle(DEFAULT_MESSAGE_TITLE)
                    .setDescription(introductionText)
                    .build();
        }
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
    private synchronized static void getIntroduction(String introductionLocation) {
        if(introductionObtainDone){
            return;
        }
        //no matter what result is , dont try again.
        introductionObtainDone = true;
        try {
            File file = ResourceUtils.getFile(introductionLocation);
            String introduction = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            if(!StringUtils.isEmpty(introduction)){
                introductionText = introduction;
            }
        } catch (IOException e) {
            log.error("get introduction error , please check file is exists and correct" , e);
        }
    }
}
