package com.neil.dailyzhihu.model.db;

import android.content.Context;

import com.orhanobut.logger.Logger;

import org.greenrobot.greendao.database.Database;

/**
 * 作者：Neil on 2017/5/30 17:54.
 * 邮箱：cn.neillee@gmail.com
 */

public class GreenDaoHelper {
    private static final String DB_NAME = "DailyApp.db";

    private DaoSession mDaoSession;

    public GreenDaoHelper(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME);
        Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }

    public StarRecord queryStarRecord(int storyId) {
        StarRecordDao recordDao = mDaoSession.getStarRecordDao();
        return recordDao.queryBuilder().where(StarRecordDao
                .Properties.StoryId.eq(storyId)).build().unique();
    }

    public void insertStarRecord(StarRecord record) {
        if (queryStarRecord(record.getStoryId()) != null) {
            Logger.e("Error in insertStarRecord due to EXIST storyId:{}", record.getStoryId());
        } else {
            mDaoSession.getStarRecordDao().insert(record);
        }
    }

    public void deleteStarRecord(StarRecord record) {
        mDaoSession.getStarRecordDao().delete(record);
    }
}
