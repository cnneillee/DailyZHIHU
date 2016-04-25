package com.neil.dailyzhihu.utils.db.catalog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.neil.dailyzhihu.utils.db.SQLiteDataBaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Neil on 2016/4/25.
 */
public class HottestCatalogDBdao implements ILHDB {

    private static final String LOG_TAG = HottestCatalogDBdao.class.getSimpleName();
    private SQLiteDatabase writable;
    private SQLiteDatabase readable;

    public HottestCatalogDBdao(Context context) {
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

//        String downloadedDate = new SimpleDateFormat("yyyyMMdd").format(Integer.valueOf(downloadedTimestamp));
        ContentValues contentValues = new ContentValues();
        contentValues.put(HottestCatalogDB.KEY_STORY_ID, storyId);
        contentValues.put(HottestCatalogDB.KEY_STORY_TITLE, title);
        contentValues.put(HottestCatalogDB.KEY_STORY_TYPE, type);
        contentValues.put(HottestCatalogDB.KEY_STORY_GA_PREFIX, gaPrefix);
        contentValues.put(HottestCatalogDB.KEY_STORY_IMAGE_URL, imageUrl);
        contentValues.put(HottestCatalogDB.KEY_STORY_IMAGE_PATH, imagePath);
        contentValues.put(HottestCatalogDB.KEY_STORY_DOWNLOADED_TIME_STAMP, downloadedTimestamp);
        int res = (int) writable.insert(HottestCatalogDB.STORY_TABLE_NAME, null, contentValues);
        if (res >= 0)
            Log.e(LOG_TAG, "----" + title + type + gaPrefix + imageUrl + imagePath + downloadedTimestamp);
        return res;
    }

    @Override
    public int dropStoryCatalog(int storyId) {
        return writable.delete(HottestCatalogDB.STORY_TABLE_NAME, HottestCatalogDB.KEY_STORY_ID + "=?", new String[]{storyId + ""});
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
        return writable.update(HottestCatalogDB.STORY_TABLE_NAME, contentValues, HottestCatalogDB.KEY_STORY_ID + "=?", new String[]{storyId + ""});
    }

    @Override
    public List<StoryCatalog> queryStoryCatalogById(int storyId) {
        List<StoryCatalog> storyCatalogList = null;
        Cursor cursor = readable.query(HottestCatalogDB.STORY_TABLE_NAME, null, HottestCatalogDB.KEY_STORY_ID + "=?", new String[]{storyId + ""}, null, null, null);
        Log.e(LOG_TAG, "cursor:" + cursor.getCount());
        if (cursor.moveToFirst()) {
            storyCatalogList = new ArrayList<>();
            do {
                StoryCatalog story = cursor2StoryCatalog(cursor);
                if (story != null)
                    storyCatalogList.add(story);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return storyCatalogList;
    }

    private StoryCatalog cursor2StoryCatalog(Cursor cursor) {
        String storyId = cursor.getString(cursor.getColumnIndex(HottestCatalogDB.KEY_STORY_ID));
        String title = cursor.getString(cursor.getColumnIndex(HottestCatalogDB.KEY_STORY_TITLE));
        String imageUrl = cursor.getString(cursor.getColumnIndex(HottestCatalogDB.KEY_STORY_IMAGE_URL));
        String imagePath = cursor.getString(cursor.getColumnIndex(HottestCatalogDB.KEY_STORY_IMAGE_PATH));
        String downloadedTimestamp = cursor.getString(cursor.getColumnIndex(HottestCatalogDB.KEY_STORY_DOWNLOADED_TIME_STAMP));
        String type = cursor.getString(cursor.getColumnIndex(HottestCatalogDB.KEY_STORY_TYPE));
        String gaPrefix = cursor.getString(cursor.getColumnIndex(HottestCatalogDB.KEY_STORY_GA_PREFIX));
        StoryCatalog storyCatalog = new StoryCatalog(storyId, title, imageUrl, imagePath, type, gaPrefix, downloadedTimestamp);
        return storyCatalog;
    }

    @Override
    public List<StoryCatalog> queryStoryCatalogByDownloadedDate(String storyDownloadedDate) {
        List<StoryCatalog> storyCatalogList = null;
        Cursor cursor = readable.query(HottestCatalogDB.STORY_TABLE_NAME, null, HottestCatalogDB.KEY_STORY_DOWNLOADED_DATE + "=?", new String[]{storyDownloadedDate}, null, null, null);
        if (cursor.moveToFirst()) {
            storyCatalogList = new ArrayList<>();
            Log.e(LOG_TAG, "cursor:" + cursor.getCount());
            do {
                StoryCatalog story = cursor2StoryCatalog(cursor);
                if (story != null)
                    storyCatalogList.add(story);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return storyCatalogList;
    }

    public List<StoryCatalog> queryAllStoryCatalog() {
        List<StoryCatalog> storyCatalogList = null;
        Cursor cursor = readable.query(HottestCatalogDB.STORY_TABLE_NAME, null, null, null, null, null, null);
        Log.e(LOG_TAG, "cursor:" + cursor.getCount());
        if (cursor.moveToFirst()) {
            storyCatalogList = new ArrayList<>();
            do {
                StoryCatalog story = cursor2StoryCatalog(cursor);
                if (story != null)
                    storyCatalogList.add(story);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return storyCatalogList;
    }
}
