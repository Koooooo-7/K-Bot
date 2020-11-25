package com.koy.kbot.plugins.audioplayer.search;

/**
 * @Description
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/11
 */
public interface IMusicSearcher {


    /**
     * the searcher order
     *
     * @return order, from smaller to bigger
     */
    int getOrder();

    /**
     * search
     *
     * @return trackUrl
     */
    String search(String songName);


}
