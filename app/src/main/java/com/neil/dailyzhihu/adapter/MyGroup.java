package com.neil.dailyzhihu.adapter;

import com.neil.dailyzhihu.utils.db.FavoriteStory;

import java.util.List;

/**
 * Created by Neil on 2016/4/21.
 */
public class MyGroup {
    String name;

    public String getName() {
        return name;
    }

    List<FavoriteStory> mChildList;

    public MyGroup(String name, List<FavoriteStory> childList) {
        this.name = name;
        mChildList = childList;
    }

    public List<FavoriteStory> getChildList() {
        return mChildList;
    }
}


