package com.koy.kbot.holder;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

/**
 * @Description
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/06
 */

public class GuildMessageReceivedEventHolder {

     private final ThreadLocal<GuildMessageReceivedEvent>  guildMessageReceivedEventHolder = new ThreadLocal<>();


    public void setGuildMessageReceivedEventHolder(GuildMessageReceivedEvent event) {
        guildMessageReceivedEventHolder.set(event);
    }

    public GuildMessageReceivedEvent getGuildMessageReceivedEventHolder(){
        return guildMessageReceivedEventHolder.get();
    }

    public void removeGuildMessageReceivedEvent(){
        guildMessageReceivedEventHolder.remove();
    }

    public MessageChannel getChannel(){
        return getGuildMessageReceivedEventHolder().getChannel();
    }

    public TextChannel getTextChannel(){
        return getGuildMessageReceivedEventHolder().getChannel();
    }

    public Message getMessage(){
        return getGuildMessageReceivedEventHolder().getMessage();
    }

    public String getContentRaw(){
        return getMessage().getContentRaw();
    }

    public Guild getGuild(){
        return getGuildMessageReceivedEventHolder().getGuild();
    }

    public User getAuthor() {
        return getMessage().getAuthor();
    }
}
