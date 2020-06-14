package com.koy.kbot.service;

import com.koy.kbot.configuration.properties.KBotProperties;
import com.koy.kbot.plugins.IPlugin;
import com.koy.kbot.plugins.audioplayer.AudioPlayer;
import com.koy.kbot.plugins.joke.Joker;
import com.koy.kbot.plugins.time.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/06
 */
@Component
public class BaseParserServiceImpl implements IParserService {


    @Autowired
    KBotProperties kBotProperties;

    @Autowired
    Joker joker;

    @Autowired
    AudioPlayer audioPlayer;

    @Autowired
    Time time;



    @Override
    public void parser(String[] args) {



        // TODO: chat bot api
        String command = args[1].toUpperCase();


        if ("JOKE".equals(command)) {
            joker.handle(args);
        }

        if ("PLAY".equals(command)) {
            audioPlayer.handle(args);
        }

        if ("TIME".equals(command)){
            time.handle(args);
        }
    }

}
