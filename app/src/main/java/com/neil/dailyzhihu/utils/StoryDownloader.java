package com.neil.dailyzhihu.utils;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.bean.CleanDataHelper;
import com.neil.dailyzhihu.bean.orignallayer.StoryExtra;
import com.neil.dailyzhihu.bean.cleanlayer.CleanBeforeStoryListBean;
import com.neil.dailyzhihu.bean.cleanlayer.CleanDetailStory;
import com.neil.dailyzhihu.bean.cleanlayer.CleanHotStoryListBean;
import com.neil.dailyzhihu.bean.cleanlayer.CleanLatestStoryListBean;
import com.neil.dailyzhihu.bean.cleanlayer.SimpleStory;
import com.neil.dailyzhihu.bean.cleanlayer.TopStory;
import com.neil.dailyzhihu.bean.listener.OnCleanHotStoryListBeanListener;
import com.neil.dailyzhihu.bean.listener.OnCleanStoryListBeanListener;
import com.neil.dailyzhihu.bean.listener.OnDetailStoryLoadingFinishedListener;
import com.neil.dailyzhihu.bean.listener.OnCleanBeforeStoryListBeanListener;
import com.neil.dailyzhihu.bean.orignallayer.BeforeStoryListBean;
import com.neil.dailyzhihu.bean.orignallayer.DetailStory;
import com.neil.dailyzhihu.bean.orignallayer.HotStory;
import com.neil.dailyzhihu.bean.orignallayer.LatestStory;
import com.neil.dailyzhihu.bean.orignallayer.RecentBean;
import com.neil.dailyzhihu.utils.db.catalog.a.DBFactory;
import com.neil.dailyzhihu.utils.db.catalog.a.MyDBHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Neil on 2016/5/6.
 * 用于下载两类Story
 */
public class StoryDownloader {
    private static final String LOG_TAG = StoryDownloader.class.getSimpleName();

    public static void downloadCleanDetailStory(final int storyId, final Context context, final OnDetailStoryLoadingFinishedListener finishedListener) {
        LoaderFactory.getContentLoaderVolley().loadContent(context, Formater.formatUrl(Constant.STORY_HEAD, storyId), new OnContentLoadingFinishedListener() {
            @Override
            public void onFinish(String content) {
                DetailStory storyContent = (DetailStory) GsonDecoder.getDecoder().decoding(content, DetailStory.class);
                CleanDetailStory cleanDetailStory = CleanDataHelper.convertDetailStory2CleanDetailStory(storyContent);
                if (cleanDetailStory == null) return;
                LoaderFactory.getImageLoader().downloadImage(context, cleanDetailStory.getImage(), "", cleanDetailStory.getStoryId(), null);
                long res = DBFactory.getIDBDetailStoryTabledao(context).addDetailStory(cleanDetailStory);
                Log.e(LOG_TAG, "res：" + res + ",storyId:" + storyId + "---" + cleanDetailStory.getStoryId());
                if (finishedListener != null)
                    finishedListener.onFinish(cleanDetailStory);
            }
        });
    }

    public static void downloadBefore(Calendar cal, final Context context, final OnCleanBeforeStoryListBeanListener finishedListener) {
        String yyyyMMDD = DateUtil.calendar2yyyyMMDD(cal);
        if (TextUtils.isEmpty(yyyyMMDD)) return;
        Log.e(LOG_TAG, "downloadBefore: yyyyMMDD：" + yyyyMMDD);
        LoaderFactory.getContentLoader().loadContent(Constant.BEFORE_NEWS_HEADER + yyyyMMDD, new OnContentLoadingFinishedListener() {
            @Override
            public void onFinish(String content) {
                BeforeStoryListBean beforeStoryListBean = (BeforeStoryListBean) GsonDecoder.getDecoder().decoding(content, BeforeStoryListBean.class);
                if (beforeStoryListBean == null) return;
                List<BeforeStoryListBean.StoriesBean> storiesBeanList = beforeStoryListBean.getStories();
                String date = beforeStoryListBean.getDate();
                String downloadTimstamp = System.currentTimeMillis() + "";
                if (storiesBeanList == null) return;
                List<SimpleStory> simpleStoryList = new ArrayList<>();
                for (int i = 0; i < storiesBeanList.size(); i++) {
                    final SimpleStory simpleStory = CleanDataHelper.convertRecentBean2SimpleStory(storiesBeanList.get(i));
                    simpleStory.setDate(date);
                    simpleStory.setDownloadTimeStamp(downloadTimstamp);
                    DBFactory.getsIDBSpecialSimpleStoryTabledao(context).addSimpleStory(simpleStory, 2);
                    //下载评论
                    LoaderFactory.getContentLoaderVolley().loadContent(context, Constant.EXTRA_HEAD + simpleStory.getStoryId(), new OnContentLoadingFinishedListener() {
                        @Override
                        public void onFinish(String content) {
                            StoryExtra extra = (StoryExtra) GsonDecoder.getDecoder().decoding(content, StoryExtra.class);
                            ContentValues cv = new ContentValues();
                            cv.put(MyDBHelper.ConstantSimpleStoryDB.KEY_SIMPLE_STORY_POPULARITY, extra.getPopularity());
                            cv.put(MyDBHelper.ConstantSimpleStoryDB.KEY_SIMPLE_STORY_LONG_COMMENTS, extra.getLong_comments());
                            cv.put(MyDBHelper.ConstantSimpleStoryDB.KEY_SIMPLE_STORY_SHORT_COMMENTS, extra.getShort_comments());
                            DBFactory.getsIDBSpecialSimpleStoryTabledao(context).updateSimpleStory(simpleStory.getStoryId(), cv, 2);
                        }
                    });
                    Log.e(LOG_TAG, i + "---" + storiesBeanList.get(i).getStoryId() + " " + simpleStory.getStoryId());
                    downloadCleanDetailStory(simpleStory.getStoryId(), context, null);
                    simpleStoryList.add(simpleStory);
                }
                CleanBeforeStoryListBean cleanBeforeStoryListBean = new CleanBeforeStoryListBean();
                cleanBeforeStoryListBean.setDate(date);
                cleanBeforeStoryListBean.setSimpleStoryList(simpleStoryList);
                if (finishedListener != null) finishedListener.onFinish(cleanBeforeStoryListBean);
            }
        });
    }

    public static void downloadLatest(final Context context, final OnCleanStoryListBeanListener finishedListener) {
        LoaderFactory.getContentLoader().loadContent(Constant.URL_LATEST_NEWS, new OnContentLoadingFinishedListener() {
            @Override
            public void onFinish(String content) {
                LatestStory latestStory = (LatestStory) GsonDecoder.getDecoder().decoding(content, LatestStory.class);
                if (latestStory == null) return;
                List<LatestStory.StoriesBean> storiesBeanList = latestStory.getStories();
                List<LatestStory.TopStoriesBean> topStoriesBeanList = latestStory.getTop_stories();
                String dateStr = latestStory.getDate();
                String downloadTimstamp = System.currentTimeMillis() + "";
                if (storiesBeanList == null || topStoriesBeanList == null) return;
                List<SimpleStory> simpleStoryList = new ArrayList<SimpleStory>();
                for (int i = 0; i < storiesBeanList.size(); i++) {
                    SimpleStory simpleStory = CleanDataHelper.convertStoriesBean2SimpleStory(storiesBeanList.get(i));
                    simpleStory.setDownloadTimeStamp(downloadTimstamp);
                    simpleStoryList.add(simpleStory);
                }
                List<TopStory> topStoryList = new ArrayList<>();
                for (int i = 0; i < topStoriesBeanList.size(); i++) {
                    TopStory topStory = CleanDataHelper.convertTopStoriesBean2TopStory(topStoriesBeanList.get(i));
                    topStory.setDate(dateStr);
                    topStoryList.add(topStory);
                }
                CleanLatestStoryListBean cleanLatestStoryListBean = new CleanLatestStoryListBean();
                cleanLatestStoryListBean.setDate(dateStr);
                cleanLatestStoryListBean.setSimpleStoryList(simpleStoryList);
                cleanLatestStoryListBean.setTopStoryList(topStoryList);
                if (finishedListener != null) finishedListener.onFinish(cleanLatestStoryListBean);
            }
        });
    }

    public static void downloadHot(final Context context, final OnCleanHotStoryListBeanListener finishedListener) {

        LoaderFactory.getContentLoader().loadContent(Constant.URL_LATEST_NEWS, new OnContentLoadingFinishedListener() {
            @Override
            public void onFinish(String content) {
                HotStory hotStory = (HotStory) GsonDecoder.getDecoder().decoding(content, HotStory.class);
                if (hotStory == null) return;
                List<RecentBean> recentBeanList = hotStory.getRecent();
                if (recentBeanList == null) return;
                List<SimpleStory> simpleStoryList = new ArrayList<>();
                String downloadTimstamp = System.currentTimeMillis() + "";
                for (int i = 0; i < recentBeanList.size(); i++) {
                    SimpleStory simpleStory = CleanDataHelper.convertRecentBean2SimpleStory(recentBeanList.get(i));
                    simpleStory.setDownloadTimeStamp(downloadTimstamp);
                    simpleStoryList.add(simpleStory);
                }
                CleanHotStoryListBean cleanHotStoryListBean = new CleanHotStoryListBean();
                cleanHotStoryListBean.setSimpleStoryList(simpleStoryList);
                if (finishedListener != null) finishedListener.onFinish(cleanHotStoryListBean);
            }
        });
    }


}
