package com.neil.dailyzhihu.presenter;

import com.neil.dailyzhihu.base.RxPresenter;
import com.neil.dailyzhihu.model.bean.orignal.CertainStoryBean;
import com.neil.dailyzhihu.model.bean.orignal.StoryExtraInfoBean;
import com.neil.dailyzhihu.model.http.RetrofitHelper;
import com.neil.dailyzhihu.presenter.constract.StoryDetailContract;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：Neil on 2017/4/6 17:17.
 * 邮箱：cn.neillee@gmail.com
 */

public class StoryDetailPresenter extends RxPresenter<StoryDetailContract.View> implements StoryDetailContract.Presenter {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    StoryDetailPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getStoryData(int storyId) {
        mRetrofitHelper.fetchNewsDetail(storyId).enqueue(new Callback<CertainStoryBean>() {
            @Override
            public void onResponse(Call<CertainStoryBean> call, Response<CertainStoryBean> response) {
                if (response.isSuccessful()) {
                    mView.showContent(response.body());
                }
            }

            @Override
            public void onFailure(Call<CertainStoryBean> call, Throwable t) {
                mView.showError(t.getMessage());
            }
        });
    }

    @Override
    public void getStoryExtras(int storyId) {
        mRetrofitHelper.fetchNewsExtraInfo(storyId).enqueue(new Callback<StoryExtraInfoBean>() {
            @Override
            public void onResponse(Call<StoryExtraInfoBean> call, Response<StoryExtraInfoBean> response) {
                mView.showExtras(response.body());
            }

            @Override
            public void onFailure(Call<StoryExtraInfoBean> call, Throwable t) {
                mView.showError(t.getMessage());
            }
        });
    }
}
