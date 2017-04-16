package com.neil.dailyzhihu.model.http;

import android.util.ArrayMap;

import com.neil.dailyzhihu.model.bean.orignal.CertainStoryBean;
import com.neil.dailyzhihu.model.bean.orignal.ColumnListBean;
import com.neil.dailyzhihu.model.bean.orignal.ColumnStoryListBean;
import com.neil.dailyzhihu.model.bean.orignal.CommentListBean;
import com.neil.dailyzhihu.model.bean.orignal.GankSplashBean;
import com.neil.dailyzhihu.model.bean.orignal.HotStoryListBean;
import com.neil.dailyzhihu.model.bean.orignal.HuaBanSplashBean;
import com.neil.dailyzhihu.model.bean.orignal.LatestStoryListBean;
import com.neil.dailyzhihu.model.bean.orignal.PastStoryListBean;
import com.neil.dailyzhihu.model.bean.orignal.StoryExtraInfoBean;
import com.neil.dailyzhihu.model.bean.orignal.TopicListBean;
import com.neil.dailyzhihu.model.bean.orignal.TopicStoryListBean;
import com.neil.dailyzhihu.model.bean.orignal.ZhihuSplashBean;
import com.neil.dailyzhihu.model.http.api.DailyService;
import com.neil.dailyzhihu.model.http.api.SplashService;

import retrofit2.Call;

/**
 * 作者：Neil on 2017/4/5 14:47.
 * 邮箱：cn.neillee@gmail.com
 * 对外暴露一致的接口
 */

public class RetrofitHelper {
    private DailyService mDailyService;
    private SplashService mSplashService;

    public RetrofitHelper(DailyService dailyService, SplashService splashService) {
        this.mDailyService = dailyService;
        this.mSplashService = splashService;
    }

    public Call<LatestStoryListBean> fetchLatestNewsList() {
        return mDailyService.getLatestNewsList();
    }

    public Call<HotStoryListBean> fetchHotNewsList() {
        return mDailyService.getHotNewsList();
    }

    public Call<PastStoryListBean> fetchPastNewsList(String date) {
        return mDailyService.getPastNewsList(date);
    }

    public Call<TopicListBean> fetchTopicList() {
        return mDailyService.getTopicList();
    }

    public Call<ColumnListBean> fetchColumnList() {
        return mDailyService.getColumnList();
    }

    public Call<TopicStoryListBean> fetchTopicNewsList(int topicId) {
        return mDailyService.getTopicNewsList(topicId);
    }

    public Call<ColumnStoryListBean> fetchColumnNewsList(int columnId) {
        return mDailyService.getColumnNewsList(columnId);
    }

    public Call<CertainStoryBean> fetchNewsDetail(int storyId) {
        return mDailyService.getNewsDetail(storyId);
    }

    public Call<CommentListBean> fetchNewsComment(int storyId, String commentType) {
        return mDailyService.getCommentList(storyId, commentType);
    }

    public Call<StoryExtraInfoBean> fetchNewsExtraInfo(int storyId) {
        return mDailyService.getStoryExtraInfo(storyId);
    }

    public Call<String> fetchEditorProfileInfo(int editorId) {
        return mDailyService.getEditorProfileInfo(editorId);
    }

    public Call<GankSplashBean> fetchGankImageSplash(String category, int count, int no) {
        return mSplashService.getGankSplash(category, count, no);
    }

    public Call<HuaBanSplashBean> fetchHuaBanImageSplash(int boardId, ArrayMap<String, String> queryMap) {
        return mSplashService.getHuabanSplash(boardId, queryMap);
    }

    public Call<ZhihuSplashBean> fetchZhihuImageSplash(ArrayMap<String, String> headersMap, ArrayMap<String, String> paramsMap) {
        return mSplashService.getZhihuSplash(headersMap, paramsMap);
    }
}