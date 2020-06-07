package com.koy.kbot.command;

import com.koy.kbot.configuration.properties.KBotProperties;
import com.koy.kbot.holder.GuildMessageReceivedEventHolder;
import com.koy.kbot.plugins.joke.Joker;
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
public class BaseParserServiceImpl implements IParserService {


    private enum commandRegister {

        JOKE;

    }

    @Autowired
    GuildMessageReceivedEventHolder guildMessageReceivedEventHolder;

    @Autowired
    KBotProperties kBotProperties;

    @Autowired
    Joker joker;

    @Override
    public void parser() {

        String content = guildMessageReceivedEventHolder.getContentRaw();

        if (content.toLowerCase().startsWith(kBotProperties.getCmd().toLowerCase())) {

            String[] args = content.split("\\s+");

            if (args.length < 2) {
                // self
                return;
            }
            // TODO: chat bot api
            String command = args[1].toUpperCase();

            if ("JOKE".equals(command)) {
                joker.handle(args);
            }
        }

    }
}
