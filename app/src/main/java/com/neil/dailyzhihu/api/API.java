package com.neil.dailyzhihu.api;

/**
 * 作者：Neil on 2017/3/5 18:14.
 * 邮箱：cn.neillee@gmail.com
 */

public class API {

    /**
     * 启动界面图像
     * START_IMG_PREFIX+"320*432"(480*728/720*1184/1080*1776)
     */
    public static final String START_IMG_PREFIX = "http://news-at.zhihu.com/api/4/start-image/";
    public static final String START_IMG_SIZE_MIN = START_IMG_PREFIX + "320*432";
    public static final String START_IMG_SIZE_MEDIUM = START_IMG_PREFIX + "480*728";
    public static final String START_IMG_SIZE_LARGE = START_IMG_PREFIX + "720*1184";
    public static final String START_IMG_SIZE_MAX = START_IMG_PREFIX + "1080*1776";

    /*主题日报、栏目纵览*/
    public static final String SECTIONS = "http://news-at.zhihu.com/api/3/sections";
    public static final String THEMES = "http://news-at.zhihu.com/api/3/themes";

    /*latest、hot、before*/
    public static final String LATEST_NEWS = "http://news-at.zhihu.com/api/4/news/latest";
    public static final String HOT_NEWS = "http://news-at.zhihu.com/api/4/news/hot";
    public static final String BEFORE_NEWS_PREFIX = "http://news-at.zhihu.com/api/4/news/before/";

    /*story prefix*/
    public static final String STORY_PREFIX = "http://news-at.zhihu.com/api/4/news/";
    /*theme prefix*/
    public static final String THEME_PREFIX = "http://news-at.zhihu.com/api/4/theme/";
    /*section prefix*/
    public static final String SECTION_PREFIX = "http://news-at.zhihu.com/api/3/section/";

    /*story comment prefix*/
    public static final String STORY_COMMENT_PREFIX = "http://news-at.zhihu.com/api/4/story/";
    /*story extra prefix*/
    public static final String STORY_EXTRA_PREFIX = "http://news-at.zhihu.com/api/4/story-extra/";
    /*editor profile page prefix*/
    public static final String EDITOR_PROFULE_PAGE_PREFIX = "http://news-at.zhihu.com/api/4/editor/";
    /*editor profile page suffix*/
    public static final String EDITOR_PROFULE_PAGE_SUFFIX = "/profile-page/android";

    /*web story url prefix*/
    public static final String WEB_STORY_PREFIX = "http://daily.zhihu.com/story/";
}
