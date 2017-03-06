package com.neil.dailyzhihu.bean;

/**
 * Created by Neil on 2016/4/27.
 */
public class StarRecord {
    private int storyId;
    private String date;
    private long timeStamp;
    private String catalog;

    public StarRecord() {
    }

    public StarRecord(int storyId, String date, long timeStamp, String catalog) {
        this.storyId = storyId;
        this.date = date;
        this.timeStamp = timeStamp;
        this.catalog = catalog;
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }
}
