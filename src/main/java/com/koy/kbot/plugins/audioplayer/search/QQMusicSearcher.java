package com.koy.kbot.plugins.audioplayer.search;

import org.springframework.stereotype.Component;

/**
 * @Description
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/12
 */
@Component
public class QQMusicSearcher implements IMusicSearcher {

    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public String search(String songName) {
        return null;
    }
}
