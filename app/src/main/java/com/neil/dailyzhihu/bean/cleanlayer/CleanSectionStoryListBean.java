package com.neil.dailyzhihu.bean.cleanlayer;

import java.util.List;

/**
 * Created by Neil on 2016/4/25.
 */
public class CleanSectionStoryListBean extends CleanStoryListBean {
    private int timestamp;
    private String name;
    private List<SectionStory> sectionStoryList;

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SectionStory> getSectionStoryList() {
        return sectionStoryList;
    }

    public void setSectionStoryList(List<SectionStory> sectionStoryList) {
        this.sectionStoryList = sectionStoryList;
    }
}
