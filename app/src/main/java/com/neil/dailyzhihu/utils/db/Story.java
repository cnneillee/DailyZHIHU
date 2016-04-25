package com.neil.dailyzhihu.utils.db;

/**
 * Created by Neil on 2016/4/25.
 */
public class Story {
    private String storyId;
    private String title;
    private String shareUrl;
    private String imgUrl;
    private String imgPath;
    private String imgsrc;
    private String body;
    private String type;
    private String gaPrefix;
    private String downloadedTimestamp;

    private String editedTimestamp;
    private String desc;
    private String author;
    private String staredTimestamp;

    public String getStoryId() {
        return storyId;
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

    public String getImgPath() {
        return imgPath;
    }

    public String getImgSrc() {
        return imgsrc;
    }

    public String getBody() {
        return body;
    }

    public String getType() {
        return type;
    }

    public String getGaPrefix() {
        return gaPrefix;
    }

    public String getDownloadedTimestamp() {
        return downloadedTimestamp;
    }

    public String getEditedTimestamp() {
        return editedTimestamp;
    }

    public String getDesc() {
        return desc;
    }

    public String getAuthor() {
        return author;
    }

    public String getStaredTimestamp() {
        return staredTimestamp;
    }

    public Story() {
    }

    public Story(String storyId, String title, String shareUrl, String imgUrl, String imgPath, String imgsrc, String body, String type, String gaPrefix, String downloadedTimestamp) {
        this.storyId = storyId;
        this.title = title;
        this.shareUrl = shareUrl;
        this.imgUrl = imgUrl;
        this.imgPath = imgPath;
        this.imgsrc = imgsrc;
        this.body = body;
        this.type = type;
        this.gaPrefix = gaPrefix;
        this.downloadedTimestamp = downloadedTimestamp;
    }

    public Story(String storyId, String title, String shareUrl, String imgUrl, String imgPath, String imgsrc, String body, String type, String gaPrefix, String downloadedTimestamp, String editedTimestamp, String desc, String author, String staredTimestamp) {
        this.storyId = storyId;
        this.title = title;
        this.shareUrl = shareUrl;
        this.imgUrl = imgUrl;
        this.imgPath = imgPath;
        this.imgsrc = imgsrc;
        this.body = body;
        this.type = type;
        this.gaPrefix = gaPrefix;
        this.downloadedTimestamp = downloadedTimestamp;
        this.editedTimestamp = editedTimestamp;
        this.desc = desc;
        this.author = author;
        this.staredTimestamp = staredTimestamp;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setGaPrefix(String gaPrefix) {
        this.gaPrefix = gaPrefix;
    }

    public void setDownloadedTimestamp(String downloadedTimestamp) {
        this.downloadedTimestamp = downloadedTimestamp;
    }

    public void setEditedTimestamp(String editedTimestamp) {
        this.editedTimestamp = editedTimestamp;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setStaredTimestamp(String staredTimestamp) {
        this.staredTimestamp = staredTimestamp;
    }
}
