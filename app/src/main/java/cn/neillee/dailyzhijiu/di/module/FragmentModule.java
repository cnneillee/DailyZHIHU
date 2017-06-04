package cn.neillee.dailyzhijiu.di.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import cn.neillee.dailyzhijiu.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * 作者：Neil on 2017/4/7 15:31.
 * 邮箱：cn.neillee@gmail.com
 */
@Module
public class FragmentModule {
    private final Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @FragmentScope
    @Provides
    public Activity provideActivity() {
        return mFragment.getActivity();
    }
}
