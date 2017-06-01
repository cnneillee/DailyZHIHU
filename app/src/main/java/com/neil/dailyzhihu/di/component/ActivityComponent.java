package com.neil.dailyzhihu.di.component;

import android.app.Activity;

import com.neil.dailyzhihu.di.module.ActivityModule;
import com.neil.dailyzhihu.di.scope.ActivityScope;
import com.neil.dailyzhihu.ui.aty.ImageSplashActivity;
import com.neil.dailyzhihu.ui.column.CertainColumnActivity;
import com.neil.dailyzhihu.ui.column.NavColumnsActivity;
import com.neil.dailyzhihu.ui.star.StoryStaredActivity;
import com.neil.dailyzhihu.ui.story.StoryDetailActivity;
import com.neil.dailyzhihu.ui.topic.NavTopicsActivity;
import com.neil.dailyzhihu.ui.topic.TopicDetailActivity;

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
