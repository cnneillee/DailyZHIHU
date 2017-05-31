package com.neil.dailyzhihu.presenter;

import com.neil.dailyzhihu.base.RxPresenter;
import com.neil.dailyzhihu.model.bean.orignal.ColumnStoryListBean;
import com.neil.dailyzhihu.model.http.RetrofitHelper;
import com.neil.dailyzhihu.presenter.constract.ColumnDetailContract;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：Neil on 2017/4/6 22:37.
 * 邮箱：cn.neillee@gmail.com
 */

public class ColumnDetailPresenter extends RxPresenter<ColumnDetailContract.View> implements ColumnDetailContract.Presenter {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    ColumnDetailPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getColumnDetailData(int columnId) {
        mRetrofitHelper.fetchColumnNewsList(columnId).enqueue(new Callback<ColumnStoryListBean>() {
            @Override
            public void onResponse(Call<ColumnStoryListBean> call, Response<ColumnStoryListBean> response) {
                if (response.isSuccessful()) {
                    mView.showContent(response.body());
                }
            }

            @Override
            public void onFailure(Call<ColumnStoryListBean> call, Throwable t) {
                mView.showError(t.getMessage());
            }
        });
    }
}