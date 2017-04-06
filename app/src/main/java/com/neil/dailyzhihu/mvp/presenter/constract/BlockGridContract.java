package com.neil.dailyzhihu.mvp.presenter.constract;

import com.neil.dailyzhihu.base.BaseView;

/**
 * 作者：Neil on 2017/4/6 22:26.
 * 邮箱：cn.neillee@gmail.com
 */

public interface BlockGridContract {
    interface View extends BaseView<Presenter> {
        void showContent(String content);
    }

    interface Presenter<View> {
        void getBlockData(String url);
    }
}
