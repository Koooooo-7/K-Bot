package com.koy.kbot.configuration;

import com.koy.kbot.holder.DescriptionHolder;
import com.koy.kbot.holder.GuildMemberEventHolder;
import com.koy.kbot.holder.GuildMessageReceivedEventHolder;
import com.koy.kbot.listener.MemberListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/06
 */
@Configuration
public class HolderConfiguration {

    @Bean
    public GuildMessageReceivedEventHolder guildMessageReceivedEventHolder() {
        return new GuildMessageReceivedEventHolder();
    }

    @Bean
    @ConditionalOnClass(MemberListener.class)
    public GuildMemberEventHolder guildMemberEventHolder() {
        return new GuildMemberEventHolder();
    }

    /**
     * auto configuration of descriptionHolder
     */
    @Bean
    public DescriptionHolder descriptionHolder() {
        return new DescriptionHolder();
    }
}
