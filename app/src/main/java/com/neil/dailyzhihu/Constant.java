package com.neil.dailyzhihu;

/**
 * Created by Neil on 2016/3/23.
 */
public class Constant {
    public static final String SECTIONS = "http://news-at.zhihu.com/api/3/sections";
    public static final String THEMES = "http://news-at.zhihu.com/api/3/themes";
    public static final String URL_LATEST_NEWS = "http://news-at.zhihu.com/api/4/news/latest";
    public static final String BEFORE_NEWS_HEADER = "http://news-at.zhihu.com/api/4/news/before/";
    public static final String HOT_NEWS = "http://news-at.zhihu.com/api/4/news/hot";
    public static final String STORY_HEAD = "http://news-at.zhihu.com/api/4/news/";
    public static final String EXTRA_HEAD = "http://news-at.zhihu.com/api/4/story-extra/";
    public static final String URL_SHARE_STORY_HEAD = "http://daily.zhihu.com/story/";


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
    public static final String SECTION_ID = "SECTION_ID";
    public static final String THEME_ID = "THEME_ID";
    public static final String STORY_ID = "STORY_ID";
    public static final String SECTION_NAME = "SECTION_NAME";
    public static final String SECTION_BG_IMG_URL = "SECTION_BG_IMG_URL";
    public static final String DAY_MODE_THEME = "theme_mode";
    public static final String SHARED_PREFERANCE_NAME = "zhihu_daily_sp";
    public static final String DAY_TIME_THEME = "daytime";
    public static final String NIGHT_TIME_THEME = "nighttime";
    public static final String DEFAULT_TIME_THEME = DAY_TIME_THEME;

    public static final String THEME_HEAD = "http://news-at.zhihu.com/api/4/theme/";
    public static final String SECTIONS_HEAD = "http://news-at.zhihu.com/api/3/section/";
    public static final String EDITOR_PROFULE_PAGE_HEAD = "http://news-at.zhihu.com/api/4/editor/";
    public static final String EDITOR_PROFULE_PAGE_TAIL = "/profile-page/android";


}
