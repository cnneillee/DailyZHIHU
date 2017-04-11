package com.neil.dailyzhihu.model.http.api;

/**
 * 作者：Neil on 2017/3/5 18:14.
 * 邮箱：cn.neillee@gmail.com
 */

public class API {
    /*Splash source*/
    public static final String GANK_SPLASH = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/1/1";
    public static final String ZHIHU_SPLASH = "https://api.zhihu.com/launch?app=daily&size=1080x1920";
    public static final String PUSH_SPLASH = "https://api.huaban.com/boards/35593400/pins?limit=1";
    public static final String ERCIYUAN_SPLASH = "https://api.huaban.com/boards/35593275/pins?limit=1";
    public static final String HUABAN_IMG_HEADER = "http://img.hb.aicdn.com/";

    /*主题日报、栏目纵览*/
    public static final String SECTIONS = "http://news-at.zhihu.com/api/3/sections";
    public static final String THEMES = "http://news-at.zhihu.com/api/3/themes";

    /*latest、hot、before*/
    public static final String LATEST_NEWS = "http://news-at.zhihu.com/api/4/news/latest";
    public static final String HOT_NEWS = "http://news-at.zhihu.com/api/4/news/hot";
    public static final String BEFORE_NEWS_PREFIX = "http://news-at.zhihu.com/api/4/news/before/";

    /*story comment prefix*/
    public static final String STORY_COMMENT_PREFIX = "http://news-at.zhihu.com/api/4/story/";

    /*web story url prefix*/
    public static final String WEB_STORY_PREFIX = "http://daily.zhihu.com/story/";

    /*version info*/
    public static final String VERSION_INFO = "https://lilinmao.github.io/projects/dailyzhihu/api/1/version/index.html";
    /*open source license*/
    public static final String OPENSOURCE_LICENSE = "https://lilinmao.github.io/projects/dailyzhihu/api/1/license/index.html";
    /*App intro*/
    public static final String APP_INTRODUCTION = "https://lilinmao.github.io/projects/dailyzhihu/api/1/intro/index.html";
    /*update*/
    public static final String CHECK_FOR_UPDATES = "https://lilinmao.github.io/projects/dailyzhihu/api/1/updates/index.html";
}
