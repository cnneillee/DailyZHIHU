package com.neil.dailyzhihu.utils.db.catalog.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.neil.dailyzhihu.bean.ShareRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neil on 2016/4/27.
 */
public class IDBShareRecordDetailStoryTabledao implements IDBShareRecordDetailStoryTable {
    private static final String LOG_TAG = IDBShareRecordDetailStoryTabledao.class.getSimpleName();
    private SQLiteDatabase writable;
    private SQLiteDatabase readable;
    private String tableName;

    public IDBShareRecordDetailStoryTabledao(Context context) {
        MyDBHelper openHelper = new MyDBHelper(context);
        readable = openHelper.getReadableDatabase();
        writable = openHelper.getWritableDatabase();
        tableName = MyDBHelper.ConstantDetailStoryDB.SHARE_RECORD_DETAIL_STORY_TABLE_NAME;
    }

    @Override
    public long addShareRecord(ShareRecord shareRecord) {
        String storyId = shareRecord.getStoryId() + "";
        String date = shareRecord.getDate();
        String methodType = shareRecord.getMethodType();
        String platform = shareRecord.getPlatform();
        String currentMillies = shareRecord.getCurrentMillies() + "";
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_ID, storyId);
        contentValues.put(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_SHARE_RECORD_DATE, date);
        contentValues.put(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_SHARE_RECORD_METHOD_TYPE, methodType);
        contentValues.put(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_SHARE_RECORD_PLATFORM, platform);
        contentValues.put(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_SHARE_RECORD_TIME_STAMP, currentMillies);
        return writable.insert(tableName, null, contentValues);
    }

    @Override
    public List<ShareRecord> queryAllShareRecord() {
        List<ShareRecord> shareRecordList = null;
        Cursor cursor = readable.query(tableName, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            shareRecordList = new ArrayList<>();
            Log.e(LOG_TAG, "cursor:" + cursor.getCount());
            do {
                ShareRecord record = cursor2ShareRecord(cursor);
                if (record != null)
                    shareRecordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return shareRecordList;
    }

    public List<Integer> queryAllShareRecordStoryId() {
        List<Integer> shareRecordStoryIdList = null;
        Cursor cursor = readable.query(tableName, new String[]{MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_ID}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            shareRecordStoryIdList = new ArrayList<>();
            Log.e(LOG_TAG, "cursor:" + cursor.getCount());
            do {
                String idStr = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_ID));
                if (idStr != null)
                    shareRecordStoryIdList.add(Integer.valueOf(idStr));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return shareRecordStoryIdList;
    }

    @Override
    public List<ShareRecord> queryShareRecordByStoryId(int storyId) {
        List<ShareRecord> shareRecordList = null;
        Cursor cursor = readable.query(tableName, null, MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_ID, new String[]{storyId + ""}, null, null, null);
        if (cursor.moveToFirst()) {
            shareRecordList = new ArrayList<>();
            Log.e(LOG_TAG, "cursor:" + cursor.getCount());
            do {
                ShareRecord record = cursor2ShareRecord(cursor);
                if (record != null)
                    shareRecordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return shareRecordList;
    }

    @Override
    public List<ShareRecord> queryShareRecordByDate(String date) {
        List<ShareRecord> shareRecordList = null;
        Cursor cursor = readable.query(tableName, null, MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_SHARE_RECORD_DATE, new String[]{date}, null, null, null);
        if (cursor.moveToFirst()) {
            shareRecordList = new ArrayList<>();
            Log.e(LOG_TAG, "cursor:" + cursor.getCount());
            do {
                ShareRecord record = cursor2ShareRecord(cursor);
                if (record != null)
                    shareRecordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return shareRecordList;
    }

    @Override
    public List<ShareRecord> queryShareRecordByMethodType(String methodType) {
        List<ShareRecord> shareRecordList = null;
        Cursor cursor = readable.query(tableName, null, MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_SHARE_RECORD_METHOD_TYPE, new String[]{methodType}, null, null, null);
        if (cursor.moveToFirst()) {
            shareRecordList = new ArrayList<>();
            Log.e(LOG_TAG, "cursor:" + cursor.getCount());
            do {
                ShareRecord record = cursor2ShareRecord(cursor);
                if (record != null)
                    shareRecordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return shareRecordList;
    }

    @Override
    public List<ShareRecord> queryShareRecordByPlatform(String platform) {
        List<ShareRecord> shareRecordList = null;
        Cursor cursor = readable.query(tableName, null, MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_SHARE_RECORD_PLATFORM, new String[]{platform}, null, null, null);
        if (cursor.moveToFirst()) {
            shareRecordList = new ArrayList<>();
            Log.e(LOG_TAG, "cursor:" + cursor.getCount());
            do {
                ShareRecord record = cursor2ShareRecord(cursor);
                if (record != null)
                    shareRecordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return shareRecordList;
    }

    private ShareRecord cursor2ShareRecord(Cursor cursor) {
        String storyId = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_ID));
        String date = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_SHARE_RECORD_DATE));
        String methodType = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_SHARE_RECORD_METHOD_TYPE));
        String platform = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_SHARE_RECORD_PLATFORM));
        String timestamp = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantDetailStoryDB.KEY_DETAIL_STORY_SHARE_RECORD_TIME_STAMP));
        ShareRecord record = new ShareRecord(Integer.valueOf(storyId), Long.valueOf(timestamp), platform, methodType, date);
        return record;
    }
}
