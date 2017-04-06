package com.neil.dailyzhihu.mvp.presenter;

import com.neil.dailyzhihu.base.RxPresenter;
import com.neil.dailyzhihu.listener.OnContentLoadedListener;
import com.neil.dailyzhihu.mvp.presenter.constract.MainFragmentContract;
import com.neil.dailyzhihu.utils.load.LoaderFactory;

/**
 * 作者：Neil on 2017/4/6 16:52.
 * 邮箱：cn.neillee@gmail.com
 */

public class MainFragmentPresenter extends RxPresenter<MainFragmentContract.View>
        implements MainFragmentContract.Presenter {

    private MainFragmentContract.View mView;

    public MainFragmentPresenter(MainFragmentContract.View view) {
        mView = view;
    }

    @Override
    public void getNewsListData(String url) {
        LoaderFactory.getContentLoader().loadContent(url, new OnContentLoadedListener() {
            @Override
            public void onSuccess(String content, String url) {
                mView.showContent(content);
                // TODO 处理ERROR
            }
        });
    }
}
