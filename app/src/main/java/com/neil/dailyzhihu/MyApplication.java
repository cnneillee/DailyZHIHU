package com.neil.dailyzhihu;

import android.app.Application;

import com.neil.dailyzhihu.utils.img.UniversalAndroidImageLoader;

/**
 * Created by Neil on 2016/4/15.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UniversalAndroidImageLoader.init(getApplicationContext());
    }
}
