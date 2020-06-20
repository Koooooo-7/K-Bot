package com.koy.kbot.configuration;

import com.koy.kbot.configuration.condition.ConditionalOnSummoned;
import com.koy.kbot.listener.KBotListener;
import com.koy.kbot.plugins.audioplayer.AudioPlayer;
import com.koy.kbot.plugins.joke.Joker;
import com.koy.kbot.plugins.time.Time;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description register  plugins
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/06
 */
@Configuration
@ConditionalOnClass(KBotListener.class)
public class KBotConfiguration {


    /**
     * Joker send a random joke
     *
     * @return
     */
    @Bean
    @ConditionalOnSummoned(name = "k-bot.plugins", havingValue = "joker")
    public Joker joker() {
        return new Joker();
    }

    /**
     * Send the time on specific city name
     * @return
     */
    @Bean
    @ConditionalOnSummoned(name = "k-bot.plugins", havingValue = "time")
    public Time time() {
        return new Time();
    }

    /**
     * AudioPlayer on song name
     * @return
     */
    @Bean
    @ConditionalOnSummoned(name = "k-bot.plugins", havingValue = "player")
    public AudioPlayerManager audioPlayerManager() {
        DefaultAudioPlayerManager playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
        return playerManager;
    }

    @Bean
    @ConditionalOnSummoned(name = "k-bot.plugins", havingValue = "player")
    public AudioPlayer audioPlayer(){
        return new AudioPlayer();
    }

}
