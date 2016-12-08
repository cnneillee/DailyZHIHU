package com.neil.dailyzhihu.utils.cnt;

import android.content.Context;

import com.neil.dailyzhihu.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.utils.LoaderWrapper;

/**
 * 内容加载器的包装
 */
public interface ContentLoaderWrapper extends LoaderWrapper {

    /**
     * 显示文本内容
     *
     * @param contentUrl 文本内容的Url
     */
     void loadContent(String contentUrl);

    /**
     * 显示文本内容
     *
     * @param contentUrl 文本内容的Url
     * @param listener   监听事件
     */
    void loadContent(String contentUrl, OnContentLoadingFinishedListener listener);

    void loadContent(Context context, String contentUrl, OnContentLoadingFinishedListener listener);
}
