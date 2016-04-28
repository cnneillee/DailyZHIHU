package com.neil.dailyzhihu.utils.db.catalog.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.neil.dailyzhihu.bean.ShareRecord;
import com.neil.dailyzhihu.bean.StarRecord;

import java.util.ArrayList;
import java.util.List;

public class IDBStarRecordDetailStoryTabledao implements IDBStarDetailStoryTable {
    private static final String LOG_TAG = IDBStarRecordDetailStoryTabledao.class.getSimpleName();
    private SQLiteDatabase writable;
    private SQLiteDatabase readable;
    private String tableName;

    public IDBStarRecordDetailStoryTabledao(Context context) {
        MyDBHelper openHelper = new MyDBHelper(context);
        readable = openHelper.getReadableDatabase();
        writable = openHelper.getWritableDatabase();
        tableName = MyDBHelper.ConstantDetailStoryDB.STAR_DETAIL_STORY_TABLE_NAME;
    }

    @Override
    public long addStarRecord(StarRecord starRecord) {
        String storyId = starRecord.getStoryId() + "";
        String date = starRecord.getDate();
        String catalog = starRecord.getCatalog();
        String currentMillies = starRecord.getTimeStamp() + "";
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_ID, storyId);
        contentValues.put(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_STAR_DATE, date);
        contentValues.put(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_STAR_CATALOG, catalog);
        contentValues.put(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_STAR_TIME_STAMP, currentMillies);
        return writable.insert(tableName, null, contentValues);
    }

    @Override
    public List<StarRecord> queryAllStarRecord() {
        List<StarRecord> starRecordList = null;
        Cursor cursor = readable.query(tableName, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            starRecordList = new ArrayList<>();
            Log.e(LOG_TAG, "cursor:" + cursor.getCount());
            do {
                StarRecord record = cursor2StarRecord(cursor);
                if (record != null)
                    starRecordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return starRecordList;
    }

    @Override
    public List<StarRecord> queryStarRecordByStoryId(int storyId) {
        List<StarRecord> starRecordList = null;
        Cursor cursor = readable.query(tableName, null, MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_ID, new String[]{storyId + ""}, null, null, null);
        if (cursor.moveToFirst()) {
            starRecordList = new ArrayList<>();
            Log.e(LOG_TAG, "cursor:" + cursor.getCount());
            do {
                StarRecord record = cursor2StarRecord(cursor);
                if (record != null)
                    starRecordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return starRecordList;
    }

    @Override
    public List<StarRecord> queryStarRecordByDate(String date) {
        List<StarRecord> starRecordList = null;
        Cursor cursor = readable.query(tableName, null, MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_STAR_DATE, new String[]{date}, null, null, null);
        if (cursor.moveToFirst()) {
            starRecordList = new ArrayList<>();
            Log.e(LOG_TAG, "cursor:" + cursor.getCount());
            do {
                StarRecord record = cursor2StarRecord(cursor);
                if (record != null)
                    starRecordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return starRecordList;
    }

    @Override
    public List<StarRecord> queryStarRecordByCatalog(String catalog) {
        List<StarRecord> StarRecord = null;
        Cursor cursor = readable.query(tableName, null, MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_STAR_CATALOG, new String[]{catalog}, null, null, null);
        if (cursor.moveToFirst()) {
            StarRecord = new ArrayList<>();
            Log.e(LOG_TAG, "cursor:" + cursor.getCount());
            do {
                StarRecord record = cursor2StarRecord(cursor);
                if (record != null)
                    StarRecord.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return StarRecord;
    }


    private StarRecord cursor2StarRecord(Cursor cursor) {
        String storyId = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_ID));
        String date = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_STAR_DATE));
        String catalog = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_STAR_CATALOG));
        String timestamp = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_STAR_TIME_STAMP));
        StarRecord record = new StarRecord(Integer.valueOf(storyId), date, Long.valueOf(timestamp), catalog);
        return record;
    }
}
