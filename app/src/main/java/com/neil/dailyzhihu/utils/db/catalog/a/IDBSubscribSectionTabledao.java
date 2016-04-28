package com.neil.dailyzhihu.utils.db.catalog.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.neil.dailyzhihu.bean.cleanlayer.CleanSectionAndThemeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neil on 2016/4/27.
 */
public class IDBSubscribSectionTabledao implements IDBSubscribSectionTable {
    private static final String LOG_TAG = IDBSubscribSectionTabledao.class.getSimpleName();
    private SQLiteDatabase writable;
    private SQLiteDatabase readable;
    private String tableName;
    private Context context;

    public IDBSubscribSectionTabledao(Context context) {
        MyDBHelper openHelper = new MyDBHelper(context);
        readable = openHelper.getReadableDatabase();
        writable = openHelper.getWritableDatabase();
        tableName = MyDBHelper.ConstantSectionDB.SUBSCRIBE_SECTION_TABLE_NAME;
        this.context = context;
    }

    @Override
    public long addSubscribSection(int sectionId, long currentMillies) {
        String sectionIdStr = sectionId + "";
        if (isSubscribtionExists(sectionId))
            return 0;
        String timestamp = currentMillies + "";
        //// TODO: 2016/4/27 封装函数
        String date = "";
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDBHelper.ConstantSectionDB.KEY_SECTION_ID, sectionIdStr);
        contentValues.put(MyDBHelper.ConstantSectionDB.SUBSCRIBE_DATE, date);
        contentValues.put(MyDBHelper.ConstantSectionDB.SUBSCRIBE_TIMESTAMP, timestamp);
        return writable.insert(tableName, null, contentValues);
    }

    @Override
    public long dropSubscribSection(int sectionId) {
        return writable.delete(tableName, MyDBHelper.ConstantSectionDB.KEY_SECTION_ID + "=?", new String[]{sectionId + ""});
    }

    public boolean isSubscribtionExists(int sectionId) {
        Cursor cursor = readable.query(tableName, null, MyDBHelper.ConstantSectionDB.KEY_SECTION_ID + "=?", new String[]{sectionId + ""}, null, null, null);
        if (cursor.moveToFirst()) {
            if (cursor.getCount() > 0)
                return true;
        }
        return false;
    }

    @Override
    public List<CleanSectionAndThemeBean> queryAllSubscribSection() {
        Cursor cursor = readable.query(tableName, null, null, null, null, null, null);
        List<CleanSectionAndThemeBean> sectionList = null;
        if (cursor.moveToFirst()) {
            sectionList = new ArrayList<>();
            do {
                String sectionId = cursor.getString(cursor.getColumnIndex(MyDBHelper.ConstantSectionDB.KEY_SECTION_ID));
                if (sectionId != null) {
                    CleanSectionAndThemeBean cleanSectionAndThemeBean = DBFactory.getsIDBSectionBeanTabledao(context).querySectionBeanBySectionId(Integer.valueOf(sectionId));
                    if (cleanSectionAndThemeBean != null)
                        sectionList.add(cleanSectionAndThemeBean);
                }
            } while (cursor.moveToNext());
        }
        return sectionList;
    }
}
