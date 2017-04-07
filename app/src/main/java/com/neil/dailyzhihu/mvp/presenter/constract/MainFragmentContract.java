package com.neil.dailyzhihu.mvp.presenter.constract;

import com.neil.dailyzhihu.base.BasePresenter;
import com.neil.dailyzhihu.base.BaseView;

/**
 * 作者：Neil on 2017/4/6 15:39.
 * 邮箱：cn.neillee@gmail.com
 */

public interface MainFragmentContract {
    interface View extends BaseView {
        void showContent(String content);

        void showError(String errorMsg);
    }

    interface Presenter extends BasePresenter<View> {
        void getNewsListData(String url);
    }
}
