package com.neil.dailyzhihu.adapter;

import java.util.List;

/**
 * Created by Neil on 2016/4/21.
 */
public class MyGroup {
    String name;

    public String getName() {
        return name;
    }

    List<Child> mChildList;

    public MyGroup(String name, List<Child> childList) {
        this.name = name;
        mChildList = childList;
    }

    public List<Child> getChildList() {
        return mChildList;
    }
}


