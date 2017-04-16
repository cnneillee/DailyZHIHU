package com.neil.dailyzhihu.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 作者：Neil on 2017/1/18 10:23.
 * 邮箱：cn.neillee@gmail.com
 */

/**
 * 主页上用于适配几个Tab移动，切换Fragment的adapter
 */
public class MainPageFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;
    private String[] mTitleArray;

    public MainPageFragmentPagerAdapter(FragmentManager fm, List<Fragment> listFragment, String[] titleArray) {
        super(fm);
        this.mFragmentList = listFragment;
        this.mTitleArray = titleArray;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList != null ? mFragmentList.size() : 0;
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleArray[position % mTitleArray.length];
    }
}
