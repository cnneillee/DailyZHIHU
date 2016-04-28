package com.neil.dailyzhihu.utils.db.catalog.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.neil.dailyzhihu.bean.cleanlayer.CleanDetailStory;
import com.neil.dailyzhihu.bean.cleanlayer.CleanSectionAndThemeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neil on 2016/4/27.
 */
public class IDBDetailStoryTabledao implements IDBDetailStoryTable {
    private static final String LOG_TAG = IDBDetailStoryTabledao.class.getSimpleName();
    private SQLiteDatabase readable;
    private SQLiteDatabase writable;
    private String tableName;

    public IDBDetailStoryTabledao(Context context) {
        MyDBHelper myDBHelper = new MyDBHelper(context);
        readable = myDBHelper.getReadableDatabase();
        writable = myDBHelper.getWritableDatabase();
        tableName = MyDBHelper.ConstantDetailStoryDB.DETAIL_STORY_TABLE_NAME;
    }

    @Override
    public long addDetailStory(CleanDetailStory story) {
        String storyId = story.getStoryId() + "";
        if (queryDetailStoryById(Integer.valueOf(storyId)) != null) {
            Log.e(LOG_TAG, "insert erro:has existed");
            return 0;
        }
        CleanSectionAndThemeBean sectionBean = story.getCleanSectionAndThemeBean();
        String sectionId = sectionBean.getSectionId() + "";
        String body = story.getBody();
        List<String> css = story.getCss();
        String gaPrefix = story.getGaPrefix();
        String imageUrl = story.getImage();
        String imageSource = story.getImageSource();
        String imageTiny = story.getImages().get(0);
//        String storyId = story.getJs();
        String shareUrl = story.getShareUrl();
        String title = story.getTitle();
        String type = story.getType() + "";

        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_ID, storyId);
        contentValues.put(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_BODY, body);
        contentValues.put(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_GA_PREFIX, gaPrefix);
        contentValues.put(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_IMAGE_URL, imageUrl);
        contentValues.put(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_IMAGE_SOURCE, imageSource);
        contentValues.put(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_IMAGE_URL_TINY, imageTiny);
        contentValues.put(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_SHARE_URL, shareUrl);
        contentValues.put(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_TITLE, title);
        contentValues.put(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_TYPE, type);
        contentValues.put(MyDBHelper.ConstantDetailStoryDB.KEY_SECTION_ID, sectionId);
        return writable.insert(MyDBHelper.ConstantDetailStoryDB.DETAIL_STORY_TABLE_NAME, null, contentValues);
    }

    @Override
    public int dropDetailStory(int storyId) {
        return writable.delete(tableName, MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_ID + "=?", new String[]{storyId + ""});
    }

    @Override
    public int updateDetailStory(int storyId, ContentValues contentValues) {
        return writable.update(tableName, contentValues, MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_ID + "=?", new String[]{storyId + ""});
    }

    @Override
    public CleanDetailStory queryDetailStoryById(int storyId) {
        Cursor cursor = readable.query(tableName, null, MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_ID + "=?", new String[]{storyId + ""}, null, null, null);
        CleanDetailStory story = null;
        if (cursor.moveToFirst()) {
            Log.e(LOG_TAG, "cursor:" + cursor.getCount());
            do {
                story = cursor2CleanDetailStory(cursor);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return story;
    }

    @Override
    public List<CleanDetailStory> queryDetailStoryByDate(String storyDate) {
        List<CleanDetailStory> simpleStoryList = null;
        Cursor cursor = readable.query(tableName, null, MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_DATE + "=?", new String[]{}, null, null, null);
        if (cursor.moveToFirst()) {
            simpleStoryList = new ArrayList<>();
            Log.e(LOG_TAG, "cursor:" + cursor.getCount());
            do {
                CleanDetailStory story = cursor2CleanDetailStory(cursor);
                if (story != null)
                    simpleStoryList.add(story);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return simpleStoryList;
    }

    @Override
    public List<CleanDetailStory> queryAllDetailStory() {
        List<CleanDetailStory> simpleStoryList = null;
        Cursor cursor = readable.query(tableName, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            simpleStoryList = new ArrayList<>();
            Log.e(LOG_TAG, "cursor:" + cursor.getCount());
            do {
                CleanDetailStory story = cursor2CleanDetailStory(cursor);
                if (story != null)
                    simpleStoryList.add(story);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return simpleStoryList;
    }

    private CleanDetailStory cursor2CleanDetailStory(Cursor cursor) {
        String storyId = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_ID));
        String body = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_BODY));
        String gaPrefix = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_GA_PREFIX));
        String imageUrl = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_IMAGE_URL));
        String imageSource = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_IMAGE_SOURCE));
        String imageTiny = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_IMAGE_URL_TINY));
        String shareUrl = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_SHARE_URL));
        String title = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_TITLE));
        String type = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_TYPE));
        String sectionId = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantDetailStoryDB.KEY_SECTION_ID));
        //TODO 填充CleanSectionBean
        CleanSectionAndThemeBean cleanSectionAndThemeBean = new CleanSectionAndThemeBean();
        cleanSectionAndThemeBean.setSectionId(Integer.valueOf(sectionId));
        List<String> images = new ArrayList<>();
        images.add(imageTiny);
        CleanDetailStory story = new CleanDetailStory(body, imageSource, title, imageUrl, shareUrl, gaPrefix, cleanSectionAndThemeBean, Integer.valueOf(type), Integer.valueOf(storyId), null, images, null);
        return story;
    }
}
