package com.neil.dailyzhihu.utils;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by Neil on 2016/4/12.
 */
public interface IwrappedLoader {
    void loadImage(Context context, ImageView iv, String imgUrl,
                   ImageLoader.OnFinishListener onFinishListener);
    void loadImage(ImageView iv, String imgUrl,
                   ImageLoader.OnFinishListener onFinishListener);
}
