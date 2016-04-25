package com.neil.dailyzhihu.utils.db.catalog;

import com.neil.dailyzhihu.utils.db.FavoriteStory;

import java.util.List;

/**
 * Created by Neil on 2016/4/25.
 */
public interface ILHDB {
    long addStoryCatalog(StoryCatalog story);

    int dropStoryCatalog(int storyId);

    int updateStoryCatalog(int storyId, StoryCatalog newStoryCatalog);

    List<StoryCatalog> queryStoryCatalogById(int storyId);

    List<StoryCatalog> queryStoryCatalogByDownloadedDate(String storyCreatedDate);

    public List<StoryCatalog> queryAllStoryCatalog();
}
