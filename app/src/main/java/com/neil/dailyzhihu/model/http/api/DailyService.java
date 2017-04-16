package com.neil.dailyzhihu.model.http.api;

import com.neil.dailyzhihu.model.bean.orignal.CertainStoryBean;
import com.neil.dailyzhihu.model.bean.orignal.ColumnListBean;
import com.neil.dailyzhihu.model.bean.orignal.ColumnStoryListBean;
import com.neil.dailyzhihu.model.bean.orignal.CommentListBean;
import com.neil.dailyzhihu.model.bean.orignal.HotStoryListBean;
import com.neil.dailyzhihu.model.bean.orignal.LatestStoryListBean;
import com.neil.dailyzhihu.model.bean.orignal.PastStoryListBean;
import com.neil.dailyzhihu.model.bean.orignal.StoryExtraInfoBean;
import com.neil.dailyzhihu.model.bean.orignal.TopicListBean;
import com.neil.dailyzhihu.model.bean.orignal.TopicStoryListBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 作者：Neil on 2017/4/5 14:19.
 * 邮箱：cn.neillee@gmail.com
 * 知乎日报API（https://github.com/izzyleung/ZhihuDailyPurify/wiki/%E7%9F%A5%E4%B9%8E%E6%97%A5%E6%8A%A5-API-%E5%88%86%E6%9E%90）
 */

public interface DailyService {
    String NEWS_HOST = "http://news-at.zhihu.com/api/4/";

    @GET("news/latest")
    Call<LatestStoryListBean> getLatestNewsList();

    @GET("news/before/{date}")
    Call<PastStoryListBean> getPastNewsList(@Path("date") String date);

    @GET("news/hot")
    Call<HotStoryListBean> getHotNewsList();

    @GET("themes")
    Call<TopicListBean> getTopicList();

    @GET("sections")
    Call<ColumnListBean> getColumnList();

    @GET("theme/{themeId}")
    Call<TopicStoryListBean> getTopicNewsList(@Path("themeId") int themeId);

    @GET("section/{sectionId}")
    Call<ColumnStoryListBean> getColumnNewsList(@Path("sectionId") int sectionId);

    @GET("news/{newsId}")
    Call<CertainStoryBean> getNewsDetail(@Path("newsId") int newsId);

    @GET("news/{newsId}/{commentType}")
    Call<CommentListBean> getCommentList(@Path("newsId") int newsId, @Path("commentType") String commentType);

    @GET("story-extra/{storyId}")
    Call<StoryExtraInfoBean> getStoryExtraInfo(@Path("storyId") int storyId);

    @GET("editor/{editorId}/profile-page/android")
    Call<String> getEditorProfileInfo(@Path("editorId") int editorId);
}
