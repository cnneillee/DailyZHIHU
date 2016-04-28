package com.neil.dailyzhihu.utils.db.catalog.a;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "neil";
    private String simpleStoryTableName;
    private String latestSimpleStoryTableName;
    private String hotSimpleStoryTableName;
    private String beforeSimpleStoryTableName;

    private String detailStoryTableName;
    private String starDetailTableName;
    private String shareDetailTableName;

    private String sectionTableName;
    private String subscribeSectionTableName;

    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        simpleStoryTableName = createTableSQL(ConstantSimpleStoryDB.SIMPLE_STORY_TABLE_NAME);
        latestSimpleStoryTableName = createTableSQL(ConstantSimpleStoryDB.LATEST_SIMPLE_TABLE_NAME);
        hotSimpleStoryTableName = createTableSQL(ConstantSimpleStoryDB.HOT_SIMPLE_STORY_TABLE_NAME);
        beforeSimpleStoryTableName = createTableSQL(ConstantSimpleStoryDB.BEFORE_SIMPLE_STORY_TABLE_NAME);

        detailStoryTableName = createTableSQL(ConstantDetailStoryDB.DETAIL_STORY_TABLE_NAME);
        starDetailTableName = createTableSQL(ConstantDetailStoryDB.STAR_DETAIL_STORY_TABLE_NAME);
        shareDetailTableName = createTableSQL(ConstantDetailStoryDB.SHARE_RECORD_DETAIL_STORY_TABLE_NAME);

        sectionTableName = createTableSQL(ConstantSectionDB.SECTION_TABLE_NAME);
        subscribeSectionTableName = createTableSQL(ConstantSectionDB.SUBSCRIBE_SECTION_TABLE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(simpleStoryTableName);
        db.execSQL(latestSimpleStoryTableName);
        db.execSQL(hotSimpleStoryTableName);
        db.execSQL(beforeSimpleStoryTableName);

        db.execSQL(detailStoryTableName);
        db.execSQL(starDetailTableName);
        db.execSQL(shareDetailTableName);

        db.execSQL(sectionTableName);
        db.execSQL(subscribeSectionTableName);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private String createTableSQL(String tableName) {
        if (tableName.equals(ConstantSimpleStoryDB.SIMPLE_STORY_TABLE_NAME)) {//创建存放SimpleStory的表
            return "CREATE TABLE if not exists " + tableName + " (" +
                    ConstantSimpleStoryDB.KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    ConstantSimpleStoryDB.KEY_SIMPLE_STORY_ID + " integer," +
                    ConstantSimpleStoryDB.KEY_SIMPLE_STORY_DOWNLOADED_TIME_STAMP + " date," +
                    ConstantSimpleStoryDB.KEY_SIMPLE_STORY_IMAGE_URL + " varchar," +
                    ConstantSimpleStoryDB.KEY_SIMPLE_STORY_IMAGE_PATH + " varchar," +
                    ConstantSimpleStoryDB.KEY_SIMPLE_STORY_TYPE + " integer," +
                    ConstantSimpleStoryDB.KEY_SIMPLE_STORY_GA_PREFIX + " varchar," +
                    ConstantSimpleStoryDB.KEY_SIMPLE_STORY_DATE + " varchar," +
                    ConstantSimpleStoryDB.KEY_SIMPLE_STORY_POPULARITY + " varchar," +
                    ConstantSimpleStoryDB.KEY_SIMPLE_STORY_LONG_COMMENTS + " varchar," +
                    ConstantSimpleStoryDB.KEY_SIMPLE_STORY_SHORT_COMMENTS + " varchar," +
                    ConstantSimpleStoryDB.KEY_SIMPLE_STORY_TITLE + " varchar" + ");";
        } else if (tableName.equals(ConstantSimpleStoryDB.LATEST_SIMPLE_TABLE_NAME) ||
                tableName.equals(ConstantSimpleStoryDB.HOT_SIMPLE_STORY_TABLE_NAME) ||
                tableName.equals(ConstantSimpleStoryDB.BEFORE_SIMPLE_STORY_TABLE_NAME)) {
            return "CREATE TABLE if not exists " + tableName + " (" +
                    ConstantSimpleStoryDB.KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    ConstantSimpleStoryDB.KEY_SIMPLE_STORY_ID + " integer," +
                    ConstantSimpleStoryDB.KEY_SIMPLE_STORY_DATE + " varchar," +
                    ConstantSimpleStoryDB.KEY_SIMPLE_STORY_INCLUDE_TIME_STAMP + " varchar" + ");";
        } else if (tableName.equals(ConstantDetailStoryDB.DETAIL_STORY_TABLE_NAME)) {
            return "CREATE TABLE if not exists " + tableName + " (" +
                    ConstantDetailStoryDB.KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    ConstantDetailStoryDB.KEY_DETAIL_STORY_ID + " integer," +
                    ConstantDetailStoryDB.KEY_DETAIL_STORY_BODY + " varchar," +
                    ConstantDetailStoryDB.KEY_DETAIL_STORY_DATE + " varchar," +
                    ConstantDetailStoryDB.KEY_DETAIL_STORY_IMAGE_SOURCE + " varchar," +
                    ConstantDetailStoryDB.KEY_DETAIL_STORY_IMAGE_URL + " varchar," +
                    ConstantDetailStoryDB.KEY_DETAIL_STORY_IMAGE_URL_TINY + " varchar," +
                    ConstantDetailStoryDB.KEY_DETAIL_STORY_IMAGE_PATH + " varchar," +
                    ConstantDetailStoryDB.KEY_DETAIL_STORY_TYPE + " integer," +
                    ConstantDetailStoryDB.KEY_DETAIL_STORY_GA_PREFIX + " varchar," +
                    ConstantDetailStoryDB.KEY_DETAIL_STORY_TITLE + " varchar," +
                    ConstantDetailStoryDB.KEY_DETAIL_STORY_SHARE_URL + " varchar," +
                    ConstantDetailStoryDB.KEY_SECTION_ID + " varchar" + ");";
        } else if (tableName.equals(ConstantDetailStoryDB.STAR_DETAIL_STORY_TABLE_NAME)) {
            return "CREATE TABLE if not exists " + tableName + " (" +
                    ConstantDetailStoryDB.KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    ConstantDetailStoryDB.KEY_DETAIL_STORY_ID + " integer," +
                    ConstantDetailStoryDB.KEY_DETAIL_STORY_STAR_TIME_STAMP + " varchar," +
                    ConstantDetailStoryDB.KEY_DETAIL_STORY_STAR_DATE + " varchar," +
                    ConstantDetailStoryDB.KEY_DETAIL_STORY_STAR_CATALOG + " varchar" + ");";
        } else if (tableName.equals(ConstantDetailStoryDB.SHARE_RECORD_DETAIL_STORY_TABLE_NAME)) {
            return "CREATE TABLE if not exists " + tableName + " (" +
                    ConstantDetailStoryDB.KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    ConstantDetailStoryDB.KEY_DETAIL_STORY_ID + " integer," +
                    ConstantDetailStoryDB.KEY_DETAIL_STORY_SHARE_RECORD_DATE + " varchar," +
                    ConstantDetailStoryDB.KEY_DETAIL_STORY_SHARE_RECORD_METHOD_TYPE + " varchar," +
                    ConstantDetailStoryDB.KEY_DETAIL_STORY_SHARE_RECORD_PLATFORM + " varchar," +
                    ConstantDetailStoryDB.KEY_DETAIL_STORY_SHARE_RECORD_TIME_STAMP + " varchar" + ");";
        } else if (tableName.equals(ConstantSectionDB.SECTION_TABLE_NAME)) {
            return "CREATE TABLE if not exists " + tableName + " (" +
                    ConstantSectionDB.KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    ConstantSectionDB.KEY_SECTION_NAME + " integer," +
                    ConstantSectionDB.KEY_SECTION_THUMBNAIL + " varchar," +
                    ConstantSectionDB.KEY_SECTION_DESC + " varchar," +
                    ConstantSectionDB.KEY_SECTION_ID + " varchar" + ");";
        } else if (tableName.equals(ConstantSectionDB.SUBSCRIBE_SECTION_TABLE_NAME)) {
            return "CREATE TABLE if not exists " + tableName + " (" +
                    ConstantSectionDB.KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    ConstantSectionDB.KEY_SECTION_ID + " integer," +
                    ConstantSectionDB.SUBSCRIBE_DATE + " integer," +
                    ConstantSectionDB.SUBSCRIBE_TIMESTAMP + " varchar" + ");";
        }
        return null;
    }

    public class ConstantSimpleStoryDB {
        /**
         * 用于存放SimpleStory的表
         */
        public static final String SIMPLE_STORY_TABLE_NAME = "simple_story_table";
        /**
         * latest表
         */
        public static final String LATEST_SIMPLE_TABLE_NAME = "latest_simple_story_table";
        /**
         * hot表
         */
        public static final String HOT_SIMPLE_STORY_TABLE_NAME = "hot_simple_story_table";
        /**
         * before表
         */
        public static final String BEFORE_SIMPLE_STORY_TABLE_NAME = "before_simple_story_table";

        public static final String KEY_ROWID = "_id";
        public static final String KEY_SIMPLE_STORY_ID = "story_id";
        public static final String KEY_SIMPLE_STORY_TITLE = "story_title";
        public static final String KEY_SIMPLE_STORY_IMAGE_URL = "story_image_url";
        public static final String KEY_SIMPLE_STORY_IMAGE_PATH = "story_image_path";
        public static final String KEY_SIMPLE_STORY_TYPE = "story_type";
        public static final String KEY_SIMPLE_STORY_GA_PREFIX = "ga_prefix";
        public static final String KEY_SIMPLE_STORY_DOWNLOADED_TIME_STAMP = "story_downloaded_time_stamp";
        public static final String KEY_SIMPLE_STORY_INCLUDE_TIME_STAMP = "story_include_time_stamp";
        public static final String KEY_SIMPLE_STORY_DATE = "story_date";
        public static final String KEY_SIMPLE_STORY_POPULARITY = "story_popularity";
        public static final String KEY_SIMPLE_STORY_LONG_COMMENTS = "story_long_comments";
        public static final String KEY_SIMPLE_STORY_SHORT_COMMENTS = "story_short_comments";
    }

    public class ConstantDetailStoryDB {
        /**
         * 用于存放DetailStory的表
         */
        public static final String DETAIL_STORY_TABLE_NAME = "detail_story_table";
        /**
         * star表
         */
        public static final String STAR_DETAIL_STORY_TABLE_NAME = "star_detail_story_table";
        /**
         * sharerecord表
         */
        public static final String SHARE_RECORD_DETAIL_STORY_TABLE_NAME = "share_record_detail_story_table";

        public static final String KEY_DETAIL_STORY_STAR_TIME_STAMP = "star_timestamp";
        public static final String KEY_DETAIL_STORY_STAR_DATE = "star_date";
        public static final String KEY_DETAIL_STORY_STAR_CATALOG = "star_catalog";

        public static final String KEY_DETAIL_STORY_SHARE_RECORD_TIME_STAMP = "share_record_timestamp";
        public static final String KEY_DETAIL_STORY_SHARE_RECORD_DATE = "share_record_date";
        public static final String KEY_DETAIL_STORY_SHARE_RECORD_PLATFORM = "share_record_platform";
        public static final String KEY_DETAIL_STORY_SHARE_RECORD_METHOD_TYPE = "share_record_method_type";

        public static final String KEY_ROWID = "_id";
        public static final String KEY_DETAIL_STORY_ID = "story_id";
        public static final String KEY_DETAIL_STORY_DATE = "story_date";
        public static final String KEY_DETAIL_STORY_BODY = "story_body";
        public static final String KEY_DETAIL_STORY_IMAGE_SOURCE = "story_image_source";
        public static final String KEY_DETAIL_STORY_IMAGE_URL = "story_image_url";
        public static final String KEY_DETAIL_STORY_IMAGE_URL_TINY = "story_image_url_tiny";
        public static final String KEY_DETAIL_STORY_IMAGE_PATH = "story_image_path";
        public static final String KEY_DETAIL_STORY_TYPE = "story_type";
        public static final String KEY_SECTION_ID = "section_id";
        public static final String KEY_DETAIL_STORY_TITLE = "story_title";
        public static final String KEY_DETAIL_STORY_SHARE_URL = "story_share_url";
        public static final String KEY_DETAIL_STORY_GA_PREFIX = "ga_prefix";
    }

    public class ConstantSectionDB {
        /**
         * 用于存放SectionBean的表
         */
        public static final String SECTION_TABLE_NAME = "section_table";
        /**
         * subscribeSection表
         */
        public static final String SUBSCRIBE_SECTION_TABLE_NAME = "subscribe_section_table";
        public static final String SUBSCRIBE_DATE = "subscribe_date";
        public static final String SUBSCRIBE_TIMESTAMP = "subscribe_timestamp";

        public static final String KEY_ROWID = "_id";
        public static final String KEY_SECTION_ID = "section_id";
        public static final String KEY_SECTION_NAME = "section_name";
        public static final String KEY_SECTION_THUMBNAIL = "section_thumbnail";
        public static final String KEY_SECTION_DESC = "section_desc";

    }
}
