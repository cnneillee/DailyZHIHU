package com.neil.dailyzhihu.mvp.presenter;

import com.neil.dailyzhihu.base.RxPresenter;
import com.neil.dailyzhihu.listener.OnContentLoadListener;
import com.neil.dailyzhihu.mvp.model.bean.orignal.ColumnStoryListBean;
import com.neil.dailyzhihu.mvp.model.http.api.API;
import com.neil.dailyzhihu.mvp.presenter.constract.ColumnDetailContract;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.load.LoaderFactory;

import javax.inject.Inject;

/**
 * 作者：Neil on 2017/4/6 22:37.
 * 邮箱：cn.neillee@gmail.com
 */

public class ColumnDetailPresenter extends RxPresenter<ColumnDetailContract.View> implements ColumnDetailContract.Presenter {

    @Inject
    public ColumnDetailPresenter() {
    }

    @Override
    public void getColumnDetailData(int columnId) {
        LoaderFactory.getContentLoader().loadContent(API.SECTION_PREFIX + columnId, new OnContentLoadListener() {
            @Override
            public void onSuccess(String content, String url) {
                mView.showContent(GsonDecoder.getDecoder().decoding(content, ColumnStoryListBean.class));
            }

            @Override
            public void onFailure(String errMsg) {
                mView.showError(errMsg);
            }
        });
    }
}
