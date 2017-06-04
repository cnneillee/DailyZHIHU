package cn.neillee.dailyzhijiu.utils.cnt;

import cn.neillee.dailyzhijiu.listener.OnContentLoadListener;
import cn.neillee.dailyzhijiu.utils.load.LoaderWrapper;

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
    void loadContent(String contentUrl, OnContentLoadListener listener);

//    /**
//     * 显示文本内容
//     *
//     * @param contentUrl 文本内容的Url
//     * @param listener   监听事件
//     */
//    <T>void loadContent(String contentUrl, OnContentLoadListener listener, OnParseListener<T> listener1);

}
