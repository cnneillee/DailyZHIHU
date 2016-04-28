package com.neil.dailyzhihu.utils.db.catalog.a;

import com.neil.dailyzhihu.bean.cleanlayer.CleanSectionAndThemeBean;

import java.util.List;

/**
 * ConstantSectionDB.KEY_ROWID + " integer PRIMARY KEY autoincrement," +
 * ConstantSectionDB.KEY_SECTION_ID + " integer," +
 * ConstantSectionDB.SUBSCRIBE_TIMESTAMP + " varchar" + ");";
 */
public interface IDBSubscribSectionTable {
    /**
     * 添加订阅
     *
     * @param sectionId 订阅Section的id
     * @return 返回参数
     */
    long addSubscribSection(int sectionId, long currentMillies);

    long dropSubscribSection(int sectionId);

    List<CleanSectionAndThemeBean> queryAllSubscribSection();

}
