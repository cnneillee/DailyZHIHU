package com.neil.dailyzhihu.bean.cleanlayer;

import java.util.List;

/**
 * Created by Neil on 2016/4/25.
 */
public class CleanStoryListBean {
    private String date;
    private List<SimpleStory> simpleStoryList;

    public String getDate() {
        return date;
    }

    public List<SimpleStory> getSimpleStoryList() {
        return simpleStoryList;
    }
}
