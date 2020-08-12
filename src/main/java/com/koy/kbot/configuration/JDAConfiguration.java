package com.koy.kbot.configuration;

import com.koy.kbot.configuration.core.JDABuilderWrapper;
import com.koy.kbot.configuration.properties.KBotProperties;
import com.koy.kbot.listener.IListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.internal.JDAImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.security.auth.login.LoginException;
import java.util.List;

@Configuration
public class JDAConfiguration {
    @Autowired
    private KBotProperties kBotProperties;

    @Autowired
    private List<IListener> listeners;

    @Bean("jda")
    public JDAImpl buildJDA() throws LoginException {
        JDA jda = JDABuilderWrapper.createDefault(kBotProperties.getToken())
                .addEventListeners(listeners)
                .build();
        JDAImpl impl = (JDAImpl) jda;
        jda.getPresence().setActivity(Activity.watching("Koy Coding Show"));
        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        return impl;
    }
}
