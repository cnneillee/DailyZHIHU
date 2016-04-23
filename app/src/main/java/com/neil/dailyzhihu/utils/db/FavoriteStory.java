package com.neil.dailyzhihu.utils.db;

/**
 * Created by Neil on 2016/4/22.
 */
public class FavoriteStory {

    public FavoriteStory(String storyId, String editedTimestamp, String title, String shareUrl, String imgUrl, String imgsrc, String body, String downloadedTimestamp, String staredTimestamp, String imgPath, String type, String gaPrefix, BaseSection section) {
        this.storyId = storyId;
        this.editedTimestamp = editedTimestamp;
        this.title = title;
        this.shareUrl = shareUrl;
        this.imgUrl = imgUrl;
        this.imgsrc = imgsrc;
        this.body = body;
        this.downloadedTimestamp = downloadedTimestamp;
        this.staredTimestamp = staredTimestamp;
        this.imgPath = imgPath;
        this.type = type;
        this.gaPrefix = gaPrefix;
        this.section = section;
    }

    public FavoriteStory() {
    }

    public FavoriteStory(String storyId, String editedTimestamp, String title, String shareUrl, String imgUrl, String imgsrc, String body) {
        this.storyId = storyId;
        this.editedTimestamp = editedTimestamp;
        this.title = title;
        this.shareUrl = shareUrl;
        this.imgUrl = imgUrl;
        this.imgsrc = imgsrc;
        this.body = body;
    }

    private String storyId;
    private String editedTimestamp;
    private String title;
    private String shareUrl;
    private String imgUrl;
    private String imgsrc;
    private String body;

    private String downloadedTimestamp;
    private String staredTimestamp;

    private String imgPath;
    private String type;

    private String gaPrefix;
    private BaseSection section;

    public String getStoryId() {
        return storyId;
    }

    public String getEditedTimestamp() {
        return editedTimestamp;
    }

    public String getTitle() {
        return title;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public String getBody() {
        return body;
    }

    public String getGaPrefix() {
        return gaPrefix;
    }

    public String getDownloadedTimestamp() {
        return downloadedTimestamp;
    }

    public String getStaredTimestamp() {
        return staredTimestamp;
    }

    public String getImgPath() {
        return imgPath;
    }

    public String getType() {
        return type;
    }

    public BaseSection getSection() {
        return section;
    }

    @Override
    public String toString() {
        return storyId + editedTimestamp + title + shareUrl;
    }
}
