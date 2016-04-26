package com.neil.dailyzhihu.utils.db.catalog.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.neil.dailyzhihu.bean.cleanlayer.SimpleStory;

import java.util.ArrayList;
import java.util.List;

public class IDBSpecialSimpleStoryTabledao implements IDBSpecialSimpleStoryTable {
    private static final String LOG_TAG = IDBSpecialSimpleStoryTabledao.class.getSimpleName();
    private SQLiteDatabase writable;
    private SQLiteDatabase readable;

    private Context context;

    public IDBSpecialSimpleStoryTabledao(Context context) {
        MyDBHelper openHelper = new MyDBHelper(context);
        readable = openHelper.getReadableDatabase();
        writable = openHelper.getWritableDatabase();
        this.context = context;
    }

    @Override
    public long addSimpleStory(SimpleStory story, int flag) {
        String tableName = getTableName(flag);
        //首先添加实体
        if (DBFactory.getsIDBSimpleStoryTabledao(context).addSimpleStory(story) >= 0) {//当表内已存在时，返回值为0
            String date = story.getDate();
            String includeTimestamp = System.currentTimeMillis() + "";
            String storyId = story.getStoryId() + "";
            ContentValues contentValues = new ContentValues();
            contentValues.put(MyDBHelper.ConstantDB.KEY_SIMPLE_STORY_DATE, date);
            contentValues.put(MyDBHelper.ConstantDB.KEY_SIMPLE_STORY_ID, storyId);
            contentValues.put(MyDBHelper.ConstantDB.KEY_SIMPLE_STORY_INCLUDE_TIME_STAMP, includeTimestamp);
            return writable.insert(tableName, null, contentValues);
        }
        return -1;
    }

    @Override
    public int dropSimpleStory(int storyId, int flag) {
        String tableName = getTableName(flag);
        return writable.delete(tableName, MyDBHelper.ConstantDB.KEY_SIMPLE_STORY_ID + "=?", new String[]{storyId + ""});
    }

    @Override
    public int updateSimpleStory(int storyId, ContentValues contentValues, int flag) {
        String tableName = getTableName(flag);
        return writable.update(tableName, contentValues, MyDBHelper.ConstantDB.KEY_SIMPLE_STORY_ID + "=?", new String[]{storyId + ""});
    }

    @Override
    public SimpleStory querySimpleStoryById(int storyId, int flag) {
        String tableName = getTableName(flag);
        SimpleStory simpleStory = null;
        Cursor cursor = readable.query(tableName, null, MyDBHelper.ConstantDB.KEY_SIMPLE_STORY_ID + "=?", new String[]{storyId + ""}, null, null, null);
        if (cursor != null && cursor.getCount() > 0)
            simpleStory = DBFactory.getsIDBSimpleStoryTabledao(context).querySimpleStoryById(storyId);
        cursor.close();
        return simpleStory;
    }

    @Override
    public List<SimpleStory> querySimpleStoryByDate(String storyDate, int flag) {
        String tableName = getTableName(flag);
        List<SimpleStory> simpleStoryList = null;
        Cursor cursor = readable.query(tableName, null, MyDBHelper.ConstantDB.KEY_SIMPLE_STORY_DATE + "=?", new String[]{storyDate}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            simpleStoryList = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    String storyId = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantDB.KEY_SIMPLE_STORY_ID));
                    SimpleStory simpleStory = DBFactory.getsIDBSimpleStoryTabledao(context).querySimpleStoryById(Integer.valueOf(storyId));
                    if (simpleStory != null)
                        simpleStoryList.add(simpleStory);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return simpleStoryList;
    }

    @Override
    public List<SimpleStory> queryAllSimpleStory(int flag) {
        String tableName = getTableName(flag);
        List<SimpleStory> simpleStoryList = null;
        Cursor cursor = readable.query(tableName, null, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            simpleStoryList = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    String storyId = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantDB.KEY_SIMPLE_STORY_ID));
                    SimpleStory simpleStory = DBFactory.getsIDBSimpleStoryTabledao(context).querySimpleStoryById(Integer.valueOf(storyId));
                    if (simpleStory != null)
                        simpleStoryList.add(simpleStory);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return simpleStoryList;
    }

    private String getTableName(int flag) {
        switch (flag) {
            case 0:
                return MyDBHelper.ConstantDB.LATEST_SIMPLE_TABLE_NAME;
            case 1:
                return MyDBHelper.ConstantDB.HOT_SIMPLE_STORY_TABLE_NAME;
            case 2:
                return MyDBHelper.ConstantDB.BEFORE_SIMPLE_STORY_TABLE_NAME;
        }
        return null;
    }
}
