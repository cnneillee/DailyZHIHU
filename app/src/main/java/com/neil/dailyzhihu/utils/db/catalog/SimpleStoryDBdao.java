//package com.neil.dailyzhihu.utils.db.catalog;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.util.Log;
//
//import com.neil.dailyzhihu.bean.cleanlayer.SimpleStory;
//import com.neil.dailyzhihu.utils.db.SQLiteDataBaseHelper;
//import com.neil.dailyzhihu.utils.db.catalog.a.IDBSimpleStoryTable;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Neil on 2016/4/25.
// */
//public class SimpleStoryDBdao implements IDBSimpleStoryTable {
//
//    private static final String LOG_TAG = SimpleStoryDBdao.class.getSimpleName();
//    private SQLiteDatabase writable;
//    private SQLiteDatabase readable;
//    private String tableName;
//
//    public SimpleStoryDBdao(Context context, String tableName) {
//        SQLiteDataBaseHelper openHelper = new SQLiteDataBaseHelper(context);
//        readable = openHelper.getReadableDatabase();
//        writable = openHelper.getWritableDatabase();
//        this.tableName = tableName;
//    }
//
//    public long addSimpleStory(SimpleStory story) {
//        int storyId = story.getStoryId();
//        if (querySimpleStoryById(storyId) != null) {
//            Log.e(LOG_TAG, "insert error:this story exists");
//            return -1;
//        }
//        String title = story.getTitle();
//        int type = story.getType();
//        String gaPrefix = story.getGaPrefix();
//        String imageUrl = story.getImageUrl();
//        String imagePath = story.getImagePath();
//        String date = story.getDate();
//        String downloadedTimestamp = story.getDownloadTimeStamp();
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(HottestCatalogDB.KEY_STORY_ID, storyId);
//        contentValues.put(HottestCatalogDB.KEY_STORY_TITLE, title);
//        contentValues.put(HottestCatalogDB.KEY_STORY_TYPE, type);
//        contentValues.put(HottestCatalogDB.KEY_STORY_GA_PREFIX, gaPrefix);
//        contentValues.put(HottestCatalogDB.KEY_STORY_IMAGE_URL, imageUrl);
//        contentValues.put(HottestCatalogDB.KEY_STORY_IMAGE_PATH, imagePath);
//        contentValues.put(HottestCatalogDB.KEY_STORY_DOWNLOADED_TIME_STAMP, downloadedTimestamp);
//        contentValues.put(HottestCatalogDB.KEY_STORY_DOWNLOADED_DATE, date);
//        int res = (int) writable.insert(HottestCatalogDB.STORY_TABLE_NAME, null, contentValues);
//        if (res >= 0)
//            Log.e(LOG_TAG, "----" + title + type + gaPrefix + imageUrl + imagePath + date + downloadedTimestamp);
//        return res;
//    }
//
//    @Override
//    public int dropStoryCatalog(int storyId) {
//        return writable.delete(HottestCatalogDB.STORY_TABLE_NAME, HottestCatalogDB.KEY_STORY_ID + "=?", new String[]{storyId + ""});
//    }
//
//    @Override
//    public int updateSimpleStory(int storyId, SimpleStory simpleStory) {
//        if (querySimpleStoryById(Integer.valueOf(storyId)) == null) {
//            Log.e(LOG_TAG, "update error:this story doesnot exists");
//            return -1;
//        }
//        String title = simpleStory.getTitle();
//        int type = simpleStory.getType();
//        String gaPrefix = simpleStory.getGaPrefix();
//        String imageUrl = simpleStory.getImageUrl();
//        String imagePath = simpleStory.getImagePath();
//        String downloadTimestamp = simpleStory.getDownloadTimeStamp();
//        String date = simpleStory.getDate();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(HottestCatalogDB.KEY_STORY_ID, storyId);
//        contentValues.put(HottestCatalogDB.KEY_STORY_TITLE, title);
//        contentValues.put(HottestCatalogDB.KEY_STORY_TYPE, type);
//        contentValues.put(HottestCatalogDB.KEY_STORY_GA_PREFIX, gaPrefix);
//        contentValues.put(HottestCatalogDB.KEY_STORY_IMAGE_URL, imageUrl);
//        contentValues.put(HottestCatalogDB.KEY_STORY_IMAGE_PATH, imagePath);
//        contentValues.put(HottestCatalogDB.KEY_STORY_DOWNLOADED_DATE, date);
//        contentValues.put(HottestCatalogDB.KEY_STORY_DOWNLOADED_TIME_STAMP, downloadTimestamp);
//        return writable.update(HottestCatalogDB.STORY_TABLE_NAME, contentValues, HottestCatalogDB.KEY_STORY_ID + "=?", new String[]{storyId + ""});
//    }
//
//    @Override
//    public List<SimpleStory> querySimpleStoryById(int storyId) {
//        List<SimpleStory> simpleStoryList = null;
//        Cursor cursor = readable.query(HottestCatalogDB.STORY_TABLE_NAME, null, HottestCatalogDB.KEY_STORY_ID + "=?", new String[]{storyId + ""}, null, null, null);
//        Log.e(LOG_TAG, "cursor:" + cursor.getCount());
//        if (cursor.moveToFirst()) {
//            simpleStoryList = new ArrayList<>();
//            do {
//                SimpleStory story = cursor2SimpleStory(cursor);
//                if (story != null)
//                    simpleStoryList.add(story);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return simpleStoryList;
//    }
//
//    private SimpleStory cursor2SimpleStory(Cursor cursor) {
//        String storyId = cursor.getString(cursor.getColumnIndex(HottestCatalogDB.KEY_STORY_ID));
//        String title = cursor.getString(cursor.getColumnIndex(HottestCatalogDB.KEY_STORY_TITLE));
//        String imageUrl = cursor.getString(cursor.getColumnIndex(HottestCatalogDB.KEY_STORY_IMAGE_URL));
//        String imagePath = cursor.getString(cursor.getColumnIndex(HottestCatalogDB.KEY_STORY_IMAGE_PATH));
//        String downloadedTimestamp = cursor.getString(cursor.getColumnIndex(HottestCatalogDB.KEY_STORY_DOWNLOADED_TIME_STAMP));
//        String type = cursor.getString(cursor.getColumnIndex(HottestCatalogDB.KEY_STORY_TYPE));
//        String gaPrefix = cursor.getString(cursor.getColumnIndex(HottestCatalogDB.KEY_STORY_GA_PREFIX));
//        String date = cursor.getString(cursor.getColumnIndex(HottestCatalogDB.KEY_STORY_DOWNLOADED_DATE));
//        SimpleStory simpleStory = new SimpleStory(Integer.valueOf(storyId), gaPrefix, title, Integer.valueOf(type), imageUrl, imagePath, date, downloadedTimestamp);
//        return simpleStory;
//    }
//
//    @Override
//    public List<SimpleStory> queryStoryCatalogByDownloadedDate(String storyDownloadedDate) {
//        List<SimpleStory> simpleStoryList = null;
//        Cursor cursor = readable.query(HottestCatalogDB.STORY_TABLE_NAME, null, HottestCatalogDB.KEY_STORY_DOWNLOADED_DATE + "=?", new String[]{storyDownloadedDate}, null, null, null);
//        if (cursor.moveToFirst()) {
//            simpleStoryList = new ArrayList<>();
//            Log.e(LOG_TAG, "cursor:" + cursor.getCount());
//            do {
//                SimpleStory story = cursor2SimpleStory(cursor);
//                if (story != null)
//                    simpleStoryList.add(story);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return simpleStoryList;
//    }
//
//    @Override
//    public List<SimpleStory> queryAllSimpleStory() {
//        List<SimpleStory> simpleStoryList = null;
//        Cursor cursor = readable.query(HottestCatalogDB.STORY_TABLE_NAME, null, null, null, null, null, null);
//        Log.e(LOG_TAG, "cursor:" + cursor.getCount());
//        if (cursor.moveToFirst()) {
//            simpleStoryList = new ArrayList<>();
//            do {
//                SimpleStory story = cursor2SimpleStory(cursor);
//                if (story != null)
//                    simpleStoryList.add(story);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return simpleStoryList;
//    }
//}
