package com.neil.dailyzhihu.bean.cleanlayer;

/**
 * Created by Neil on 2016/4/27.
 */
public class CleanSectionAndThemeBean {
    private String thumbnail;
    private int sectionId;
    private String name;
    private String description;

    public CleanSectionAndThemeBean() {
    }

    public CleanSectionAndThemeBean(String thumbnail, int sectionId, String name, String description) {
        this.thumbnail = thumbnail;
        this.sectionId = sectionId;
        this.name = name;
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
