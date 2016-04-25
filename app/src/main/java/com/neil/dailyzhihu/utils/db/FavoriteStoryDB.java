package com.neil.dailyzhihu.utils.db;

/**
 * Created by Neil on 2016/4/22.
 */
public class FavoriteStoryDB {

    public static final String FAVORITE_STORY_TABLE_NAME = "favotite_story";
    public static final String KEY_ROWID = "_id";
    public static final String KEY_STORY_ID = "story_id";
    public static final String KEY_STORY_EDITED_TIME_STAMP = "story_edited_time_stamp";
    public static final String KEY_STORY_DOWNLOADED_TIME_STAMP = "story_downloaded_time_stamp";
    public static final String KEY_STORY_STARED_TIME_STAMP = "story_stared_time_stamp";
    public static final String KEY_STORY_BODY = "story_body";
    public static final String KEY_STORY_DESC = "story_desc";
    public static final String KEY_STORY_AUTHOR = "story_author";
    public static final String KEY_STORY_IMAGE_SOURCE = "story_image_source";
    public static final String KEY_STORY_IMAGE_URL = "story_image_url";
    public static final String KEY_STORY_IMAGE_PATH = "story_image_path";
    public static final String KEY_STORY_TYPE = "story_type";
    public static final String KEY_STORY_BELONG_SECTION_THUMBNAIL = "story_belong_section_thumbnail";
    public static final String KEY_STORY_BELONG_SECTION_ID = "story_belong_section_id";
    public static final String KEY_STORY_BELONG_SECTION_NAME = "story_belong_section_name";
    public static final String KEY_STORY_TITLE = "story_title";
    public static final String KEY_STORY_SHARE_URL = "story_share_url";
    public static final String KEY_STORY_GA_PREFIX = "ga_prefix";

    public static String getCreatedStatement() {
        return "CREATE TABLE if not exists " + FavoriteStoryDB.FAVORITE_STORY_TABLE_NAME + " (" +
                FavoriteStoryDB.KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                FavoriteStoryDB.KEY_STORY_ID + " integer," +
                FavoriteStoryDB.KEY_STORY_EDITED_TIME_STAMP + " date," +
                FavoriteStoryDB.KEY_STORY_DOWNLOADED_TIME_STAMP + " date," +
                FavoriteStoryDB.KEY_STORY_STARED_TIME_STAMP + " date," +
                FavoriteStoryDB.KEY_STORY_BODY + " varchar," +
                FavoriteStoryDB.KEY_STORY_IMAGE_SOURCE + " varchar," +
                FavoriteStoryDB.KEY_STORY_IMAGE_URL + " varchar," +
                FavoriteStoryDB.KEY_STORY_IMAGE_PATH + " varchar," +
                FavoriteStoryDB.KEY_STORY_TYPE + " integer," +
                FavoriteStoryDB.KEY_STORY_BELONG_SECTION_THUMBNAIL + " varchar," +
                FavoriteStoryDB.KEY_STORY_BELONG_SECTION_ID + " integer," +
                FavoriteStoryDB.KEY_STORY_BELONG_SECTION_NAME + " varchar," +
                FavoriteStoryDB.KEY_STORY_GA_PREFIX + " varchar," +
                FavoriteStoryDB.KEY_STORY_TITLE + " varchar," +
                FavoriteStoryDB.KEY_STORY_DESC + " varchar," +
                FavoriteStoryDB.KEY_STORY_AUTHOR + " varchar," +
                FavoriteStoryDB.KEY_STORY_SHARE_URL + " varchar" + ");";
    }
}
