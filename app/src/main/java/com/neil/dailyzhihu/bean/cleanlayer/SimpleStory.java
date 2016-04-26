package com.neil.dailyzhihu.bean.cleanlayer;

public class SimpleStory {
    private int storyId;
    private String gaPrefix;
    private String title;
    private int type;
    private String imageUrl;
    private String imagePath;
    private String date;
    private String downloadTimeStamp;

    public SimpleStory() {
    }

    public SimpleStory(int storyId, String gaPrefix, String title, int type, String imageUrl, String imagePath, String date, String downloadTimeStamp) {
        this.storyId = storyId;
        this.gaPrefix = gaPrefix;
        this.title = title;
        this.type = type;
        this.imageUrl = imageUrl;
        this.imagePath = imagePath;
        this.date = date;
        this.downloadTimeStamp = downloadTimeStamp;
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public String getGaPrefix() {
        return gaPrefix;
    }

    public void setGaPrefix(String gaPrefix) {
        this.gaPrefix = gaPrefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDownloadTimeStamp() {
        return downloadTimeStamp;
    }

    public void setDownloadTimeStamp(String downloadTimeStamp) {
        this.downloadTimeStamp = downloadTimeStamp;
    }
}

