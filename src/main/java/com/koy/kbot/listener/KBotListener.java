package com.koy.kbot.listener;

import com.koy.kbot.command.IParserService;
import com.koy.kbot.configuration.properties.KBotProperties;
import com.koy.kbot.holder.GuildMessageReceivedEventHolder;
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
    private GuildMessageReceivedEventHolder guildMessageReceivedEventHolder;
    @Autowired
    private IParserService parser;

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        guildMessageReceivedEventHolder.setGuildMessageReceivedEventHolder(event);

        // Ignore message if bot or there is muted
        if (event.getMessage().getAuthor().isBot() || !event.getChannel().canTalk()) {
            return;
        }

        parser.parser();
    }
}
