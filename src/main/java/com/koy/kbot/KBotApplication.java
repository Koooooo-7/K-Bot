package com.koy.kbot;

import com.koy.kbot.configuration.properties.KBotProperties;
import com.koy.kbot.listener.KBotListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.security.auth.login.LoginException;

@SpringBootApplication
public class KBotApplication {

    @Autowired
    private KBotListener kBotListener;

    @Autowired
    private KBotProperties kBotProperties;

    public static void main(String[] args) {
        SpringApplication.run(KBotApplication.class, args);
    }


    // initial boot
    @PostConstruct
    public void bootstrap() throws LoginException {
        JDA jda = JDABuilder.createDefault(kBotProperties.getToken())
                .addEventListeners(kBotListener)
                .build();

        jda.getPresence().setActivity(Activity.playing("Koy Game"));
        jda.getPresence().setStatus(OnlineStatus.ONLINE);

    }

}
