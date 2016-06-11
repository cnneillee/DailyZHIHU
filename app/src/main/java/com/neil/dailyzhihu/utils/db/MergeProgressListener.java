package com.neil.dailyzhihu.utils.db;

/**
 * Created by Neil on 2016/5/7.
 */
public interface MergeProgressListener {
    void onStarted();

    void onProgressing(String str);

    void onCompleted();
}
