package cn.neillee.dailyzhijiu.presenter;

import cn.neillee.dailyzhijiu.base.RxPresenter;
import cn.neillee.dailyzhijiu.model.bean.orignal.HotStoryListBean;
import cn.neillee.dailyzhijiu.model.bean.orignal.LatestStoryListBean;
import cn.neillee.dailyzhijiu.model.bean.orignal.PastStoryListBean;
import cn.neillee.dailyzhijiu.model.http.RetrofitHelper;
import cn.neillee.dailyzhijiu.presenter.constract.MainFragmentContract;
import com.orhanobut.logger.Logger;

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
                        if (response.isSuccessful()) mView.showContent(response.body());
                        else Logger.e("Error[%d] in request NewsListData", response.code());
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
                        if (response.isSuccessful()) mView.showContent(response.body());
                        else Logger.e("Error[%d] in request LatestNewsList", response.code());
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
                        if (response.isSuccessful()) mView.showContent(response.body());
                        else Logger.e("Error[%d] in request PastNewsList", response.code());
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
