package com.neil.dailyzhihu.model.http.api;

import com.neil.dailyzhihu.model.bean.orignal.CertainStoryBean;
import com.neil.dailyzhihu.model.bean.orignal.ColumnListBean;
import com.neil.dailyzhihu.model.bean.orignal.ColumnStoryListBean;
import com.neil.dailyzhihu.model.bean.orignal.CommentListBean;
import com.neil.dailyzhihu.model.bean.orignal.HotStoryListBean;
import com.neil.dailyzhihu.model.bean.orignal.LatestStoryListBean;
import com.neil.dailyzhihu.model.bean.orignal.PastStoryListBean;
import com.neil.dailyzhihu.model.bean.orignal.TopicListBean;
import com.neil.dailyzhihu.model.bean.orignal.TopicStoryListBean;
import com.neil.dailyzhihu.model.bean.orignal.UpdateInfoBean;

import io.reactivex.Flowable;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * 作者：Neil on 2017/4/5 14:19.
 * 邮箱：cn.neillee@gmail.com
 * 知乎日报API（https://github.com/izzyleung/ZhihuDailyPurify/wiki/%E7%9F%A5%E4%B9%8E%E6%97%A5%E6%8A%A5-API-%E5%88%86%E6%9E%90）
 */

public interface DailyApi {
    String NEWS_HOST = "http://news-at.zhihu.com/api/4/";
    String SUPPORT_HOST = "https://lilinmao.github.io/projects/dailyzhihu/api/1/";

    @GET("news/latest")
    Flowable<LatestStoryListBean> getLatestNewsList();

    @GET("news/before/{date}")
    Flowable<PastStoryListBean> getPastNewsList(@Path("date") String date);

    @GET("news/hot")
    Flowable<HotStoryListBean> getHotNewsList();

    @GET("themes")
    Flowable<TopicListBean> getTopicList();

    @GET("sections")
    Flowable<ColumnListBean> getColumnList();

    @GET("theme/{themeId}")
    Flowable<TopicStoryListBean> getTopicNewsList(@Path("themeId") int themeId);

    @GET("section/{sectionId}")
    Flowable<ColumnStoryListBean> getColumnNewsList(@Path("sectionId") int sectionId);

    @GET("news/{newsId}")
    Flowable<CertainStoryBean> getNewsDetail(@Path("newsId") int newsId);

    @GET("news/{newsId}/{commentType}")
    Flowable<CommentListBean> getCommentList(@Path("newsId") int newsId, @Path("commentType") String commentType);

    @GET("updates/index.html")
    Flowable<UpdateInfoBean> getUpdateInfo();
}
