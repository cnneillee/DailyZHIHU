package com.neil.dailyzhihu.utils;

//android---ScrollView中嵌套ListView
//        方法一：计算Listview设置适配器后的高度，然后手动设置高度。


import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;


//这个类用来重新计算LISTVIEW的高度

public class Utility {
    private static int lvH = 0;

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        lvH = params.height;
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    public static void setListViewHeightBasedOnChildren(AbsListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;// + (listView.getDividerHeight() * (listAdapter.getCount() - 1))
        lvH = params.height;
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    public static void setGridViewHeightBasedOnChildren(GridView gv) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = gv.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        final int colCount = gv.getNumColumns();
        final int childCount = gv.getChildCount();
        final int rowCount = childCount / colCount + (childCount % colCount == 0 ? 0 : 1);
        int rowHight = 0;
        for (int i = 0; i < rowCount; i++) {//每行的最大高度
            for (int j = 0; j < colCount; j++) {
                View listItem = gv.getChildAt(i * rowCount + j);
                listItem.measure(0, 0); // 计算子项View 的宽高
                rowHight = rowHight > listItem.getMeasuredHeight() ? rowHight : listItem.getMeasuredHeight();
            }
            totalHeight += rowHight; // 统计所有子项的总高度
            rowHight = 0;
        }
        ViewGroup.LayoutParams params = gv.getLayoutParams();
        params.height = totalHeight;// + (listView.getDividerHeight() * (listAdapter.getCount() - 1))
        lvH = params.height;
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        gv.setLayoutParams(params);
    }

    //
    public static void setViewPagerHeight(ViewPager viewPager) {
        PagerAdapter adapter = viewPager.getAdapter();
        if (viewPager.getChildCount() <= 0) {
            return;
        }
        //init
        int minHight = viewPager.getChildAt(0).getMeasuredHeight();
        for (int i = 0; i < viewPager.getChildCount(); i++) {
            if (minHight > viewPager.getChildAt(i).getMeasuredHeight())
                minHight = viewPager.getChildAt(i).getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = viewPager.getLayoutParams();
        params.height = minHight;
        viewPager.setLayoutParams(params);
    }
//
//    public static void setScrollView(ScrollView sv, ListView lv) {
//        if (sv == null) {
//            // pre-condition
//            return;
//        }
//        ViewGroup.LayoutParams params = sv.getLayoutParams();
//        ViewGroup.LayoutParams lvparams = lv.getLayoutParams();
//        Log.e("Log_tag", params.height + "--" + lvH);
//        params.height = params.height - lvH;
//        params.height = -1;
//        sv.setLayoutParams(params);
//    }
}
//
//    正常使用ListView  然后在设置完适配器后使用以下语句。
//
//        Utility.setListViewHeightBasedOnChildren(listView);
//
//        listview中的item的布局必须最外层是线性布局才行。