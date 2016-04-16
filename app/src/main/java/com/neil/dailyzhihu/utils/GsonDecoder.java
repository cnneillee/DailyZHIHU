package com.neil.dailyzhihu.utils;

import com.google.gson.Gson;

/**
 * Created by Neil on 2016/4/16.
 */
public class GsonDecoder<T> {
    private GsonDecoder() {
    }

    public static GsonDecoder getDecoder() {
        return new GsonDecoder();
    }

    public T decoding(String gsonStr, Class<T> classOfT) {
        T t;
        Gson gson = new Gson();
        t = gson.fromJson(gsonStr, classOfT);
        return t;
    }
}
