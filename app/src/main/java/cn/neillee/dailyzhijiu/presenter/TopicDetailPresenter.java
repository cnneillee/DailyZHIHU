package cn.neillee.dailyzhijiu.presenter;

import cn.neillee.dailyzhijiu.base.RxPresenter;
import cn.neillee.dailyzhijiu.model.bean.orignal.TopicStoryListBean;
import cn.neillee.dailyzhijiu.model.http.RetrofitHelper;
import cn.neillee.dailyzhijiu.presenter.constract.TopicDetailContract;
import com.orhanobut.logger.Logger;

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
                if (response.isSuccessful()) mView.showContent(response.body());
                else Logger.e("Error[%d] in request TopicDetailData", response.code());
            }

            @Override
            public void onFailure(Call<TopicStoryListBean> call, Throwable t) {
                mView.showError(t.getMessage());
            }
        });
    }
}
