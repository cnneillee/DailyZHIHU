package com.neil.dailyzhihu.bean.cleanlayer;

import java.util.List;

public class CleanBeforeStoryListBean extends CleanStoryListBean {
    private String date;
    private List<SimpleStory> simpleStoryList;

    public String getDate() {
        return date;
    }

    public List<SimpleStory> getSimpleStoryList() {
        return simpleStoryList;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSimpleStoryList(List<SimpleStory> simpleStoryList) {
        this.simpleStoryList = simpleStoryList;
    }
}
