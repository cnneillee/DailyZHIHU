package com.neil.dailyzhihu.bean.cleanlayer;

import java.util.List;

public class CleanHotStoryListBean extends CleanStoryListBean {
    private String date;
    private List<SimpleStory> simpleStoryList;

    public CleanHotStoryListBean() {

    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSimpleStoryList(List<SimpleStory> simpleStoryList) {
        this.simpleStoryList = simpleStoryList;
    }

    public String getDate() {
        return date;
    }

    public List<SimpleStory> getSimpleStoryList() {
        return simpleStoryList;
    }
}
