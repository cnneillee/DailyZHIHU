package cn.neillee.dailyzhijiu.presenter.constract;

import cn.neillee.dailyzhijiu.base.BasePresenter;
import cn.neillee.dailyzhijiu.base.BaseView;
import cn.neillee.dailyzhijiu.model.db.StarRecord;

import java.util.List;

/**
 * 作者：Neil on 2017/6/1 14:33.
 * 邮箱：cn.neillee@gmail.com
 */

public interface StoryStaredContract {
    interface View extends BaseView {
        void showEmptyStared();

        void showStaredRecord(List<StarRecord> recordList);
    }

    interface Presenter extends BasePresenter<View> {
        void getStaredStory();
    }
}
