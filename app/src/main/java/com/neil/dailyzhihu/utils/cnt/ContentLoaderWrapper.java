package com.neil.dailyzhihu.utils.cnt;

import com.neil.dailyzhihu.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.utils.LoaderWrapper;

/**
 * Created by Neil on 2016/4/15.
 */
public interface ContentLoaderWrapper extends LoaderWrapper {

    /**
     * 显示文本内容
     *
     * @param contentUrl 文本内容的Url
     */
    public void loadContent(String contentUrl);

    /**
     * 显示文本内容
     *
     * @param contentUrl 文本内容的Url
     * @param listener   监听事件
     */
    public void loadContent(String contentUrl, OnContentLoadingFinishedListener listener);
}
