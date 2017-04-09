package com.neil.dailyzhihu.di.module;

import com.neil.dailyzhihu.DailyApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 作者：Neil on 2017/4/7 16:40.
 * 邮箱：cn.neillee@gmail.com
 */
@Module
public class AppModule {
    private final DailyApp mApp;

    public AppModule(DailyApp app) {
        mApp = app;
    }

    @Provides
    @Singleton
    DailyApp provideAppContext() {
        return mApp;
    }

//    @Provides
//    @Singleton
//    RetrofitHelper provideRetrofitHelper(DailyApi dailyApiService) {
//        return new RetrofitHelper(dailyApiService);
//    }
}
