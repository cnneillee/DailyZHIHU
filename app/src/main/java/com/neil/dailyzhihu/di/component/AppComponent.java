package com.neil.dailyzhihu.di.component;

import com.neil.dailyzhihu.app.DailyApp;
import com.neil.dailyzhihu.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * 作者：Neil on 2017/4/7 16:39.
 * 邮箱：cn.neillee@gmail.com
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    DailyApp getContext();

//    RetrofitHelper retrofitHelper();
}
