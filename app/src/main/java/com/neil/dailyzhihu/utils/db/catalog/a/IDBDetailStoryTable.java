package com.neil.dailyzhihu.utils.db.catalog.a;

import android.content.ContentValues;

import com.neil.dailyzhihu.bean.cleanlayer.CleanDetailStory;
import com.neil.dailyzhihu.bean.orignallayer.DetailStory;

import java.util.List;

/**
 * Created by Neil on 2016/4/27.
 */
public interface IDBDetailStoryTable {
    /**
     * 向DetailStory表中添加DetailStory实体
     *
     * @param story 待添加的DetailStory
     * @return 返回参数（the row ID of the newly inserted row, or -1 if an error occurred）
     */
    long addDetailStory(CleanDetailStory story);

    /**
     * 向DetailStory表中删除DetailStory实体
     *
     * @param storyId 待删除的DetailStory实体的Id
     * @return 返回参数（the number of rows affected if a whereClause is passed in, 0 otherwise. To remove all rows and get a count pass "1" as the whereClause.）
     */
    int dropDetailStory(int storyId);

    /**
     * 更新DetailStory表中实体的信息
     *
     * @param storyId       待更新的DetailStory实体的Id
     * @param contentValues key包含在#MyDBHelper.ConstantDetailStoryDB#类中
     * @return 返回参数（the number of rows affected）
     */
    int updateDetailStory(int storyId, ContentValues contentValues);

    /**
     * 根据ID从DetailStory中查询到DetailStory实体
     *
     * @param storyId 要查询实体的ID
     * @return 查询到的DetailStory实体
     */
    CleanDetailStory queryDetailStoryById(int storyId);

    /**
     * 按DetailStory日期查询到DetailStory实体
     *
     * @param storyDate DetailStory的日期
     * @return 查询到的DetailStory实体列表
     */
    List<CleanDetailStory> queryDetailStoryByDate(String storyDate);

    /**
     * 查询所有的DetailStory实体
     *
     * @return 所有DetailStory实体列表
     */
    List<CleanDetailStory> queryAllDetailStory();
}
