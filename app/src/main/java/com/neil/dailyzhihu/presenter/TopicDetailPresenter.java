package com.neil.dailyzhihu.presenter;

import com.neil.dailyzhihu.base.RxPresenter;
import com.neil.dailyzhihu.listener.OnContentLoadListener;
import com.neil.dailyzhihu.model.http.api.API;
import com.neil.dailyzhihu.presenter.constract.TopicDetailContract;
import com.neil.dailyzhihu.utils.load.LoaderFactory;

import javax.inject.Inject;

/**
 * 作者：Neil on 2017/4/6 22:11.
 * 邮箱：cn.neillee@gmail.com
 */

public class TopicDetailPresenter extends RxPresenter<TopicDetailContract.View> implements TopicDetailContract.Presenter {
    @Inject
    public TopicDetailPresenter() {
    }

    @Override
    public void getTopicDetailData(int topicId) {
        LoaderFactory.getContentLoader().loadContent(API.THEME_PREFIX + topicId, new OnContentLoadListener() {
            @Override
            public void onSuccess(String content, String url) {
                mView.showContent(content);
            }

            @Override
            public void onFailure(String errMsg) {
                mView.showError(errMsg);
            }
        });
    }
}
