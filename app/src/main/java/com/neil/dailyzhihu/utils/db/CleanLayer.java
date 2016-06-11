package com.neil.dailyzhihu.utils.db;

import android.content.Context;

import com.neil.dailyzhihu.bean.cleanlayer.CleanLatestStoryListBean;
import com.neil.dailyzhihu.bean.cleanlayer.SimpleStory;
import com.neil.dailyzhihu.bean.listener.OnCleanStoryListBeanListener;
import com.neil.dailyzhihu.utils.db.catalog.a.DBFactory;

import java.util.List;

/**
 * Created by Neil on 2016/5/7.
 */
public class CleanLayer {
    public static void getCleanLatestListBean(Context context, OnCleanStoryListBeanListener listener) {
        CleanLatestStoryListBean cleanLatestStoryListBean = new CleanLatestStoryListBean();
        List<SimpleStory> simpleStoryList = DBFactory.getsIDBSpecialSimpleStoryTabledao(context).queryAllSimpleStory(0);
        cleanLatestStoryListBean.setSimpleStoryList(simpleStoryList);
        if (simpleStoryList != null && simpleStoryList.size() > 0)
            cleanLatestStoryListBean.setDate(simpleStoryList.get(0).getDate());
        listener.onFinish(cleanLatestStoryListBean);
    }
}
