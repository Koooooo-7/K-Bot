package com.koy.kbot.exception;

import com.koy.kbot.common.MessageSender;
import com.koy.kbot.holder.GuildMessageReceivedEventHolder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

/**
 * @Description
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/07
 */
@Component
@Aspect
public class PluginsExceptionHandler {


    @Autowired
    MessageSender messageSender;

    @Autowired
    GuildMessageReceivedEventHolder guildMessageReceivedEventHolder;

    @Pointcut("execution(* com.koy.kbot.plugins.*.*(..))")
    public void pointCut() {
    }


    @Around("pointCut()")
    public Object exceptionHandler(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable ex) {
            MessageEmbed embed = new EmbedBuilder()
                    .setColor(Color.ORANGE)
                    .setTitle("ERROR")
                    .setDescription(ex.getMessage())
                    .addField("", "Ooops! something wrong in the plugin :ghost:", false)
                    .addField("tips:", " use [help] to get more usage commands", true)
                    .build();

            MessageChannel channel = guildMessageReceivedEventHolder.getChannel();
            messageSender.setEmbed(channel, embed);
            // release event
            guildMessageReceivedEventHolder.removeGuildMessageReceivedEvent();
        }
        return null;
    }


}
