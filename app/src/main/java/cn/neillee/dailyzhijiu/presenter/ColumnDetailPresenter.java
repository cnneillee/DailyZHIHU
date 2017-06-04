package cn.neillee.dailyzhijiu.presenter;

import cn.neillee.dailyzhijiu.base.RxPresenter;
import cn.neillee.dailyzhijiu.model.bean.orignal.ColumnStoryListBean;
import cn.neillee.dailyzhijiu.model.http.RetrofitHelper;
import cn.neillee.dailyzhijiu.presenter.constract.ColumnDetailContract;
import com.orhanobut.logger.Logger;

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
                if (response.isSuccessful()) mView.showContent(response.body());
                else Logger.e("Error[%d] in request ColumnDetailData", response.code());
            }

            @Override
            public void onFailure(Call<ColumnStoryListBean> call, Throwable t) {
                mView.showError(t.getMessage());
            }
        });
    }
}
