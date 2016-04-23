package com.neil.dailyzhihu.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Neil on 2016/4/21.
 */
public class CommentPagerAdapter extends PagerAdapter {
    List<View> listItem;

    public CommentPagerAdapter(List<View> listItem) {
        this.listItem = listItem;
    }

    //viewpager中的组件数量
    @Override
    public int getCount() {
        return listItem.size();
    }

    //滑动切换的时候销毁当前的组件
    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        container.removeView(listItem.get(position));
    }

    //每次滑动的时候生成的组件
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = listItem.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "标题" + position;
    }

}
