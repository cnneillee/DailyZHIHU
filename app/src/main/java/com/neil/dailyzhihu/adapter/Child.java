package com.neil.dailyzhihu.adapter;

/**
 * Created by Neil on 2016/4/21.
 */
public class Child {
    String title;
    String imgUrl;
    int storyId;

    public Child(String title, String imgUrl, int storyId) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.storyId = storyId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getStoryId() {
        return storyId;
    }

    public String getTitle() {
        return title;
    }
}
