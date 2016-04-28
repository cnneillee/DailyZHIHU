package com.neil.dailyzhihu.utils.db.catalog.a;

import com.neil.dailyzhihu.bean.ShareRecord;
import com.neil.dailyzhihu.bean.StarRecord;

import java.util.List;

/**
 * ConstantDetailStoryDB.KEY_ROWID + " integer PRIMARY KEY autoincrement," +
 * ConstantDetailStoryDB.KEY_DETAIL_STORY_ID + " integer," +
 * ConstantDetailStoryDB.KEY_DETAIL_STORY_STAR_TIME_STAMP + " varchar," +
 * ConstantDetailStoryDB.KEY_DETAIL_STORY_STAR_DATE + " varchar," +
 * ConstantDetailStoryDB.KEY_DETAIL_STORY_STAR_CATALOG + " varchar" + ");";
 */
public interface IDBStarDetailStoryTable {
    /**
     * 添加收藏记录
     *
     * @param starRecord 收藏记录
     * @return 返回参数
     */
    long addStarRecord(StarRecord starRecord);

    List<StarRecord> queryAllStarRecord();

    List<StarRecord> queryStarRecordByStoryId(int storyId);

    List<StarRecord> queryStarRecordByDate(String date);

    List<StarRecord> queryStarRecordByCatalog(String catalog);

}
