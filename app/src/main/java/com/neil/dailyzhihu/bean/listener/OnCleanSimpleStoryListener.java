package com.neil.dailyzhihu.bean.listener;

import com.neil.dailyzhihu.bean.cleanlayer.CleanLatestStoryListBean;

/**
 * Created by Neil on 2016/5/7.
 */
public interface OnCleanSimpleStoryListener {
    void onStart();

    void onContentLoaded(String content);

    void onDBdealed();

    void onFinish(CleanLatestStoryListBean cleanLatestStoryListBean);

    void onError(String errorMessage);
}
