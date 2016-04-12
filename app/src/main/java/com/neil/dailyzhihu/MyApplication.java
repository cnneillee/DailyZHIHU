package com.neil.dailyzhihu;

import android.app.Application;

import com.neil.dailyzhihu.utils.ImageLoader;
import com.neil.dailyzhihu.utils.UniversalLoader;

/**
 * Created by Neil on 2016/4/12.
 */
public class MyApplication extends Application {

    private UniversalLoader universalLoader = null;
    private boolean isInit = false;

    @Override
    public void onCreate() {
        super.onCreate();
        if (!isInit) {
            universalLoader = new UniversalLoader();
            ImageLoader imageLoader = new ImageLoader();
            universalLoader.initLoader(imageLoader);
            isInit = true;
        }
    }

    public UniversalLoader getUniversalLoader() {
        if (universalLoader == null) {
            universalLoader = new UniversalLoader();
            ImageLoader imageLoader = new ImageLoader();
            universalLoader.initLoader(imageLoader);
        }
        return universalLoader;
    }
}
