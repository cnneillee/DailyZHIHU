package com.neil.dailyzhihu.model.db;

import android.content.Context;

import com.neil.dailyzhihu.model.bean.orignal.CertainStoryBean;
import com.orhanobut.logger.Logger;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.List;

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

    public List<CachedStory> queryAllStarStory() {
        List<StarRecord> records = mDaoSession.getStarRecordDao().loadAll();
        if (records == null || records.size() == 0) return null;
        List<CachedStory> cachedStories = new ArrayList<>();
        for (StarRecord record : records)
            cachedStories.add(queryCachedStory(record.getStoryId()));
        return cachedStories;
    }

    public void deleteCachedStory(int storyId) {
        CachedStory cachedStory = queryCachedStory(storyId);
        if (cachedStory != null) {
            mDaoSession.getCachedStoryDao().deleteInTx(cachedStory);
        }
    }

    public CachedStory queryCachedStory(int storyId) {
        return mDaoSession.getCachedStoryDao().queryBuilder()
                .where(CachedStoryDao.Properties.StoryId.eq(storyId))
                .build().unique();
    }

    public void cacheCachedStory(CertainStoryBean story) {
        CachedStory cachedStory = queryCachedStory(story.getId());
        if (cachedStory == null || cachedStory.getStoryId() == 0) {
            mDaoSession.getCachedStoryDao().insert(
                    new CachedStory(story.getId(),
                            story.getTitle(), story.getBody(),
                            story.getImage(), story.getImageSource()));
        } else {// todo update
            return;
        }
    }
}
