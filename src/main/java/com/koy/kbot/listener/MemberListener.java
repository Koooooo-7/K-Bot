package com.koy.kbot.listener;

import com.koy.kbot.common.MessageSender;
import com.koy.kbot.holder.GuildMemberEventHolder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;

/**
 * @Description
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/18
 */
public class MemberListener extends ListenerAdapter implements IListener {

    @Autowired
    private GuildMemberEventHolder guildMemberEventHolder;

    @Autowired
    MessageSender messageSender;

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        super.onGuildMemberJoin(event);
        guildMemberEventHolder.setGuildMemberJoinEventHolder(event);
        TextChannel channel = event.getGuild().getDefaultChannel();
        String nickname = event.getMember().getNickname();
        channel.sendMessage("Wow ! "+nickname+" welcome to the beer bar ! :beers:").queue();
    }
}
