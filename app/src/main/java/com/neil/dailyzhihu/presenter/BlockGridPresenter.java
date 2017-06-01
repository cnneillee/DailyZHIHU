package com.neil.dailyzhihu.presenter;

import com.neil.dailyzhihu.base.RxPresenter;
import com.neil.dailyzhihu.model.bean.orignal.ColumnListBean;
import com.neil.dailyzhihu.model.bean.orignal.TopicListBean;
import com.neil.dailyzhihu.model.http.RetrofitHelper;
import com.neil.dailyzhihu.presenter.constract.BlockGridContract;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：Neil on 2017/4/6 22:29.
 * 邮箱：cn.neillee@gmail.com
 */

public class BlockGridPresenter extends RxPresenter<BlockGridContract.View> implements BlockGridContract.Presenter {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    BlockGridPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getBlockData(int type) {
        switch (type) {
            case BlockGridContract.COLUMN:
                mRetrofitHelper.fetchColumnList().enqueue(new Callback<ColumnListBean>() {
                    @Override
                    public void onResponse(Call<ColumnListBean> call, Response<ColumnListBean> response) {
                        if (response.isSuccessful()) mView.showContent(response.body());
                        else Logger.e("Error[%d] in request COLUMN BlockData", response.code());
                    }

                    @Override
                    public void onFailure(Call<ColumnListBean> call, Throwable t) {
                        mView.showError(t.getMessage());
                    }
                });
                break;
            case BlockGridContract.TOPIC:
                mRetrofitHelper.fetchTopicList().enqueue(new Callback<TopicListBean>() {
                    @Override
                    public void onResponse(Call<TopicListBean> call, Response<TopicListBean> response) {
                        if (response.isSuccessful()) mView.showContent(response.body());
                        else Logger.e("Error[%d] in request TOPIC BlockData", response.code());
                    }

                    @Override
                    public void onFailure(Call<TopicListBean> call, Throwable t) {
                        mView.showError(t.getMessage());
                    }
                });
                break;
        }
    }
}
