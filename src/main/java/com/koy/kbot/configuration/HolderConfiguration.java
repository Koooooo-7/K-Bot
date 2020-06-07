package com.koy.kbot.configuration;

import com.koy.kbot.holder.GuildMessageReceivedEventHolder;
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
    public GuildMessageReceivedEventHolder guildMessageReceivedEventHolder(){
        return new GuildMessageReceivedEventHolder();
    }
}
