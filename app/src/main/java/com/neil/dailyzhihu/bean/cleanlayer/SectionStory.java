package com.neil.dailyzhihu.bean.cleanlayer;

/**
 * Created by Neil on 2016/4/25.
 */
public class SectionStory extends SimpleStory {
    private int storyId;
    private String gaPrefix;
    private String title;
    private int type;
    private String imageUrl;
    private String imagePath;

    private String date;
    private String displayDate;

    @Override
    public int getStoryId() {
        return storyId;
    }

    @Override
    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    @Override
    public String getGaPrefix() {
        return gaPrefix;
    }

    @Override
    public void setGaPrefix(String gaPrefix) {
        this.gaPrefix = gaPrefix;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDisplayDate() {
        return displayDate;
    }

    public void setDisplayDate(String displayDate) {
        this.displayDate = displayDate;
    }
}
