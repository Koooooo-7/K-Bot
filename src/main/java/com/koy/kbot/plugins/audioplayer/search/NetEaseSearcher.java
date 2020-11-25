package com.koy.kbot.plugins.audioplayer.search;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.koy.kbot.common.constant.ApiURLConstant;
import com.koy.kbot.common.util.HttpUtils;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/11
 */
@Component
public class NetEaseSearcher implements IMusicSearcher {


    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public String search(String songName) {
        // trackUrl == null
        String searchSong = ApiURLConstant.NET_EASE_SEARCH_API + songName;
        JSONObject requestGetJsonObject = HttpUtils.requestGetJsonObject(searchSong);

        if (requestGetJsonObject == null) {
            return null;
        }
        try {
            JSONObject result = requestGetJsonObject.getJSONObject("result");
            JSONArray songs = result.getJSONArray("songs");
            // just choose the first as result
            JSONObject song = songs.getJSONObject(0);
            return song.get("id").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
