package cn.neillee.dailyzhijiu.di.component;

import android.app.Activity;

import cn.neillee.dailyzhijiu.di.module.ActivityModule;
import cn.neillee.dailyzhijiu.di.scope.ActivityScope;
import cn.neillee.dailyzhijiu.ui.aty.ImageSplashActivity;
import cn.neillee.dailyzhijiu.ui.column.CertainColumnActivity;
import cn.neillee.dailyzhijiu.ui.column.NavColumnsActivity;
import cn.neillee.dailyzhijiu.ui.star.StoryStaredActivity;
import cn.neillee.dailyzhijiu.ui.story.StoryDetailActivity;
import cn.neillee.dailyzhijiu.ui.topic.NavTopicsActivity;
import cn.neillee.dailyzhijiu.ui.topic.TopicDetailActivity;

import dagger.Component;

/**
 * 作者：Neil on 2017/4/7 15:44.
 * 邮箱：cn.neillee@gmail.com
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();

    void inject(ImageSplashActivity imageSplashActivity);

    void inject(CertainColumnActivity certainColumnActivity);

    void inject(NavColumnsActivity navColumnsActivity);

    void inject(TopicDetailActivity topicDetailActivity);

    void inject(NavTopicsActivity navTopicsActivity);

    void inject(StoryDetailActivity storyDetailActivity);

    void inject(StoryStaredActivity storyStaredActivity);
}
