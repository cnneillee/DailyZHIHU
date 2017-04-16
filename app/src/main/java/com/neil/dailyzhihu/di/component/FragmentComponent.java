package com.neil.dailyzhihu.di.component;

import android.app.Activity;

import com.neil.dailyzhihu.di.module.FragmentModule;
import com.neil.dailyzhihu.di.scope.FragmentScope;
import com.neil.dailyzhihu.ui.main.HotFragment;
import com.neil.dailyzhihu.ui.main.LatestFragment;
import com.neil.dailyzhihu.ui.main.PastFragment;
import com.neil.dailyzhihu.ui.story.StoryCommentFragment;

import dagger.Component;

/**
 * 作者：Neil on 2017/4/7 15:29.
 * 邮箱：cn.neillee@gmail.com
 */
@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(HotFragment hotFragment);

    void inject(LatestFragment latestFragment);

    void inject(PastFragment pastFragment);

    void inject(StoryCommentFragment storyCommentFragment);
}
