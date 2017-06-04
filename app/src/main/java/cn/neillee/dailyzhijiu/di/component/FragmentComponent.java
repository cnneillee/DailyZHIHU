package cn.neillee.dailyzhijiu.di.component;

import android.app.Activity;

import cn.neillee.dailyzhijiu.di.module.FragmentModule;
import cn.neillee.dailyzhijiu.di.scope.FragmentScope;
import cn.neillee.dailyzhijiu.ui.main.HotFragment;
import cn.neillee.dailyzhijiu.ui.main.LatestFragment;
import cn.neillee.dailyzhijiu.ui.main.PastFragment;
import cn.neillee.dailyzhijiu.ui.story.StoryCommentFragment;

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
