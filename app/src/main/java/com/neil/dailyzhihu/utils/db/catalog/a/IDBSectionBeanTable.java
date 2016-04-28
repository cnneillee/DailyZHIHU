package com.neil.dailyzhihu.utils.db.catalog.a;

import android.content.ContentValues;

import com.neil.dailyzhihu.bean.cleanlayer.CleanSectionAndThemeBean;

/**
 * "CREATE TABLE if not exists " + tableName + " (" +
 * ConstantSectionDB.KEY_ROWID + " integer PRIMARY KEY autoincrement," +
 * ConstantSectionDB.KEY_SECTION_NAME + " integer," +
 * ConstantSectionDB.KEY_SECTION_THUMBNAIL + " varchar," +
 * ConstantSectionDB.KEY_SECTION_DESC + " varchar," +
 * ConstantSectionDB.KEY_SECTION_ID + " varchar" + ");";
 */
public interface IDBSectionBeanTable {
    /**
     * 添加SectionBean
     *
     * @param sectionBean 实体
     * @return 返回参数
     */
    long addSectionBean(CleanSectionAndThemeBean sectionBean);

    long dropSectionBean(int sectionId);

    int updateSectionBean(int sectionId, ContentValues contentValues);

    CleanSectionAndThemeBean querySectionBeanBySectionId(int sectionId);

    CleanSectionAndThemeBean querySectionBeanBySectionName(String sectionName);
}
