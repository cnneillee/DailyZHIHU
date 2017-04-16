package com.neil.dailyzhihu.presenter;

import com.neil.dailyzhihu.base.RxPresenter;
import com.neil.dailyzhihu.model.bean.orignal.TopicStoryListBean;
import com.neil.dailyzhihu.model.http.RetrofitHelper;
import com.neil.dailyzhihu.presenter.constract.TopicDetailContract;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：Neil on 2017/4/6 22:11.
 * 邮箱：cn.neillee@gmail.com
 */

public class TopicDetailPresenter extends RxPresenter<TopicDetailContract.View> implements TopicDetailContract.Presenter {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    TopicDetailPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getTopicDetailData(int topicId) {
        mRetrofitHelper.fetchTopicNewsList(topicId).enqueue(new Callback<TopicStoryListBean>() {
            @Override
            public void onResponse(Call<TopicStoryListBean> call, Response<TopicStoryListBean> response) {
                if (response.isSuccessful()) {
                    mView.showContent(response.body());
                }
            }

            @Override
            public void onFailure(Call<TopicStoryListBean> call, Throwable t) {
                mView.showError(t.getMessage());
            }
        });
    }
}
