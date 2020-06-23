package com.koy.kbot.listener;

import com.koy.kbot.service.IParserService;
import com.koy.kbot.common.MessageSender;
import com.koy.kbot.configuration.properties.KBotProperties;
import com.koy.kbot.holder.GuildMessageReceivedEventHolder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.awt.*;

/**
 * @Description
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/04
 */
public class KBotListener extends ListenerAdapter implements IListener {


    @Autowired
    private GuildMessageReceivedEventHolder guildMessageReceivedEventHolder;

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private IParserService parser;

    @Autowired
    private KBotProperties kBotProperties;

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {

        // Ignore message if bot or there is muted
        if (event.getMessage().getAuthor().isBot() || !event.getChannel().canTalk()) {
            return;
        }

        // get content
        String content = event.getMessage().getContentRaw();
        if (!content.toLowerCase().startsWith(kBotProperties.getCmd().toLowerCase())) {
            return;
        }

        // get args
        String[] args = content.split("\\s+");

        // just type the call without other args
        if (args.length < 2) {
            // only call self
            String userName = event.getAuthor().getName();
            MessageEmbed embed = new EmbedBuilder()
                    .setColor(Color.ORANGE)
                    .setTitle("Master " + userName + " :")
                    .addField("", "What can I do for u ! :ghost:", false)
                    .setFooter("tips: use [help] arg to get more usage commands")
                    .build();

            MessageChannel channel = event.getChannel();
            messageSender.setEmbed(channel, embed);
            return;
        }

        // hold event
        guildMessageReceivedEventHolder.setGuildMessageReceivedEventHolder(event);
        // parser command
        parser.parser(args);
    }
}
