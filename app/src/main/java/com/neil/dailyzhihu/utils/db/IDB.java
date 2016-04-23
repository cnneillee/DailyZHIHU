package com.neil.dailyzhihu.utils.db;

import java.util.List;

/**
 * Created by Neil on 2016/4/22.
 */
public interface IDB {
    long addStory(FavoriteStory story);

    void dropStory(int storyId);

    void updateStory(int storyId, FavoriteStory newStory);

    List<FavoriteStory> queryStoryById(int storyId);

    List<FavoriteStory> queryStoryByStaredDate(long storyStaredDate);

    List<FavoriteStory> queryStoryByCreatedDate(long storyCreatedDate);
}
