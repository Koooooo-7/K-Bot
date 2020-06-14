package com.koy.kbot.plugins.audioplayer;

import com.koy.kbot.holder.GuildMessageReceivedEventHolder;
import com.koy.kbot.plugins.IPlugin;
import com.koy.kbot.plugins.audioplayer.search.MusicSearcherHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/09
 */
@Component
public class AudioPlayer implements IPlugin {

    @Autowired
    GuildMessageReceivedEventHolder guildMessageReceivedEventHolder;

    @Autowired
    private AudioPlayerManager playerManager;

    @Autowired
    private MusicSearcherHandler musicSearcherHandler;

    private final Map<Long, GuildMusicManager> musicManagers = new HashMap<>();


    private synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        long guildId = Long.parseLong(guild.getId());
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    private void loadAndPlay(final TextChannel channel, final String songName) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

        // search song trackUrl by song name
        String trackUrl = musicSearcherHandler.search(songName);
        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                channel.sendMessage("adding to queue, song: " + songName).queue();

                play(channel.getGuild(), musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }

                channel.sendMessage("adding to queue " + songName + " (first track of playlist " + playlist.getName() + ")").queue();

                play(channel.getGuild(), musicManager, firstTrack);
            }

            @Override
            public void noMatches() {
                channel.sendMessage("can not find this song " + songName).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage("could not play: " + exception.getMessage()).queue();
            }
        });
    }

    private void play(Guild guild, GuildMusicManager musicManager, AudioTrack track) {
        connectToFirstVoiceChannel(guild.getAudioManager());

        musicManager.scheduler.queue(track);
    }

    private void skipTrack(TextChannel channel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.scheduler.nextTrack();

        channel.sendMessage("skipped to next track.").queue();
    }

    private static void connectToFirstVoiceChannel(AudioManager audioManager) {
        if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {
            audioManager.getGuild().getVoiceChannels().stream().findFirst().ifPresent(audioManager::openAudioConnection);
        }
    }

    @Override
    public void handle(String[] args) {

        String cmd = args[1];


        if ("play".equals(cmd)) {
            loadAndPlay(guildMessageReceivedEventHolder.getTextChannel(), args[2]);
        }
        if ("skip".equals(cmd)) {
            skipTrack(guildMessageReceivedEventHolder.getTextChannel());
        }

    }
}
