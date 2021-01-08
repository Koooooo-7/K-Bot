package com.koy.kbot.holder;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;

/**
 * @Description
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/06
 */
public class GuildMemberEventHolder {

    private final ThreadLocal<GuildMemberJoinEvent> GuildMemberJoinEventHolder = new ThreadLocal<>();


    public void setGuildMemberJoinEventHolder(GuildMemberJoinEvent event) {
        GuildMemberJoinEventHolder.set(event);
    }

    public GuildMemberJoinEvent getGuildMemberJoinEventHolder() {
        return GuildMemberJoinEventHolder.get();
    }

    public void removeGuildMemberJoinEvent() {
        GuildMemberJoinEventHolder.remove();
    }

}
