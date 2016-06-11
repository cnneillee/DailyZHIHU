package com.neil.dailyzhihu.bean.listener;

import com.neil.dailyzhihu.bean.cleanlayer.SimpleStory;

/**
 * Created by Neil on 2016/5/7.
 */
public interface OnContentLoadingListener {
    void onStarted();

    void onFinished(SimpleStory simpleStory);

}
