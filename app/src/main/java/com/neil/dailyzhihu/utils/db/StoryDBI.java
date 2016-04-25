package com.neil.dailyzhihu.utils.db;

import java.util.List;

/**
 * Created by Neil on 2016/4/25.
 */
public interface StoryDBI {
    long addStory(Story story);

    List<Story> queryAllStory();

    Story queryStoryById(String storyId);

    Story queryStoryByGaPrefix(String gaPrefix);

    List<Story> queryStoryByType(String type);

    int updateStoryOfImgPath(String storyId, String imagePath);

    int deleteStoryById(String storyId);
}
