package com.neil.dailyzhihu.bean.cleanlayer;

import java.util.List;

/**
 * Created by Neil on 2016/3/23.
 */
public class CleanLatestStoryListBean extends CleanStoryListBean {
    private String date;
    private List<SimpleStory> simpleStoryList;
    private List<TopStory> topStoryList;

    public String getDate() {
        return date;
    }

    public List<SimpleStory> getSimpleStoryList() {
        return simpleStoryList;
    }

    public List<TopStory> getTopStoryList() {
        return topStoryList;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSimpleStoryList(List<SimpleStory> simpleStoryList) {
        this.simpleStoryList = simpleStoryList;
    }

    public void setTopStoryList(List<TopStory> topStoryList) {
        this.topStoryList = topStoryList;
    }
}
