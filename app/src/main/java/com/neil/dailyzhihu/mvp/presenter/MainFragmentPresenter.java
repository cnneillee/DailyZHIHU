package com.neil.dailyzhihu.mvp.presenter;

import com.neil.dailyzhihu.base.RxPresenter;
import com.neil.dailyzhihu.listener.OnContentLoadListener;
import com.neil.dailyzhihu.mvp.presenter.constract.MainFragmentContract;
import com.neil.dailyzhihu.utils.load.LoaderFactory;

import javax.inject.Inject;

/**
 * 作者：Neil on 2017/4/6 16:52.
 * 邮箱：cn.neillee@gmail.com
 */

public class MainFragmentPresenter extends RxPresenter<MainFragmentContract.View> implements MainFragmentContract.Presenter {

    @Inject
    public MainFragmentPresenter() {
    }

    @Override
    public void getNewsListData(String url) {
        LoaderFactory.getContentLoader().loadContent(url, new OnContentLoadListener() {
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

    @Override
    public void startRefresh(String refreshUrl) {
        LoaderFactory.getContentLoader().loadContent(refreshUrl, new OnContentLoadListener() {
            @Override
            public void onSuccess(String content, String url) {
                mView.refresh(content);
            }

            @Override
            public void onFailure(String errMsg) {
                mView.showError(errMsg);
            }
        });
    }
}
