package com.neil.dailyzhihu.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Neil on 2016/4/22.
 */
public class SQLiteDataBaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "com.neillee.dailyzhihu";
    private String favoriteTableName;

    public SQLiteDataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        favoriteTableName = FavoriteStoryDB.getCreatedStatement();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(favoriteTableName);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
