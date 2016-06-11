package com.neil.dailyzhihu.bean.listener;

import com.neil.dailyzhihu.bean.StoryExtra;
import com.neil.dailyzhihu.bean.cleanlayer.CleanStoryExtra;
import com.neil.dailyzhihu.bean.cleanlayer.CleanStoryListBean;

/**
 * Created by Neil on 2016/5/8.
 */
public interface OnStoryExtraLoadingListener {
    void onStart();

    void onContentLoaded(String content);

    void onDBdealed();

    void onFinish(CleanStoryExtra storyExtra);

    void onError(String errorMessage);
}
