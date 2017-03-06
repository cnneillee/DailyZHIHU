package com.neil.dailyzhihu.bean.orignallayer;

import com.neil.dailyzhihu.bean.UniversalStoryBean;

import java.util.ArrayList;
import java.util.List;

public class RecentBean extends OrignalStory implements UniversalStoryBean {
    private int news_id;
    /**
     * URL还失效了
     */
    private String url;
    private String thumbnail;
    private String title;

    public RecentBean(int news_id, String url, String thumbnail, String title) {
        this.news_id = news_id;
        this.url = url;
        this.thumbnail = thumbnail;
        this.title = title;
    }

    @Override
    public int getStoryId() {
        return news_id;
    }

    @Override
    public List<String> getImages() {
        List<String> images = new ArrayList<>();
        images.add(thumbnail);
        return images;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}