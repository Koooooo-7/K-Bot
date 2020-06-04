package com.koy.kbot.listener;

import com.koy.kbot.configuration.properties.KBotProperties;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

/**
 * @Description
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/04
 */
@Component
public class KBotListener extends ListenerAdapter {


    @Autowired
    private KBotProperties kBotProperties;

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        Message message = event.getMessage();
        User author = message.getAuthor();
        String content = message.getContentRaw();
        Guild guild = event.getGuild();
        MessageChannel channel = event.getChannel();
        // Ignore message if bot
        if (author.isBot())
            return;

        if (content.startsWith(kBotProperties.getCmd())) {

            System.out.println(content);
//            CommandHandler.commandParser(channel, content.substring(6));
            channel.sendMessage(content + "  yo?").queue();
        }


    }
}
