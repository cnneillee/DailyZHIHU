package com.neil.dailyzhihu.presenter;

import com.neil.dailyzhihu.base.RxPresenter;
import com.neil.dailyzhihu.model.bean.orignal.HotStoryListBean;
import com.neil.dailyzhihu.model.bean.orignal.LatestStoryListBean;
import com.neil.dailyzhihu.model.bean.orignal.PastStoryListBean;
import com.neil.dailyzhihu.model.http.RetrofitHelper;
import com.neil.dailyzhihu.presenter.constract.MainFragmentContract;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：Neil on 2017/4/6 16:52.
 * 邮箱：cn.neillee@gmail.com
 */

public class MainFragmentPresenter extends RxPresenter<MainFragmentContract.View> implements MainFragmentContract.Presenter {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    MainFragmentPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getNewsListData(int type, String params) {
        switch (type) {
            case MainFragmentContract.HOT:
                mRetrofitHelper.fetchHotNewsList().enqueue(new Callback<HotStoryListBean>() {
                    @Override
                    public void onResponse(Call<HotStoryListBean> call, Response<HotStoryListBean> response) {
                        mView.showContent(response.body());
                    }

                    @Override
                    public void onFailure(Call<HotStoryListBean> call, Throwable t) {
                        mView.showError(t.getMessage());
                    }
                });
                break;
            case MainFragmentContract.LATEST:
                mRetrofitHelper.fetchLatestNewsList().enqueue(new Callback<LatestStoryListBean>() {
                    @Override
                    public void onResponse(Call<LatestStoryListBean> call, Response<LatestStoryListBean> response) {
                        mView.showContent(response.body());
                    }

                    @Override
                    public void onFailure(Call<LatestStoryListBean> call, Throwable t) {
                        mView.showError(t.getMessage());
                    }
                });
                break;
            case MainFragmentContract.PAST:
                mRetrofitHelper.fetchPastNewsList(params).enqueue(new Callback<PastStoryListBean>() {
                    @Override
                    public void onResponse(Call<PastStoryListBean> call, Response<PastStoryListBean> response) {
                        mView.showContent(response.body());
                    }

                    @Override
                    public void onFailure(Call<PastStoryListBean> call, Throwable t) {
                        mView.showError(t.getMessage());
                    }
                });
                break;
        }
    }

    @Override
    public void startRefresh(int type, String params) {
        getNewsListData(type, params);
    }
}
