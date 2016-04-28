package com.neil.dailyzhihu.utils.db.catalog.a;

import android.content.ContentValues;

import com.neil.dailyzhihu.bean.cleanlayer.SimpleStory;

import java.util.List;

/**
 * SimpleStory表的接口
 */
public interface IDBSimpleStoryTable {
    /**
     * 向SimpleStory表中添加SimpleStory实体
     *
     * @param story 待添加的SimpleStory
     * @return 返回参数（the row ID of the newly inserted row, or -1 if an error occurred）
     */
    long addSimpleStory(SimpleStory story);

    /**
     * 向SimpleStory表中删除SimpleStory实体
     *
     * @param storyId 待删除的SimpleStory实体的Id
     * @return 返回参数（the number of rows affected if a whereClause is passed in, 0 otherwise. To remove all rows and get a count pass "1" as the whereClause.）
     */
    int dropSimpleStory(int storyId);

    /**
     * 更新SimpleStory表中实体的信息
     *
     * @param storyId       待更新的SimpleStory实体的Id
     * @param contentValues key包含在#MyDBHelper.ConstantSimpleStoryDB#类中
     * @return 返回参数（the number of rows affected）
     */
    int updateSimpleStory(int storyId, ContentValues contentValues);

    /**
     * 根据ID从SimpleStory中查询到SimpleStory实体
     *
     * @param storyId 要查询实体的ID
     * @return 查询到的SimpleStory实体
     */
    SimpleStory querySimpleStoryById(int storyId);

    /**
     * 按SimpleStory下载日期查询到SimpleStory实体
     *
     * @param storyDownloadedDate SimpleStory的下载日期
     * @return 查询到的SimpleStory实体列表
     */
    List<SimpleStory> querySimpleStoryByDownloadedDate(String storyDownloadedDate);

    /**
     * 查询所有的SimpleStory实体
     *
     * @return 所有SimpleStory实体列表
     */
    List<SimpleStory> queryAllSimpleStory();
}
