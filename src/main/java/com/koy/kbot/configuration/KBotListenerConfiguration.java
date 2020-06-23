package com.koy.kbot.configuration;

import com.koy.kbot.configuration.condition.ConditionalOnSummoned;
import com.koy.kbot.listener.KBotListener;
import com.koy.kbot.listener.MemberListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/21
 */
@Configuration
public class KBotListenerConfiguration {


    /**
     * default listener, for commands
     * @return
     */

    @Bean
    @ConditionalOnSummoned(name = "k-bot.listeners", havingValue = "kbot", matchIfMissing = true)
    public KBotListener kBotListener() {
        return new KBotListener();
    }


    /**
     * member listener, for member active
     */
    @Bean
    @ConditionalOnSummoned(name = "k-bot.listeners", havingValue = "member")
    public MemberListener memberListener(){
        return new MemberListener();
    }
}
