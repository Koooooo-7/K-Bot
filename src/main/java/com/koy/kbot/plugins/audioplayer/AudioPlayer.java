package com.koy.kbot.plugins.audioplayer;

import com.google.common.base.Joiner;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.koy.kbot.configuration.core.Plugin;
import com.koy.kbot.exception.KBotException;
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
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/09
 */
@Plugin(name = "audioPlayer", call = "play",fastCommand = "p")
public class AudioPlayer implements IPlugin {

    private static final Cache<String, String> trackUrlCache = CacheBuilder.newBuilder()
            .initialCapacity(10)
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .maximumSize(500)
            .build();

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

    private void loadAndPlay(final TextChannel channel, final String trackUrl, String songName) {
        // check if the user in a voice channel now
        String userId = guildMessageReceivedEventHolder.getAuthor().getId();
        AudioManager audioManager = channel.getGuild().getAudioManager();
            List<VoiceChannel> voiceChannels = audioManager.getGuild().getVoiceChannels();
            VoiceChannel voiceChannel = voiceChannels
                    .stream()
                    .filter(p -> p
                            .getMembers()
                            .stream()
                            .anyMatch(e -> e
                                    .getUser()
                                    .getId()
                                    .equals(userId)))
                    .findFirst()
                    .orElseGet(() -> null);
            if (voiceChannel == null) {
                channel.sendMessage("> you need join a voice channel first :eyes: ").queue();
                return;
            }

            doLoadAndPlay(channel, trackUrl, songName, voiceChannel);
        }

    private void doLoadAndPlay(final TextChannel channel, final String trackUrl, String songName, final VoiceChannel voiceChannel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                channel.sendMessage(":headphones: adding to queue, song name: **"
                        + Optional.ofNullable(songName).orElseGet(() -> track.getInfo().title) + "**").queue();
                play(channel.getGuild(), musicManager, track, voiceChannel);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }

                channel.sendMessage("adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();

                play(channel.getGuild(), musicManager, firstTrack, voiceChannel);
            }

            @Override
            public void noMatches() {
                channel.sendMessage(":sweat_smile: can not find this song "
                        + Optional.ofNullable(songName).orElseGet(() -> trackUrl)).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage(":sweat_smile:could not play: " + exception.getMessage()).queue();
            }
        });
    }

    private void play(Guild guild, GuildMusicManager musicManager, AudioTrack track, VoiceChannel voiceChannel) {
        connectToVoiceChannel(guild.getAudioManager(), voiceChannel);

        musicManager.scheduler.queue(track);
    }

    private void skipTrack(TextChannel channel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.scheduler.nextTrack();

        channel.sendMessage(":athletic_shoe:skipped to next track.").queue();
    }

    private void connectToVoiceChannel(AudioManager audioManager, VoiceChannel voiceChannel) {
        if(!audioManager.isConnected()&& !audioManager.isAttemptingToConnect()){
            audioManager.openAudioConnection(voiceChannel);
        }
    }


    /**
     *  play for song name: !cmd play song name
     *  play for track url: !cmd play -u www.example.com/song.mp3
     */
    @Override
    public void handle(String[] args) {

        if (args.length < 3) {
            throw new KBotException(":sweat_smile: cant play the song");
        }

        String subCmd = args[2];

        // subCmd skip to next
        if ("~skip".equals(subCmd)) {
            skipTrack(guildMessageReceivedEventHolder.getTextChannel());
            return;
        }


        // if url
        if ("-u".equals(subCmd)) {
            loadAndPlay(guildMessageReceivedEventHolder.getTextChannel(), args[2], null);
            return;
        }

        // if song name
        String[] names = Arrays.copyOfRange(args, 2, args.length);
        String songName = Joiner.on(" ").join(names);

        String trackUrl = trackUrlCache.getIfPresent(songName.toUpperCase());
        if (trackUrl == null) {
            // search song trackUrl by song name
            trackUrl = musicSearcherHandler.search(songName);
            trackUrlCache.put(songName.toUpperCase(), trackUrl);
        }
        loadAndPlay(guildMessageReceivedEventHolder.getTextChannel(), trackUrl, songName);
    }

    @Override
    public String command() {
        return "play";
    }
}
