package com.neil.dailyzhihu.utils.db;

import android.content.Context;
import android.widget.Toast;

import com.neil.dailyzhihu.bean.StoryContent;
import com.neil.dailyzhihu.utils.db.FavoriteStoryDBdao;

/**
 * Created by Neil on 2016/4/23.
 */
public class FavoriteStoryDBdaoFactory {
    private static FavoriteStoryDBdao sInstanceImg;

    public static FavoriteStoryDBdao getInstance(Context context) {
        if (sInstanceImg == null) {
            synchronized (FavoriteStoryDBdao.class) {
                if (sInstanceImg == null) {
                    sInstanceImg = new FavoriteStoryDBdao(context);
                }
            }
        }
        return sInstanceImg;
    }

    public static FavoriteStory convertStoryContent2DBStory(StoryContent story) {
        if (story == null) {
            return null;
        }
        String shareUrl = story.getShare_url();
        String body = story.getBody();
        String title = story.getTitle();
        String gaPrefix = story.getGa_prefix();
        String storyId = story.getId() + "";
        String imageUrl = story.getImage();
        String imageSource = story.getImage_source();
        String type = story.getType() + "";
        String currentMillies = System.currentTimeMillis() + "";
        FavoriteStory favoriteStory = new FavoriteStory(storyId, null, title, shareUrl, imageUrl, imageSource, body, null, currentMillies, null, type, gaPrefix, null);
        return favoriteStory;
    }
}
