package com.koy.kbot.plugins.audioplayer.search;

import com.koy.kbot.common.constant.ApiURLConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @Description
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/11
 */
@Component
public class MusicSearcherHandler {


    private final List<IMusicSearcher> sortedSearchers;


    @Autowired
    private MusicSearcherHandler(List<IMusicSearcher> searchers) {
        sortedSearchers = searchers.stream().sorted(Comparator.comparingInt(IMusicSearcher::getOrder)).collect(Collectors.toList());

    }

    private static final Lock lock = new ReentrantLock();

    public String search(String songName) {
        lock.lock();
        try {
            String songId = doSearch(songName);
            return ApiURLConstant.NET_EASE_MUSIC_API+songId+ApiURLConstant.SUFFIX_MP3;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return "";
    }


    private String doSearch(String songName) {

        for (IMusicSearcher searcher : sortedSearchers) {
            String trackUrl = searcher.search(songName);
            if (trackUrl != null) {
                return trackUrl;
            }

        }
        return "";
    }

}
