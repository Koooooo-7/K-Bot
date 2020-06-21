package com.koy.kbot;

import com.koy.kbot.configuration.core.JDABuilderWrapper;
import com.koy.kbot.configuration.properties.KBotProperties;
import com.koy.kbot.listener.IListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.EventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.security.auth.login.LoginException;
import java.util.List;

@SpringBootApplication
public class KBotApplication {

    @Autowired
    private List<IListener> listeners;

    @Autowired
    private KBotProperties kBotProperties;

    public static void main(String[] args) {
        SpringApplication.run(KBotApplication.class, args);
    }


    // initial bot
    @PostConstruct
    public void bootstrap() throws LoginException {
        JDA jda = JDABuilderWrapper.createDefault(kBotProperties.getToken())
                .addEventListeners(listeners)
                .build();

        jda.getPresence().setActivity(Activity.watching("Koy Coding Show"));
        jda.getPresence().setStatus(OnlineStatus.ONLINE);

    }

}
