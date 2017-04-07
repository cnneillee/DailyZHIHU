package com.neil.dailyzhihu.mvp.presenter.constract;

import com.neil.dailyzhihu.base.BaseView;
import com.neil.dailyzhihu.mvp.model.bean.orignal.CertainStoryBean;

/**
 * 作者：Neil on 2017/4/6 17:16.
 * 邮箱：cn.neillee@gmail.com
 */

public interface StoryDetailContract {
    interface View extends BaseView{
        void showContent(CertainStoryBean storyBean);
        void showError(String errMsg);
    }

    interface Presenter {
        void getStoryData(int storyId);
    }
}
