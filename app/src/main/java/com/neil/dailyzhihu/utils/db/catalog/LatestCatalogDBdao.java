package com.neil.dailyzhihu.utils.db.catalog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.neil.dailyzhihu.utils.db.SQLiteDataBaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neil on 2016/4/25.
 */
public class LatestCatalogDBdao implements ILHDB {

    private static final String LOG_TAG = LatestCatalogDBdao.class.getSimpleName();
    private SQLiteDatabase writable;
    private SQLiteDatabase readable;

    public LatestCatalogDBdao(Context context) {
        SQLiteDataBaseHelper openHelper = new SQLiteDataBaseHelper(context);
        readable = openHelper.getReadableDatabase();
        writable = openHelper.getWritableDatabase();
    }

    @Override
    public long addStoryCatalog(StoryCatalog story) {
        String storyId = story.getStoryId();
        if (queryStoryCatalogById(Integer.valueOf(storyId)) != null) {
            Log.e(LOG_TAG, "insert error:this story exists");
            return -1;
        }
        String title = story.getTitle();
        String type = story.getType();
        String gaPrefix = story.getGaPrefix();
        String imageUrl = story.getImageUrl();
        String imagePath = story.getImagePath();
        String downloadedTimestamp = story.getDownloadedTimestamp();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LatestCatalogDB.KEY_STORY_ID, storyId);
        contentValues.put(LatestCatalogDB.KEY_STORY_TITLE, title);
        contentValues.put(LatestCatalogDB.KEY_STORY_TYPE, type);
        contentValues.put(LatestCatalogDB.KEY_STORY_GA_PREFIX, gaPrefix);
        contentValues.put(LatestCatalogDB.KEY_STORY_IMAGE_URL, imageUrl);
        contentValues.put(LatestCatalogDB.KEY_STORY_IMAGE_PATH, imagePath);
        contentValues.put(LatestCatalogDB.KEY_STORY_DOWNLOADED_TIME_STAMP, downloadedTimestamp);
        return writable.insert(LatestCatalogDB.STORY_TABLE_NAME, null, contentValues);
    }

    @Override
    public int dropStoryCatalog(int storyId) {
        return writable.delete(LatestCatalogDB.STORY_TABLE_NAME, LatestCatalogDB.KEY_STORY_ID + "=?", new String[]{storyId + ""});
    }

    @Override
    public int updateStoryCatalog(int storyId, StoryCatalog newStoryCatalog) {
        if (queryStoryCatalogById(Integer.valueOf(storyId)) == null) {
            Log.e(LOG_TAG, "update error:this story doesnot exists");
            return -1;
        }
        String title = newStoryCatalog.getTitle();
        String type = newStoryCatalog.getType();
        String gaPrefix = newStoryCatalog.getGaPrefix();
        String imageUrl = newStoryCatalog.getImageUrl();
        String imagePath = newStoryCatalog.getImagePath();
        String downloadedTimestamp = newStoryCatalog.getDownloadedTimestamp();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LatestCatalogDB.KEY_STORY_ID, storyId);
        contentValues.put(LatestCatalogDB.KEY_STORY_TITLE, title);
        contentValues.put(LatestCatalogDB.KEY_STORY_TYPE, type);
        contentValues.put(LatestCatalogDB.KEY_STORY_GA_PREFIX, gaPrefix);
        contentValues.put(LatestCatalogDB.KEY_STORY_IMAGE_URL, imageUrl);
        contentValues.put(LatestCatalogDB.KEY_STORY_IMAGE_PATH, imagePath);
        contentValues.put(LatestCatalogDB.KEY_STORY_DOWNLOADED_TIME_STAMP, downloadedTimestamp);
        return writable.update(LatestCatalogDB.STORY_TABLE_NAME, contentValues, LatestCatalogDB.KEY_STORY_ID + "=?", new String[]{storyId + ""});
    }

    @Override
    public List<StoryCatalog> queryStoryCatalogById(int storyId) {
        List<StoryCatalog> storyCatalogList = null;
        Cursor cursor = readable.query(LatestCatalogDB.STORY_TABLE_NAME, null, LatestCatalogDB.KEY_STORY_ID + "=?", new String[]{storyId + ""}, null, null, null);
        if (cursor.moveToFirst()) {
            storyCatalogList = new ArrayList<>();
            do {
                StoryCatalog story = cursor2StoryCatalog(cursor);
                if (story != null)
                    storyCatalogList.add(story);
            } while (cursor.moveToNext());
        }
        return storyCatalogList;
    }

    private StoryCatalog cursor2StoryCatalog(Cursor cursor) {
        String storyId = cursor.getString(cursor.getColumnIndex(LatestCatalogDB.KEY_STORY_ID));
        String title = cursor.getString(cursor.getColumnIndex(LatestCatalogDB.KEY_STORY_TITLE));
        String imageUrl = cursor.getString(cursor.getColumnIndex(LatestCatalogDB.KEY_STORY_IMAGE_URL));
        String imagePath = cursor.getString(cursor.getColumnIndex(LatestCatalogDB.KEY_STORY_IMAGE_PATH));
        String downloadedTimestamp = cursor.getString(cursor.getColumnIndex(LatestCatalogDB.KEY_STORY_DOWNLOADED_TIME_STAMP));
        String type = cursor.getString(cursor.getColumnIndex(LatestCatalogDB.KEY_STORY_TYPE));
        String gaPrefix = cursor.getString(cursor.getColumnIndex(LatestCatalogDB.KEY_STORY_GA_PREFIX));
        StoryCatalog storyCatalog = new StoryCatalog(storyId, title, imageUrl, imagePath, type, gaPrefix, downloadedTimestamp);
        return storyCatalog;
    }


    @Override
    public List<StoryCatalog> queryStoryCatalogByDownloadedDate(String storyDownloadedDate) {
        List<StoryCatalog> storyCatalogList = null;
        Cursor cursor = readable.query(LatestCatalogDB.STORY_TABLE_NAME, null, LatestCatalogDB.KEY_STORY_DOWNLOADED_TIME_STAMP + "=?", new String[]{storyDownloadedDate}, null, null, null);
        if (cursor.moveToFirst()) {
            storyCatalogList = new ArrayList<>();
            do {
                StoryCatalog story = cursor2StoryCatalog(cursor);
                if (story != null)
                    storyCatalogList.add(story);
            } while (cursor.moveToNext());
        }
        return storyCatalogList;
    }

    @Override
    public List<StoryCatalog> queryAllStoryCatalog() {
        List<StoryCatalog> storyCatalogList = null;
        Cursor cursor = readable.query(LatestCatalogDB.STORY_TABLE_NAME, null, null, null, null, null, null);
        Log.e(LOG_TAG, "cursor:" + cursor.getCount());
        if (cursor.moveToFirst()) {
            storyCatalogList = new ArrayList<>();
            do {
                StoryCatalog story = cursor2StoryCatalog(cursor);
                if (story != null)
                    storyCatalogList.add(story);
            } while (cursor.moveToNext());
        }
        return storyCatalogList;
    }
}
