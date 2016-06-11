package com.neil.dailyzhihu.utils;

import com.google.gson.Gson;
import com.neil.dailyzhihu.bean.orignallayer.OrignalStory;

/**
 * Created by Neil on 2016/4/16.
 */
public class GsonDecoder {
    private GsonDecoder() {
    }

    public static GsonDecoder getDecoder() {
        return new GsonDecoder();
    }

    public <T extends OrignalStory> T decoding(String gsonStr, Class<T> classOfT) {
        T t;
        Gson gson = new Gson();
        t = gson.fromJson(gsonStr, classOfT);
        return t;
    }
}
