package com.neil.dailyzhihu.utils.db.catalog;

/**
 * Created by Neil on 2016/4/25.
 */
public class HottestCatalogDB {
    public static final String STORY_TABLE_NAME = "hottest_story_catalog";

    public static final String KEY_ROWID = "_id";
    public static final String KEY_STORY_ID = "story_id";
    public static final String KEY_STORY_TITLE = "story_title";
    public static final String KEY_STORY_IMAGE_URL = "story_image_url";
    public static final String KEY_STORY_IMAGE_PATH = "story_image_path";
    public static final String KEY_STORY_TYPE = "story_type";
    public static final String KEY_STORY_GA_PREFIX = "ga_prefix";
    public static final String KEY_STORY_DOWNLOADED_TIME_STAMP = "story_downloaded_time_stamp";
    public static final String KEY_STORY_DOWNLOADED_DATE = "story_downloaded_date";
    public static final String KEY_STORY_POPULARITY = "story_popularity";
    public static final String KEY_STORY_LONG_COMMENTS = "story_long_comments";
    public static final String KEY_STORY_SHORT_COMMENTS = "story_short_comments";


    public static String getCreateStatement() {
        return "CREATE TABLE if not exists " + HottestCatalogDB.STORY_TABLE_NAME + " (" +
                HottestCatalogDB.KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                HottestCatalogDB.KEY_STORY_ID + " integer," +
                HottestCatalogDB.KEY_STORY_DOWNLOADED_TIME_STAMP + " date," +
                HottestCatalogDB.KEY_STORY_IMAGE_URL + " varchar," +
                HottestCatalogDB.KEY_STORY_IMAGE_PATH + " varchar," +
                HottestCatalogDB.KEY_STORY_TYPE + " integer," +
                HottestCatalogDB.KEY_STORY_GA_PREFIX + " varchar," +
                HottestCatalogDB.KEY_STORY_DOWNLOADED_DATE + " varchar," +
                HottestCatalogDB.KEY_STORY_POPULARITY + " varchar," +
                HottestCatalogDB.KEY_STORY_LONG_COMMENTS + " varchar," +
                HottestCatalogDB.KEY_STORY_SHORT_COMMENTS + " varchar," +
                HottestCatalogDB.KEY_STORY_TITLE + " varchar" + ");";
    }
}
