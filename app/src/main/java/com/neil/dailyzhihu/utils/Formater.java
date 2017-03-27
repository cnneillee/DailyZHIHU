package com.neil.dailyzhihu.utils;

import com.neil.dailyzhihu.bean.orignal.StoryExtraInfoBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者：Neil on 2016/4/16 20:16.
 * 邮箱：cn.neillee@gmail.com
 */
public class Formater {
    public static String formatStoryExtra(StoryExtraInfoBean extra) {
        String result = "热度：-，评论：-L + -S";
        if (extra != null)
            result = String.format("热度：%d，评论：%dL + %dS", extra.getPopularity(),
                    extra.getLongComments(), extra.getShortComments());
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
