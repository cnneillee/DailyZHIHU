package com.neil.dailyzhihu.utils.db;

/**
 * Created by Neil on 2016/4/22.
 */
public class BaseSection {
    private String sectionName;
    private int sectionId;
    private String thumbnail;

    public BaseSection(String sectionName, int sectionId, String thumbnail) {
        this.sectionName = sectionName;
        this.sectionId = sectionId;
        this.thumbnail = thumbnail;
    }

    public BaseSection() {
    }

    public String getSectionName() {
        return sectionName;
    }

    public int getSectionId() {
        return sectionId;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
