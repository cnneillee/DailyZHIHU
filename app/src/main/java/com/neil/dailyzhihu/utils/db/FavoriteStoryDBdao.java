package com.neil.dailyzhihu.utils.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neil on 2016/4/22.
 */
public class FavoriteStoryDBdao implements IDB {
    private SQLiteDatabase readable;
    private SQLiteDatabase writable;

    public FavoriteStoryDBdao(Context context) {
        SQLiteDataBaseHelper openHelper = new SQLiteDataBaseHelper(context);
        readable = openHelper.getReadableDatabase();
        writable = openHelper.getWritableDatabase();
    }

    @Override
    public long addStory(FavoriteStory story) {
        String title = story.getTitle();
        String storyId = story.getStoryId();
        String body = story.getBody();
        String editedTimestamp = story.getEditedTimestamp();
        String shareUrl = story.getShareUrl();

        String imgsrc = story.getImgsrc();
        String imgUrl = story.getImgUrl();

        String downloadedTimestamp = story.getDownloadedTimestamp();
        String staredTimestamp = story.getStaredTimestamp();
        String imgPath = story.getImgPath();
        String type = story.getType();

        BaseSection section = story.getSection();

        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoriteStoryDB.KEY_STORY_ID, storyId);
        contentValues.put(FavoriteStoryDB.KEY_STORY_BODY, body);
        contentValues.put(FavoriteStoryDB.KEY_STORY_TITLE, title);
        if (!TextUtils.isEmpty(editedTimestamp))
            contentValues.put(FavoriteStoryDB.KEY_STORY_EDITED_TIME_STAMP, editedTimestamp);
        contentValues.put(FavoriteStoryDB.KEY_STORY_SHARE_URL, shareUrl);
        contentValues.put(FavoriteStoryDB.KEY_STORY_IMAGE_SOURCE, imgsrc);
        contentValues.put(FavoriteStoryDB.KEY_STORY_IMAGE_URL, imgUrl);
        if (!TextUtils.isEmpty(imgPath))
            contentValues.put(FavoriteStoryDB.KEY_STORY_IMAGE_PATH, imgPath);
        if (!TextUtils.isEmpty(downloadedTimestamp))
            contentValues.put(FavoriteStoryDB.KEY_STORY_DOWNLOADED_TIME_STAMP, downloadedTimestamp);
        contentValues.put(FavoriteStoryDB.KEY_STORY_STARED_TIME_STAMP, staredTimestamp);
        contentValues.put(FavoriteStoryDB.KEY_STORY_TYPE, type);
        if (section != null) {
            String sectionId = section.getSectionId() + "";
            String sectionName = section.getSectionName();
            String thumbnail = section.getThumbnail();
            contentValues.put(FavoriteStoryDB.KEY_STORY_BELONG_SECTION_ID, sectionId);
            contentValues.put(FavoriteStoryDB.KEY_STORY_BELONG_SECTION_NAME, sectionName);
            contentValues.put(FavoriteStoryDB.KEY_STORY_BELONG_SECTION_THUMBNAIL, thumbnail);
        }
        return writable.insert(FavoriteStoryDB.FAVORITE_STORY_TABLE_NAME, null, contentValues);
    }

    @Override
    public void dropStory(int storyId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoriteStoryDB.KEY_STORY_ID, storyId);
        writable.delete(FavoriteStoryDB.FAVORITE_STORY_TABLE_NAME, FavoriteStoryDB.KEY_STORY_ID + "=?", new String[]{storyId + ""});
    }

    //// TODO: 2016/4/22 期待实现
    @Override
    public void updateStory(int storyId, FavoriteStory newStory) {
        //readable.query(FavoriteStoryDB.FAVORITE_STORY_TABLE_NAME,new String[]{FavoriteStoryDB.STAR})
    }

//    public FavoriteStory queryStoryById(int storyId) {
//        Cursor cursor = readable.query(FavoriteStoryDB.FAVORITE_STORY_TABLE_NAME, null, FavoriteStoryDB.KEY_STORY_ID + "=?", new String[]{storyId + ""}, null, null, null);
//        FavoriteStory favoriteStory = null;
//        if (cursor.moveToFirst()) {
//            FavoriteStory story = cursor2FavoriteStory(cursor);
//            if (story != null)
//                favoriteStory = story;
//        }
//        cursor.close();
//        return favoriteStory;
//    }

    @Override
    public List<FavoriteStory> queryStoryById(int storyId) {
        Cursor cursor = readable.query(FavoriteStoryDB.FAVORITE_STORY_TABLE_NAME, null, FavoriteStoryDB.KEY_STORY_ID + "=?", new String[]{storyId + ""}, null, null, null);
        List<FavoriteStory> favoriteStories = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                FavoriteStory story = cursor2FavoriteStory(cursor);
                if (story != null)
                    favoriteStories.add(story);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return favoriteStories;
    }

    @Override
    public List<FavoriteStory> queryStoryByStaredDate(long storyStaredDate) {
        Cursor cursor = readable.query(FavoriteStoryDB.FAVORITE_STORY_TABLE_NAME, null, FavoriteStoryDB.KEY_STORY_STARED_TIME_STAMP + "=?", new String[]{storyStaredDate + ""}, null, null, null);
        List<FavoriteStory> favoriteStories = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                FavoriteStory story = cursor2FavoriteStory(cursor);
                if (story != null)
                    favoriteStories.add(story);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return favoriteStories;
    }

    @Override
    public List<FavoriteStory> queryStoryByCreatedDate(long storyEditedDate) {
        Cursor cursor = readable.query(FavoriteStoryDB.FAVORITE_STORY_TABLE_NAME, null, FavoriteStoryDB.KEY_STORY_EDITED_TIME_STAMP + "=?", new String[]{storyEditedDate + ""}, null, null, null);
        List<FavoriteStory> favoriteStories = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                FavoriteStory story = cursor2FavoriteStory(cursor);
                if (story != null)
                    favoriteStories.add(story);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return favoriteStories;
    }

    public List<FavoriteStory> queryAll() {
        Cursor cursor = readable.query(FavoriteStoryDB.FAVORITE_STORY_TABLE_NAME, null, null, null, null, null, null);
        List<FavoriteStory> favoriteStories = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                FavoriteStory story = cursor2FavoriteStory(cursor);
                if (story != null)
                    favoriteStories.add(story);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return favoriteStories;
    }

    private FavoriteStory cursor2FavoriteStory(Cursor cursor) {
        String storyId = cursor.getString(cursor.getColumnIndex(FavoriteStoryDB.KEY_STORY_ID));
        String body = cursor.getString(cursor.getColumnIndex(FavoriteStoryDB.KEY_STORY_BODY));
        String title = cursor.getString(cursor.getColumnIndex(FavoriteStoryDB.KEY_STORY_TITLE));
        String desc = cursor.getString(cursor.getColumnIndex(FavoriteStoryDB.KEY_STORY_DESC));
        String author = cursor.getString(cursor.getColumnIndex(FavoriteStoryDB.KEY_STORY_AUTHOR));
        String editedTimestamp = cursor.getString(cursor.getColumnIndex(FavoriteStoryDB.KEY_STORY_EDITED_TIME_STAMP));
        String shareUrl = cursor.getString(cursor.getColumnIndex(FavoriteStoryDB.KEY_STORY_SHARE_URL));
        String imgsrc = cursor.getString(cursor.getColumnIndex(FavoriteStoryDB.KEY_STORY_IMAGE_SOURCE));
        String imgUrl = cursor.getString(cursor.getColumnIndex(FavoriteStoryDB.KEY_STORY_IMAGE_URL));
        String imgPath = cursor.getString(cursor.getColumnIndex(FavoriteStoryDB.KEY_STORY_IMAGE_PATH));
        String downloadedTimestamp = cursor.getString(cursor.getColumnIndex(FavoriteStoryDB.KEY_STORY_DOWNLOADED_TIME_STAMP));
        String staredTimestamp = cursor.getString(cursor.getColumnIndex(FavoriteStoryDB.KEY_STORY_STARED_TIME_STAMP));
        String type = cursor.getString(cursor.getColumnIndex(FavoriteStoryDB.KEY_STORY_TYPE));
        String sectionId = cursor.getString(cursor.getColumnIndex(FavoriteStoryDB.KEY_STORY_BELONG_SECTION_ID));
        String sectionName = cursor.getString(cursor.getColumnIndex(FavoriteStoryDB.KEY_STORY_BELONG_SECTION_NAME));
        String sectionThumbnail = cursor.getString(cursor.getColumnIndex(FavoriteStoryDB.KEY_STORY_BELONG_SECTION_THUMBNAIL));
        String gaPrefix = cursor.getString(cursor.getColumnIndex(FavoriteStoryDB.KEY_STORY_GA_PREFIX));
        BaseSection section = null;
        if (sectionId != null) {
            section = new BaseSection(sectionName, Integer.valueOf(sectionId), sectionThumbnail);
        }
        FavoriteStory story = new FavoriteStory(storyId, editedTimestamp, title, desc, author, shareUrl, imgUrl, imgsrc, body, downloadedTimestamp, staredTimestamp, imgPath, type, gaPrefix, section);
        return story;
    }
}
