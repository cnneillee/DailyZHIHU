package com.neil.dailyzhihu.utils.db;

/**
 * Created by Neil on 2016/4/25.
 */
public class StoryDB {
    public static final String STORY_TABLE_NAME = "story";
    public static final String KEY_ROWID = "_id";
    public static final String KEY_STORY_ID = "story_id";
    public static final String KEY_STORY_TITLE = "story_title";
    public static final String KEY_STORY_BODY = "story_body";
    public static final String KEY_STORY_SHARE_URL = "story_share_url";
    public static final String KEY_STORY_IMAGE_URL = "story_image_url";
    public static final String KEY_STORY_IMAGE_PATH = "story_image_path";
    public static final String KEY_STORY_IMAGE_SOURCE = "story_image_source";
    public static final String KEY_STORY_TYPE = "story_type";
    public static final String KEY_STORY_GA_PREFIX = "ga_prefix";
    public static final String KEY_STORY_DOWNLOADED_TIME_STAMP = "story_downloaded_time_stamp";

    public static String getCreateStatement() {
        return "CREATE TABLE if not exists " + StoryDB.STORY_TABLE_NAME + " (" +
                StoryDB.KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                StoryDB.KEY_STORY_ID + " integer," +
                StoryDB.KEY_STORY_DOWNLOADED_TIME_STAMP + " date," +
                StoryDB.KEY_STORY_BODY + " varchar," +
                StoryDB.KEY_STORY_IMAGE_SOURCE + " varchar," +
                StoryDB.KEY_STORY_IMAGE_URL + " varchar," +
                StoryDB.KEY_STORY_IMAGE_PATH + " varchar," +
                StoryDB.KEY_STORY_TYPE + " integer," +
                StoryDB.KEY_STORY_GA_PREFIX + " varchar," +
                StoryDB.KEY_STORY_TITLE + " varchar," +
                StoryDB.KEY_STORY_SHARE_URL + " varchar" + ");";
    }
}
