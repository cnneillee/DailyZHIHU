package com.neil.dailyzhihu.presenter.constract;

import com.neil.dailyzhihu.base.BasePresenter;
import com.neil.dailyzhihu.base.BaseView;
import com.neil.dailyzhihu.model.bean.orignal.CertainStoryBean;
import com.neil.dailyzhihu.model.bean.orignal.StoryExtraInfoBean;

/**
 * 作者：Neil on 2017/4/6 17:16.
 * 邮箱：cn.neillee@gmail.com
 */

public interface StoryDetailContract {
    interface View extends BaseView {
        void showContent(CertainStoryBean storyBean);

        void showError(String errMsg);

        void showExtras(StoryExtraInfoBean infoBean);
    }

    interface Presenter extends BasePresenter<View> {
        void getStoryData(int storyId);

        void getStoryExtras(int storyId);
    }
}
