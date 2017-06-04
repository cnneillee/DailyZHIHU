package cn.neillee.dailyzhijiu.presenter.constract;

import cn.neillee.dailyzhijiu.base.BasePresenter;
import cn.neillee.dailyzhijiu.base.BaseView;
import cn.neillee.dailyzhijiu.model.bean.orignal.TopicStoryListBean;

/**
 * 作者：Neil on 2017/4/6 20:22.
 * 邮箱：cn.neillee@gmail.com
 */

public interface TopicDetailContract {
    interface View extends BaseView {
        void showContent(TopicStoryListBean bean);

        void showError(String errMsg);
    }

    interface Presenter extends BasePresenter<View> {
        void getTopicDetailData(int topicId);
    }
}
