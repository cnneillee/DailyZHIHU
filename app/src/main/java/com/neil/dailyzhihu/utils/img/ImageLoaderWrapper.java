package com.neil.dailyzhihu.utils.img;

import android.widget.ImageView;

import com.neil.dailyzhihu.utils.LoaderWrapper;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import java.io.File;

/**
 * 图片加载功能接口
 * <p/>
 * Created by Clock on 2016/1/18.
 */
public interface ImageLoaderWrapper extends LoaderWrapper {

    /**
     * 显示 图片
     *
     * @param imageView 显示图片的ImageView
     * @param imageFile 图片文件
     * @param option    显示参数设置
     */
    public void displayImage(ImageView imageView, File imageFile, DisplayOption option);

    /**
     * 显示图片
     *
     * @param imageView 显示图片的ImageView
     * @param imageUrl  图片资源的URL
     * @param option    显示参数设置
     */
    public void displayImage(ImageView imageView, String imageUrl, DisplayOption option);

    /**
     * 显示图片
     * @param imageView 显示图片的ImageView
     * @param imageUrl 图片资源的URL
     * @param option  显示参数设置
     * @param listener  图片加载监听事件
     */
    public void displayImage(ImageView imageView, String imageUrl, DisplayOption option, ImageLoadingListener listener);

    /**
     * 显示图片
     * @param imageView 显示图片的ImageView
     * @param imageUrl 图片资源的URL
     * @param option  显示参数设置
     * @param listener  图片加载监听事件
     * @param progressListener  图片加载进度监听事件
     */
    public void displayImage(ImageView imageView, String imageUrl, DisplayOption option, ImageLoadingListener listener, ImageLoadingProgressListener progressListener);

    /**
     * 图片加载参数
     */
    public static class DisplayOption {
        /**
         * 加载中的资源id
         */
        public int loadingResId;
        /**
         * 加载失败的资源id
         */
        public int loadErrorResId;
    }
}
