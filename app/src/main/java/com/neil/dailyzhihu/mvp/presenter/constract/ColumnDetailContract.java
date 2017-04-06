package com.neil.dailyzhihu.mvp.presenter.constract;

import com.neil.dailyzhihu.base.BaseView;
import com.neil.dailyzhihu.mvp.model.bean.orignal.ColumnStoryListBean;

/**
 * 作者：Neil on 2017/4/6 22:34.
 * 邮箱：cn.neillee@gmail.com
 */

public interface ColumnDetailContract {
    interface View extends BaseView<Presenter> {
        void showContent(ColumnStoryListBean bean);
    }

    interface Presenter {
        void getColumnDetailData(int columnId);
    }
}
