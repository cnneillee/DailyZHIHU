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

    public int addSimpleStoryList(List<SimpleStory> storyList, int flag) {
        if (storyList == null) return -1;
        int errorcount = 0;
        for (SimpleStory story : storyList) {
            if (addSimpleStory(story, flag) < 0) errorcount++;
        }
        return errorcount;
    }

    @Override
    public long addSimpleStory(SimpleStory story, int flag) {
        String tableName = getTableName(flag);
        //首先添加实体
        if (DBFactory.getsIDBSimpleStoryTabledao(context).addSimpleStory(story) >= 0) {//当表内已存在时，返回值为0
            String date = story.getDate();
            String includeTimestamp = story.getDownloadTimeStamp();
            String storyId = story.getStoryId() + "";
            ContentValues contentValues = new ContentValues();
            contentValues.put(MyDBHelper.ConstantSimpleStoryDB.KEY_SIMPLE_STORY_DATE, date);
            contentValues.put(MyDBHelper.ConstantSimpleStoryDB.KEY_SIMPLE_STORY_ID, storyId);
            contentValues.put(MyDBHelper.ConstantSimpleStoryDB.KEY_SIMPLE_STORY_INCLUDE_TIME_STAMP, includeTimestamp);
            return writable.insert(tableName, null, contentValues);
        }
        return -1;
    }

    @Override
    public int dropSimpleStory(int storyId, int flag) {
        String tableName = getTableName(flag);
        return writable.delete(tableName, MyDBHelper.ConstantSimpleStoryDB.KEY_SIMPLE_STORY_ID + "=?", new String[]{storyId + ""});
    }

    @Override
    public int dropAllSimpleStory(int flag) {
        String tableName = getTableName(flag);
        return writable.delete(tableName, null, null);
    }

    @Override
    public int updateSimpleStory(int storyId, ContentValues contentValues, int flag) {
        String tableName = getTableName(flag);
        if (querySimpleStoryById(storyId, flag) == null) return -1;
        return writable.update(getTableName(3), contentValues, MyDBHelper.ConstantSimpleStoryDB.KEY_SIMPLE_STORY_ID + "=?", new String[]{storyId + ""});
    }

    @Override
    public SimpleStory querySimpleStoryById(int storyId, int flag) {
        String tableName = getTableName(flag);
        SimpleStory simpleStory = null;
        Cursor cursor = readable.query(tableName, null, MyDBHelper.ConstantSimpleStoryDB.KEY_SIMPLE_STORY_ID + "=?", new String[]{storyId + ""}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            simpleStory = DBFactory.getsIDBSimpleStoryTabledao(context).querySimpleStoryById(storyId);
            cursor.close();
        }
        return simpleStory;
    }

    public List<String> queryDate(int flag) {
        String tableName = getTableName(flag);
        List<String> dateList = null;
        Cursor cursor = readable.query(tableName, new String[]{MyDBHelper.ConstantSimpleStoryDB.KEY_SIMPLE_STORY_DATE}, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            dateList = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    String date = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantSimpleStoryDB.KEY_SIMPLE_STORY_DATE));
                    if (date != null)
                        dateList.add(date);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return dateList;
    }

    @Override
    public List<SimpleStory> querySimpleStoryByDate(String storyDate, int flag) {
        String tableName = getTableName(flag);
        List<SimpleStory> simpleStoryList = null;
        Cursor cursor = readable.query(tableName, null, MyDBHelper.ConstantSimpleStoryDB.KEY_SIMPLE_STORY_DATE + "=?", new String[]{storyDate}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            simpleStoryList = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    String storyId = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantSimpleStoryDB.KEY_SIMPLE_STORY_ID));
                    SimpleStory simpleStory = DBFactory.getsIDBSimpleStoryTabledao(context).querySimpleStoryById(Integer.valueOf(storyId));
                    if (simpleStory != null)
                        simpleStoryList.add(simpleStory);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return simpleStoryList;
    }

    @Override
    public List<SimpleStory> queryAllSimpleStory(int flag) {
        String tableName = getTableName(flag);
        List<SimpleStory> simpleStoryList = null;
        Cursor cursor = readable.query(tableName, null, null, null, null, null, MyDBHelper.ConstantSimpleStoryDB.KEY_SIMPLE_STORY_INCLUDE_TIME_STAMP + " DESC");
        if (cursor != null && cursor.getCount() > 0) {
            simpleStoryList = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    String storyId = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantSimpleStoryDB.KEY_SIMPLE_STORY_ID));
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
                return MyDBHelper.ConstantSimpleStoryDB.LATEST_SIMPLE_TABLE_NAME;
            case 1:
                return MyDBHelper.ConstantSimpleStoryDB.HOT_SIMPLE_STORY_TABLE_NAME;
            case 2:
                return MyDBHelper.ConstantSimpleStoryDB.BEFORE_SIMPLE_STORY_TABLE_NAME;
            case 3:
                return MyDBHelper.ConstantSimpleStoryDB.SIMPLE_STORY_TABLE_NAME;
        }
        return null;
    }
}
