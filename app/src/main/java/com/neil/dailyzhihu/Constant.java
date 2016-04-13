package com.neil.dailyzhihu;

/**
 * Created by Neil on 2016/3/23.
 */
public class Constant {
    public static final String URL_LATEST_NEWS = "http://news-at.zhihu.com/api/4/news/latest";
    /**
     * 启动界面图像
     * START_IMG_HEAD+"320*432"(480*728/720*1184/1080*1776)
     */
    //启动头
    public static final String START_IMG_HEAD = "http://news-at.zhihu.com/api/4/start-image/";
    /**
     * START_IMG_SIZE_MIN=320*432
     */
    public static final String START_IMG_SIZE_MIN = START_IMG_HEAD + "320*432";
    /**
     * START_IMG_SIZE_MEDIUM=480*728
     */
    public static final String START_IMG_SIZE_MEDIUM = START_IMG_HEAD + "480*728";
    /**
     * START_IMG_SIZE_LARGE=720*1184
     */
    public static final String START_IMG_SIZE_LARGE = START_IMG_HEAD + "720*1184";
    /**
     * START_IMG_SIZE_MAX=1080*1776
     */
    public static final String START_IMG_SIZE_MAX = START_IMG_HEAD + "1080*1776";
    public static final int START_ACTY_LAST_MILLIES = 2000;
}
