package com.koy.kbot.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.koy.kbot.exception.KBotException;
import okhttp3.*;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @Description
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/06
 */
public class HttpUtils {

    private static final Pattern pattern = Pattern.compile("/((([A-Za-z]{3,9}:(?:\\/\\/)?)(?:[-;:&=\\+\\$,\\w]+@)?[A-Za-z0-9.-]+|(?:www.|[-;:&=\\+\\$,\\w]+@)[A-Za-z0-9.-]+)((?:\\/[\\+~%\\/.\\w-_]*)?\\??(?:[-\\+=&;%@.\\w_]*)#?(?:[\\w]*))?)/");

    public static void requestGetJsonObject(String url, Callback callback) throws IOException, KBotException {
        OkHttpClient httpClient = new OkHttpClient.Builder().build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .build();

        httpClient.newCall(request).enqueue(callback);
    }

    public static JSONObject requestGetJsonObject(String url) {
        OkHttpClient httpClient = new OkHttpClient.Builder().build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .build();

        Response response;
        try {
            response = httpClient.newCall(request).execute();
            if (response.body() == null) {
                throw new KBotException("request content is null");
            }
            return JSON.parseObject(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
         return null;
    }


    public static boolean isUrl(String content){
        return pattern.matcher(content).matches();
    }
}
