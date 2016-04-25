package com.neil.dailyzhihu.utils.db.catalog;

/**
 * Created by Neil on 2016/4/25.
 */
public class StoryCatalog {
    private String storyId;
    private String title;
    private String imageUrl;
    private String imagePath;
    private String type;
    private String gaPrefix;
    private String downloadedTimestamp;

    public StoryCatalog() {
    }

    public StoryCatalog(String storyId, String title, String imageUrl, String imagePath, String type, String gaPrefix, String downloadedTimestamp) {
        this.storyId = storyId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.imagePath = imagePath;
        this.type = type;
        this.gaPrefix = gaPrefix;
        this.downloadedTimestamp = downloadedTimestamp;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGaPrefix() {
        return gaPrefix;
    }

    public void setGaPrefix(String gaPrefix) {
        this.gaPrefix = gaPrefix;
    }

    public String getDownloadedTimestamp() {
        return downloadedTimestamp;
    }

    public void setDownloadedTimestamp(String downloadedTimestamp) {
        this.downloadedTimestamp = downloadedTimestamp;
    }
}
