package com.neil.dailyzhihu.utils;

import android.content.Context;
import android.util.Log;

import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.bean.CleanDataHelper;
import com.neil.dailyzhihu.bean.cleanlayer.CleanDetailStory;
import com.neil.dailyzhihu.bean.cleanlayer.SimpleStory;
import com.neil.dailyzhihu.bean.orignallayer.BeforeStoryListBean;
import com.neil.dailyzhihu.bean.orignallayer.DetailStory;
import com.neil.dailyzhihu.utils.db.catalog.a.DBFactory;

import java.util.Date;
import java.util.List;

/**
 * Created by Neil on 2016/5/6.
 */
public class Downloader {

    public static void downloadCertainDay(Date date, final Context context) {
//        validateDate();
        String yyyyMMDD = DateUtil.millies2yyyyMMDD(date.getTime());
        Log.e("SUCCESS", "------" + yyyyMMDD + "---" + date+ "---" + date.getTime());
        LoaderFactory.getContentLoader().loadContent(Formater.formatUrl(Constant.BEFORE_NEWS_HEADER, yyyyMMDD), new OnContentLoadingFinishedListener() {
            @Override
            public void onFinish(String content) {
                BeforeStoryListBean storiesBean = (BeforeStoryListBean) GsonDecoder.getDecoder().decoding(content, BeforeStoryListBean.class);
                if (storiesBean == null) return;
                Log.e("SUCCESS", "storiesBean!=NULL");
                List<BeforeStoryListBean.StoriesBean> storiesBeanList = storiesBean.getStories();
                String dateStr = storiesBean.getDate();
                for (int i = 0; i < storiesBeanList.size(); i++) {
                    SimpleStory simpleStory = CleanDataHelper.convertRecentBean2SimpleStory(storiesBeanList.get(i));
                    if (simpleStory == null) continue;
                    simpleStory.setDate(dateStr);
                    simpleStory.setDownloadTimeStamp(System.currentTimeMillis() + "");
                    DBFactory.getsIDBSpecialSimpleStoryTabledao(context).addSimpleStory(simpleStory, 2);
                    downloadStoryDetailNDWirteDB(simpleStory.getStoryId(), context);
                }
            }
        });
    }

    public static void downloadStoryDetailNDWirteDB(int storyId, final Context context) {
        LoaderFactory.getContentLoader().loadContent(Formater.formatUrl(Constant.STORY_HEAD, storyId), new OnContentLoadingFinishedListener() {
            @Override
            public void onFinish(String content) {
                DetailStory storyContent = (DetailStory) GsonDecoder.getDecoder().decoding(content, DetailStory.class);
                CleanDetailStory cleanDetailStory = CleanDataHelper.convertDetailStory2CleanDetailStory(storyContent);
                DBFactory.getIDBDetailStoryTabledao(context).addDetailStory(cleanDetailStory);
                Log.e("SUCCESS", "DOWNLOADED");
            }
        });
    }
}
