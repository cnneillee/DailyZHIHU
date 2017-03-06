package com.neil.dailyzhihu;

import android.app.Application;
import android.content.Context;

import com.neil.dailyzhihu.utils.img.UniversalAndroidImageLoader;

/**
 * 作者：Neil on 2016/4/15 23:52.
 * 邮箱：cn.neillee@gmail.com
 */
public class MyApplication extends Application {
    public static Context AppContext = null;

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext = getApplicationContext();
        UniversalAndroidImageLoader.init(getApplicationContext());
    }
}
