package com.neil.dailyzhihu.utils;

import com.neil.dailyzhihu.bean.orignallayer.StoryExtra;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者：Neil on 2016/4/16 20:16.
 * 邮箱：cn.neillee@gmail.com
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

    public static String formatUrl(String head, String id) {
        return head + id;
    }

    public static String formatUrl(String head, int id, String tail) {
        return head + id + tail;
    }

    public static String formatData(String dataFormat, long timeStamp) {
        if (timeStamp == 0) {
            return "";
        }
        timeStamp = timeStamp * 1000;
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat(dataFormat);
        result = format.format(new Date(timeStamp));
        return result;
    }
}
