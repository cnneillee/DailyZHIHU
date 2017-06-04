package cn.neillee.dailyzhijiu.presenter.constract;

import cn.neillee.dailyzhijiu.base.BasePresenter;
import cn.neillee.dailyzhijiu.base.BaseView;
import cn.neillee.dailyzhijiu.model.bean.orignal.CommentListBean;

/**
 * 作者：Neil on 2017/4/5 14:08.
 * 邮箱：cn.neillee@gmail.com
 */

public interface StoryCommentContract {
    interface View extends BaseView {

        void showContent(CommentListBean commentList);

        void showError(String msg);
    }

    interface Presenter extends BasePresenter<View> {
        void getCommentData(int id, int commentType);
    }
}
