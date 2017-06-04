package cn.neillee.dailyzhijiu.di.module;

import cn.neillee.dailyzhijiu.app.DailyApp;
import cn.neillee.dailyzhijiu.model.db.GreenDaoHelper;
import cn.neillee.dailyzhijiu.model.http.RetrofitHelper;
import cn.neillee.dailyzhijiu.model.http.api.DailyService;
import cn.neillee.dailyzhijiu.model.http.api.SplashService;

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

    @Provides
    @Singleton
    RetrofitHelper provideRetrofitHelper(DailyService dailyServiceService, SplashService splashService) {
        return new RetrofitHelper(dailyServiceService, splashService);
    }

    @Provides
    @Singleton
    GreenDaoHelper provideGreenDaoHelper() {
        return new GreenDaoHelper(mApp);
    }
}
