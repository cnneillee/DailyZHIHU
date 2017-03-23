package com.neil.dailyzhihu.bean;

/**
 * 作者：Neil on 2016/4/27 15:10.
 * 邮箱：cn.neillee@gmail.com
 */
public class ShareRecord {
    private int storyId;
    private long currentMillies;
    private String platform;
    private String methodType;
    private String date;

    public ShareRecord() {
    }

    public ShareRecord(int storyId, long currentMillies, String platform, String methodType, String date) {
        this.storyId = storyId;
        this.currentMillies = currentMillies;
        this.platform = platform;
        this.methodType = methodType;
        this.date = date;
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public long getCurrentMillies() {
        return currentMillies;
    }

    public void setCurrentMillies(long currentMillies) {
        this.currentMillies = currentMillies;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
