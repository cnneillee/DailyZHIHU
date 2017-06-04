package cn.neillee.dailyzhijiu.di.component;

import cn.neillee.dailyzhijiu.app.DailyApp;
import cn.neillee.dailyzhijiu.di.module.AppModule;
import cn.neillee.dailyzhijiu.di.module.HttpModule;
import cn.neillee.dailyzhijiu.model.db.GreenDaoHelper;
import cn.neillee.dailyzhijiu.model.http.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * 作者：Neil on 2017/4/7 16:39.
 * 邮箱：cn.neillee@gmail.com
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {
    DailyApp getContext();

    RetrofitHelper retrofitHelper();

    GreenDaoHelper greenDaoHelper();
}
