package com.neil.dailyzhihu.utils.db.catalog;

/**
 * Created by Neil on 2016/4/25.
 */
public class LatestCatalogDB {
    public static final String STORY_TABLE_NAME = "latest_story_catalog";

    public static final String KEY_ROWID = "_id";
    public static final String KEY_STORY_ID = "story_id";
    public static final String KEY_STORY_TITLE = "story_title";
    public static final String KEY_STORY_IMAGE_URL = "story_image_url";
    public static final String KEY_STORY_IMAGE_PATH = "story_image_path";
    public static final String KEY_STORY_TYPE = "story_type";
    public static final String KEY_STORY_GA_PREFIX = "ga_prefix";
    public static final String KEY_STORY_DOWNLOADED_TIME_STAMP = "story_downloaded_time_stamp";

    public static String getCreateStatement() {
        return "CREATE TABLE if not exists " + LatestCatalogDB.STORY_TABLE_NAME + " (" +
                LatestCatalogDB.KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                LatestCatalogDB.KEY_STORY_ID + " integer," +
                LatestCatalogDB.KEY_STORY_DOWNLOADED_TIME_STAMP + " date," +
                LatestCatalogDB.KEY_STORY_IMAGE_URL + " varchar," +
                LatestCatalogDB.KEY_STORY_IMAGE_PATH + " varchar," +
                LatestCatalogDB.KEY_STORY_TYPE + " integer," +
                LatestCatalogDB.KEY_STORY_GA_PREFIX + " varchar," +
                LatestCatalogDB.KEY_STORY_TITLE + " varchar" + ");";
    }
}
