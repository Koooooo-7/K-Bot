package com.koy.kbot;

import com.koy.kbot.configuration.KBotConfiguration;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.internal.JDAImpl;
import net.dv8tion.jda.internal.entities.GuildImpl;
import net.dv8tion.jda.internal.entities.MemberImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@SpringBootTest
@Import(KBotConfiguration.class)
class KBotApplicationTests {

    @Autowired
    private JDAImpl jda;

    /**
     * cant use beforeAll annotation ? to be define.
     */
    @BeforeEach
    public void init(){
        Assertions.assertNotNull(jda);
        SelfUser selfUser = jda.getSelfUser();
        List<Guild> guilds = jda.getGuilds();
        GuildImpl guild = (GuildImpl) guilds.get(0);
        Member member = new MemberImpl(guild,selfUser);
        GuildMemberJoinEvent guildMemberJoinEvent = new GuildMemberJoinEvent(jda, 1000L, member);
        jda.handleEvent(guildMemberJoinEvent);
    }

    @Test
    void contextLoads() {
        MessageBuilder builder = new MessageBuilder("!koy hi");
        Message message = builder.build();
        GuildMessageReceivedEvent messageReceivedEvent = new GuildMessageReceivedEvent(jda,5000L,message);
        jda.handleEvent(messageReceivedEvent);
    }

}
