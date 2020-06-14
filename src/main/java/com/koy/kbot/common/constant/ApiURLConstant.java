package com.koy.kbot.common.constant;

/**
 * @Description
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/06
 */
public class ApiURLConstant {
    /**
     * joke
     */
    public static final String JOKE_API = "https://icanhazdadjoke.com/";

    /**
     * music api
     */
    public static final String SUFFIX_MP3 = ".mp3";
    // arg: song name
    public static final String NET_EASE_SEARCH_API = "http://music.163.com/api/cloudsearch/pc?&type=1&limite=1%offset=0&s=";
    // arg: song id
    public static final String NET_EASE_MUSIC_API = "https://music.163.com/song/media/outer/url?id=";

    public static final String QQ_SEARCH_API = "https://c.y.qq.com/soso/fcgi-bin/client_search_cp?aggr=1&cr=1&flag_qc=0&p=1&n=30&w=";
    public static final String QQ_MUSIC_API = "";


    /**
     * time  arg: city name
     * origin api support site https://www.nowapi.com/api/time.world
     */
    public static final String TIME_API = "http://api.k780.com/?app=time.world&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json&city_en=";
}
