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

    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        simpleStoryTableName = createTableSQL(ConstantDB.SIMPLE_STORY_TABLE_NAME);
        latestSimpleStoryTableName = createTableSQL(ConstantDB.LATEST_SIMPLE_TABLE_NAME);
        hotSimpleStoryTableName = createTableSQL(ConstantDB.HOT_SIMPLE_STORY_TABLE_NAME);
        beforeSimpleStoryTableName = createTableSQL(ConstantDB.BEFORE_SIMPLE_STORY_TABLE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(simpleStoryTableName);
        db.execSQL(latestSimpleStoryTableName);
        db.execSQL(hotSimpleStoryTableName);
        db.execSQL(beforeSimpleStoryTableName);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private String createTableSQL(String tableName) {
        if (tableName.equals(ConstantDB.SIMPLE_STORY_TABLE_NAME)) {//创建存放SimpleStory的表
            return "CREATE TABLE if not exists " + tableName + " (" +
                    ConstantDB.KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    ConstantDB.KEY_SIMPLE_STORY_ID + " integer," +
                    ConstantDB.KEY_SIMPLE_STORY_DOWNLOADED_TIME_STAMP + " date," +
                    ConstantDB.KEY_SIMPLE_STORY_IMAGE_URL + " varchar," +
                    ConstantDB.KEY_SIMPLE_STORY_IMAGE_PATH + " varchar," +
                    ConstantDB.KEY_SIMPLE_STORY_TYPE + " integer," +
                    ConstantDB.KEY_SIMPLE_STORY_GA_PREFIX + " varchar," +
                    ConstantDB.KEY_SIMPLE_STORY_DATE + " varchar," +
                    ConstantDB.KEY_SIMPLE_STORY_POPULARITY + " varchar," +
                    ConstantDB.KEY_SIMPLE_STORY_LONG_COMMENTS + " varchar," +
                    ConstantDB.KEY_SIMPLE_STORY_SHORT_COMMENTS + " varchar," +
                    ConstantDB.KEY_SIMPLE_STORY_TITLE + " varchar" + ");";
        } else if (tableName.equals(ConstantDB.LATEST_SIMPLE_TABLE_NAME) ||
                tableName.equals(ConstantDB.HOT_SIMPLE_STORY_TABLE_NAME) ||
                tableName.equals(ConstantDB.BEFORE_SIMPLE_STORY_TABLE_NAME)) {
            return "CREATE TABLE if not exists " + tableName + " (" +
                    ConstantDB.KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    ConstantDB.KEY_SIMPLE_STORY_ID + " integer," +
                    ConstantDB.KEY_SIMPLE_STORY_DATE + " varchar," +
                    ConstantDB.KEY_SIMPLE_STORY_INCLUDE_TIME_STAMP + " varchar" + ");";
        }
        return null;
    }

    public class ConstantDB {
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
}
