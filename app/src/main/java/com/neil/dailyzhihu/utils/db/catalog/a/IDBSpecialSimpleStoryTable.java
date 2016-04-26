package com.neil.dailyzhihu.utils.db.catalog.a;

import android.content.ContentValues;

import com.neil.dailyzhihu.bean.cleanlayer.SimpleStory;

import java.util.List;

/**
 * LatestSimpleStory、HotSimpleStory、BeforeSimpleStory表的接口
 */
public interface IDBSpecialSimpleStoryTable {
    /**
     * 向表中添加SimpleStory实体索引
     *
     * @param story 待添加的SimpleStory
     * @param flag  提示操作的表
     * @return 返回参数（the row ID of the newly inserted row, or -1 if an error occurred）
     */
    long addSimpleStory(SimpleStory story, int flag);

    /**
     * 从表中删除SimpleStory
     *
     * @param storyId 待删除的SimpleStory的Id
     * @param flag    提示操作的表
     * @return 返回参数（the number of rows affected if a whereClause is passed in, 0 otherwise. To remove all rows and get a count pass "1" as the whereClause.）
     */
    int dropSimpleStory(int storyId, int flag);

    /**
     * 更新表中实体的信息
     *
     * @param storyId       待更新的SimpleStory的Id
     * @param contentValues SimpleStory实体的更新内容,key在ConstantDB中
     * @param flag          提示操作的表
     * @return 返回参数（the number of rows affected）
     */
    int updateSimpleStory(int storyId, ContentValues contentValues, int flag);

    /**
     * 根据ID从SimpleStory中查询到SimpleStory实体
     *
     * @param storyId 要查询实体的ID
     * @param flag    提示操作的表
     * @return 查询到的SimpleStory实体
     */
    SimpleStory querySimpleStoryById(int storyId, int flag);

    /**
     * 按SimpleStory下载日期查询到SimpleStory实体
     *
     * @param storyDate SimpleStory的日期
     * @param flag      提示操作的表
     * @return 查询到的SimpleStory实体列表
     */
    List<SimpleStory> querySimpleStoryByDate(String storyDate, int flag);

    /**
     * 查询所有的SimpleStory实体
     *
     * @param flag 提示操作的表
     * @return 所有SimpleStory实体列表
     */
    List<SimpleStory> queryAllSimpleStory(int flag);
}
