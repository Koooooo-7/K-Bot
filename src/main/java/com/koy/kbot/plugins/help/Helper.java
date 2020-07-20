package com.koy.kbot.plugins.help;

import com.koy.kbot.common.MessageSender;
import com.koy.kbot.configuration.core.Plugin;
import com.koy.kbot.holder.GuildMessageReceivedEventHolder;
import com.koy.kbot.plugins.IPlugin;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.HashMap;

/**
 * @Description default plugin always available
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/20
 */
@Component
@Plugin(name = "helper", call = "help", fastCommand = {"h", "hp"})
public class Helper implements IPlugin {

    @Autowired
    GuildMessageReceivedEventHolder guildMessageReceivedEventHolder;

    @Autowired
    MessageSender messageSender;

    // TODO helper contains all the available plugins on bootstrap
//    private static final HashMap<String, String> commands = new HashMap<>();

    @Override

    public void handle(String[] args) {
        MessageChannel channel = guildMessageReceivedEventHolder.getChannel();

        if (!command().toUpperCase().equals(args[0])) {

            MessageEmbed embed = new EmbedBuilder()
                    .setColor(Color.ORANGE)
                    .setTitle("Help")
                    .addField("", "Sorry, I cant understand ur command :ghost:", false)
                    .addField("tips:", " use [help] to get more usage commands", true)
                    .build();

            messageSender.setEmbed(channel, embed);
            return;
        }

    }

    @Override
    public String command() {
        return "help";
    }
}
