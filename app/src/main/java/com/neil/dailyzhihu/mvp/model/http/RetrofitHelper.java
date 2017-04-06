package com.neil.dailyzhihu.mvp.model.http;

import com.neil.dailyzhihu.mvp.model.bean.orignal.ColumnListBean;
import com.neil.dailyzhihu.mvp.model.bean.orignal.CommentListBean;
import com.neil.dailyzhihu.mvp.model.http.api.DailyApi;

import io.reactivex.Flowable;

/**
 * 作者：Neil on 2017/4/5 14:47.
 * 邮箱：cn.neillee@gmail.com
 * 对外暴露一致的接口
 */

public class RetrofitHelper {
    private DailyApi mDailyApi;

    public RetrofitHelper(DailyApi dailyApi) {
        this.mDailyApi = dailyApi;
    }

    public Flowable<ColumnListBean> fetchColumnList() {
        return mDailyApi.getColumnList();
    }

    public Flowable<CommentListBean> fetchStoryComment(int storyId, String commentType) {
        return mDailyApi.getCommentList(storyId, commentType);
    }
}