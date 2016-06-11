package com.neil.dailyzhihu.utils.db.catalog.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.neil.dailyzhihu.bean.cleanlayer.CleanSectionAndThemeBean;

import java.util.ArrayList;

public class IDBSectionBeanTabledao implements IDBSectionBeanTable {
    private static final String LOG_TAG = IDBSectionBeanTabledao.class.getSimpleName();
    private SQLiteDatabase writable;
    private SQLiteDatabase readable;
    private String tableName;


    public IDBSectionBeanTabledao(Context context) {
        MyDBHelper openHelper = new MyDBHelper(context);
        readable = openHelper.getReadableDatabase();
        writable = openHelper.getWritableDatabase();
        tableName = MyDBHelper.ConstantSectionDB.SECTION_TABLE_NAME;
    }

    @Override
    public long addSectionBean(CleanSectionAndThemeBean sectionBean) {
        String sectionId = sectionBean.getSectionId() + "";
        String desc = sectionBean.getDescription() + "";
        String name = sectionBean.getName() + "";
        String thumbnail = sectionBean.getThumbnail() + "";
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDBHelper.ConstantSectionDB.KEY_SECTION_ID, sectionId);
        contentValues.put(MyDBHelper.ConstantSectionDB.KEY_SECTION_DESC, desc);
        contentValues.put(MyDBHelper.ConstantSectionDB.KEY_SECTION_NAME, name);
        contentValues.put(MyDBHelper.ConstantSectionDB.KEY_SECTION_THUMBNAIL, thumbnail);
        return writable.insert(tableName, null, contentValues);
    }

    @Override
    public long dropSectionBean(int sectionId) {
        return writable.delete(tableName, MyDBHelper.ConstantSectionDB.KEY_SECTION_ID + "=?", new String[]{sectionId + ""});
    }

    @Override
    public int updateSectionBean(int sectionId, ContentValues contentValues) {
        return writable.update(tableName, contentValues, MyDBHelper.ConstantSectionDB.KEY_SECTION_ID + "=?", new String[]{sectionId + ""});
    }

    @Override
    public CleanSectionAndThemeBean querySectionBeanBySectionId(int sectionId) {
        Cursor cursor1 = readable.query(tableName, null, null, null, null, null, null);
        if (cursor1.moveToFirst()) {
            Log.e(LOG_TAG, "cursor1.SIZE()" + cursor1.getCount());
            do {
                String sectionIdstr = cursor1.getString(cursor1.getColumnIndex(MyDBHelper.ConstantSectionDB.KEY_SECTION_ID));
                if (sectionIdstr != null) Log.e(LOG_TAG, "sectionIdstr:" + sectionIdstr);
            } while (cursor1.moveToNext());
        }
        cursor1.close();
        Log.e(LOG_TAG, "sectionId:" + sectionId);
        Cursor cursor = readable.query(tableName, null, MyDBHelper.ConstantSectionDB.KEY_SECTION_ID + "=?", new String[]{sectionId + ""}, null, null, null);
        CleanSectionAndThemeBean sectionBean = null;
        if (cursor.moveToFirst()) {
            sectionBean = cursor2CleanSectionBean(cursor);
        }
        cursor.close();
        return sectionBean;
    }

    @Override
    public CleanSectionAndThemeBean querySectionBeanBySectionName(String sectionName) {
        Cursor cursor = readable.query(tableName, null, MyDBHelper.ConstantSectionDB.KEY_SECTION_NAME + "=?", new String[]{sectionName}, null, null, null);
        CleanSectionAndThemeBean sectionBean = null;
        if (cursor.moveToFirst()) {
            sectionBean = cursor2CleanSectionBean(cursor);
        }
        cursor.close();
        return sectionBean;
    }

    private CleanSectionAndThemeBean cursor2CleanSectionBean(Cursor cursor) {
        String thumbnail = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantSectionDB.KEY_SECTION_THUMBNAIL));
        String sectionId = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantSectionDB.KEY_SECTION_ID));
        String name = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantSectionDB.KEY_SECTION_DESC));
        String description = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantSectionDB.KEY_SECTION_ID));
        CleanSectionAndThemeBean section = new CleanSectionAndThemeBean(thumbnail, Integer.valueOf(sectionId), name, description);
        return section;
    }
}
