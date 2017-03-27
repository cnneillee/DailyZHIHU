package com.neil.dailyzhihu.utils.cnt;

import com.neil.dailyzhihu.listener.OnContentLoadedListener;
import com.neil.dailyzhihu.utils.load.LoaderWrapper;

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
    void loadContent(String contentUrl, OnContentLoadedListener listener);

//    /**
//     * 显示文本内容
//     *
//     * @param contentUrl 文本内容的Url
//     * @param listener   监听事件
//     */
//    <T>void loadContent(String contentUrl, OnContentLoadedListener listener, OnParseListener<T> listener1);

}
