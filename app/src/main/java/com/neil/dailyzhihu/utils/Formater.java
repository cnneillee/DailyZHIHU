package com.neil.dailyzhihu.utils;

import com.neil.dailyzhihu.bean.StoryExtra;

/**
 * Created by Neil on 2016/4/16.
 */
public class Formater {
    public static String formatStoryExtra(StoryExtra extra) {
        String result = "热度：-，评论：-L + -S";
        if (extra != null)
            result = String.format("热度：%d，评论：%dL + %dS", extra.getPopularity(),
                    extra.getLong_comments(), extra.getShort_comments());
        return result;
    }

    public static String formatUrl(String head, int id) {
        return head + id;
    }

    public static String formatUrl(String head, int id, String tail) {
        return head + id + tail;
    }
}
