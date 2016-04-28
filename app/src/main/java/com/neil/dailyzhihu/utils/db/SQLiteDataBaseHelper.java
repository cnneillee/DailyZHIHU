//package com.neil.dailyzhihu.utils.db;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//public class SQLiteDataBaseHelper extends SQLiteOpenHelper {
//
//    public static final String DB_NAME = "com.neillee.dailyzhihu";
//    private String favoriteTableName;
//    private String dailyTableName;
//    private String storyCatalogTableName;
//    private String hotteststoryCatalogTableName;
//
//    public SQLiteDataBaseHelper(Context context) {
//        super(context, DB_NAME, null, 1);
//        favoriteTableName = FavoriteStoryDB.getCreatedStatement();
//        dailyTableName = StoryDB.getCreateStatement();
//        storyCatalogTableName = LatestCatalogDB.getCreateStatement();
//        hotteststoryCatalogTableName = HottestCatalogDB.getCreateStatement();
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(favoriteTableName);
//        db.execSQL(dailyTableName);
//        db.execSQL(storyCatalogTableName);
//        db.execSQL(hotteststoryCatalogTableName);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//    }
//}
