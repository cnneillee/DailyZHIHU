package com.neil.dailyzhihu.utils.db.catalog.a;

import com.neil.dailyzhihu.bean.ShareRecord;

import java.util.List;

/**
 * ConstantDetailStoryDB.KEY_ROWID + " integer PRIMARY KEY autoincrement," +
 * ConstantDetailStoryDB.KEY_DETAIL_STORY_ID + " integer," +
 * ConstantDetailStoryDB.KEY_DETAIL_STORY_SHARE_RECORD_DATE + " varchar," +
 * ConstantDetailStoryDB.KEY_DETAIL_STORY_SHARE_RECORD_METHOD_TYPE + " varchar," +
 * ConstantDetailStoryDB.KEY_DETAIL_STORY_SHARE_RECORD_PLATFORM + " varchar," +
 * ConstantDetailStoryDB.KEY_DETAIL_STORY_SHARE_RECORD_TIME_STAMP + " varchar" + ");";
 */
public interface IDBShareRecordDetailStoryTable {
    /**
     * 添加分享记录
     *
     * @param shareRecord 分享记录
     * @return 返回参数
     */
    long addShareRecord(ShareRecord shareRecord);

    List<ShareRecord> queryAllShareRecord();

    List<ShareRecord> queryShareRecordByStoryId(int storyId);

    List<ShareRecord> queryShareRecordByDate(String date);

    List<ShareRecord> queryShareRecordByMethodType(String methodType);

    List<ShareRecord> queryShareRecordByPlatform(String platform);
}
