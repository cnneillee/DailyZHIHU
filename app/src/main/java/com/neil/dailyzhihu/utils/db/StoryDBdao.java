//package com.neil.dailyzhihu.utils.db;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.text.TextUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Neil on 2016/4/25.
// */
//public class StoryDBdao implements StoryDBI {
//    private SQLiteDatabase readable;
//    private SQLiteDatabase writable;
//
//    public StoryDBdao(Context context) {
//        SQLiteDataBaseHelper openHelper = new SQLiteDataBaseHelper(context);
//        readable = openHelper.getReadableDatabase();
//        writable = openHelper.getWritableDatabase();
//    }
//
//    @Override
//    public long addStory(Story story) {
//        String storyId = story.getStoryId();
//        String body = story.getBody();
//        String title = story.getTitle();
//        String type = story.getType();
//        String gaPrefix = story.getGaPrefix();
//        String imgPath = story.getImgPath();
//        String imgSrc = story.getImgSrc();
//        String imgUrl = story.getImgUrl();
//        String shareUrl = story.getShareUrl();
//
//        String downloadedTimestamp = story.getDownloadedTimestamp();
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(StoryDB.KEY_STORY_ID, storyId);
//        contentValues.put(StoryDB.KEY_STORY_BODY, body);
//        contentValues.put(StoryDB.KEY_STORY_TITLE, title);
//        contentValues.put(StoryDB.KEY_STORY_SHARE_URL, shareUrl);
//        contentValues.put(StoryDB.KEY_STORY_IMAGE_SOURCE, imgSrc);
//        contentValues.put(StoryDB.KEY_STORY_IMAGE_URL, imgUrl);
//        contentValues.put(StoryDB.KEY_STORY_GA_PREFIX, gaPrefix);
//        if (!TextUtils.isEmpty(imgPath))
//            contentValues.put(StoryDB.KEY_STORY_IMAGE_PATH, imgPath);
//        if (!TextUtils.isEmpty(downloadedTimestamp))
//            contentValues.put(StoryDB.KEY_STORY_DOWNLOADED_TIME_STAMP, downloadedTimestamp);
//        contentValues.put(StoryDB.KEY_STORY_TYPE, type);
//        return writable.insert(StoryDB.STORY_TABLE_NAME, null, contentValues);
//    }
//
//    @Override
//    public List<Story> queryAllStory() {
//        Cursor cursor = readable.query(StoryDB.STORY_TABLE_NAME, null, null, null, null, null, null);
//        List<Story> storyList = new ArrayList<>();
//        if (cursor.moveToFirst()) {
//            do {
//                Story story = cursor2Story(cursor);
//                if (story != null)
//                    storyList.add(story);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return storyList;
//    }
//
//    private Story cursor2Story(Cursor cursor) {
//        String storyId = cursor.getString(cursor.getColumnIndex(StoryDB.KEY_STORY_ID));
//        String body = cursor.getString(cursor.getColumnIndex(StoryDB.KEY_STORY_BODY));
//        String title = cursor.getString(cursor.getColumnIndex(StoryDB.KEY_STORY_TITLE));
//        String shareUrl = cursor.getString(cursor.getColumnIndex(StoryDB.KEY_STORY_SHARE_URL));
//        String imgsrc = cursor.getString(cursor.getColumnIndex(StoryDB.KEY_STORY_IMAGE_SOURCE));
//        String imgUrl = cursor.getString(cursor.getColumnIndex(StoryDB.KEY_STORY_IMAGE_URL));
//        String imgPath = cursor.getString(cursor.getColumnIndex(StoryDB.KEY_STORY_IMAGE_PATH));
//        String downloadedTimestamp = cursor.getString(cursor.getColumnIndex(StoryDB.KEY_STORY_DOWNLOADED_TIME_STAMP));
//        String type = cursor.getString(cursor.getColumnIndex(StoryDB.KEY_STORY_TYPE));
//        String gaPrefix = cursor.getString(cursor.getColumnIndex(StoryDB.KEY_STORY_GA_PREFIX));
//
//        Story story = new Story(storyId, title, shareUrl, imgUrl, imgPath, imgsrc, body, type, gaPrefix, downloadedTimestamp, null, null, null, null);
//        return story;
//    }
//
//    @Override
//    public Story queryStoryById(String storyId) {
//        Story story = null;
//        Cursor cursor = readable.query(StoryDB.STORY_TABLE_NAME, null, StoryDB.KEY_STORY_ID + "=?", new String[]{storyId}, null, null, null);
//        if (cursor.moveToFirst()) {
//            story = cursor2Story(cursor);
//        }
//        cursor.close();
//        return story;
//    }
//
//    @Override
//    public Story queryStoryByGaPrefix(String gaPrefix) {
//        Story story = null;
//        Cursor cursor = readable.query(StoryDB.STORY_TABLE_NAME, null, StoryDB.KEY_STORY_GA_PREFIX + "=?", new String[]{gaPrefix}, null, null, null);
//        if (cursor.moveToFirst()) {
//            story = cursor2Story(cursor);
//        }
//        cursor.close();
//        return story;
//    }
//
//    @Override
//    public List<Story> queryStoryByType(String type) {
//        Cursor cursor = readable.query(StoryDB.STORY_TABLE_NAME, null, StoryDB.KEY_STORY_TYPE + "=?", new String[]{type}, null, null, null);
//        List<Story> storyList = new ArrayList<>();
//        if (cursor.moveToFirst()) {
//            do {
//                Story story = cursor2Story(cursor);
//                if (story != null)
//                    storyList.add(story);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return storyList;
//    }
//
//
//    @Override
//    public int updateStoryOfImgPath(String storyId, String imagePath) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(StoryDB.KEY_STORY_IMAGE_PATH, imagePath);
//        return writable.update(StoryDB.STORY_TABLE_NAME, contentValues, StoryDB.KEY_STORY_ID + "=?", new String[]{storyId});
//    }
//
//    @Override
//    public int deleteStoryById(String storyId) {
//        return writable.delete(StoryDB.STORY_TABLE_NAME, StoryDB.KEY_STORY_ID + "=?", new String[]{storyId});
//    }
//}
