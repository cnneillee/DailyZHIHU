package com.neil.dailyzhihu.mvp.presenter.constract;

import com.neil.dailyzhihu.base.BasePresenter;
import com.neil.dailyzhihu.base.BaseView;

/**
 * 作者：Neil on 2017/4/6 20:22.
 * 邮箱：cn.neillee@gmail.com
 */

public interface TopicDetailContract {
    interface View extends BaseView<Presenter> {
        void showContent(String content);
        void showError(String errMsg);
    }

    interface Presenter extends BasePresenter<View> {
        void getTopicDetailData(int topicId);
    }
}
