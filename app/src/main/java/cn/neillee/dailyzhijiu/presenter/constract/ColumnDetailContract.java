package cn.neillee.dailyzhijiu.presenter.constract;

import cn.neillee.dailyzhijiu.base.BasePresenter;
import cn.neillee.dailyzhijiu.base.BaseView;
import cn.neillee.dailyzhijiu.model.bean.orignal.ColumnStoryListBean;

/**
 * 作者：Neil on 2017/4/6 22:34.
 * 邮箱：cn.neillee@gmail.com
 */

public interface ColumnDetailContract {
    interface View extends BaseView {
        void showContent(ColumnStoryListBean bean);

        void showError(String errMsg);
    }

    interface Presenter extends BasePresenter<View> {
        void getColumnDetailData(int columnId);
    }
}
