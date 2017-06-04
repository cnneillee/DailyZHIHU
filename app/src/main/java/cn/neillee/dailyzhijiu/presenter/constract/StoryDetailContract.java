package cn.neillee.dailyzhijiu.presenter.constract;

import cn.neillee.dailyzhijiu.base.BasePresenter;
import cn.neillee.dailyzhijiu.base.BaseView;
import cn.neillee.dailyzhijiu.model.bean.orignal.CertainStoryBean;
import cn.neillee.dailyzhijiu.model.bean.orignal.StoryExtraInfoBean;
import cn.neillee.dailyzhijiu.model.db.StarRecord;

/**
 * 作者：Neil on 2017/4/6 17:16.
 * 邮箱：cn.neillee@gmail.com
 */

public interface StoryDetailContract {
    interface View extends BaseView {
        void showContent(CertainStoryBean storyBean);

        void showError(String errMsg);

        void showExtras(StoryExtraInfoBean infoBean);

        void showStarRecord(StarRecord record, boolean show);
    }

    interface Presenter extends BasePresenter<View> {
        void getStoryData(int storyId);

        void getStoryExtras(int storyId);

        void queryStarRecord(int storyId);

        void queryCachedStory(int storyId);

        void cacheCachedStory(CertainStoryBean story);

        void starStory(int storyId, String title, String image);
    }
}
