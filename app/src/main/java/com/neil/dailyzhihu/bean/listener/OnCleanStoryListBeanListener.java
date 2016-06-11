package com.neil.dailyzhihu.bean.listener;

import com.neil.dailyzhihu.bean.cleanlayer.CleanStoryListBean;

public interface OnCleanStoryListBeanListener {
    void onStart();

    void onContentLoaded(String content);

    void onDBdealed();

    <T extends CleanStoryListBean> void onFinish(T t);

    void onError(String errorMessage);
}
