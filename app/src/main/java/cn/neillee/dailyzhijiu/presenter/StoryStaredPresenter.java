package cn.neillee.dailyzhijiu.presenter;

import cn.neillee.dailyzhijiu.base.RxPresenter;
import cn.neillee.dailyzhijiu.model.db.GreenDaoHelper;
import cn.neillee.dailyzhijiu.model.db.StarRecord;
import cn.neillee.dailyzhijiu.model.http.RetrofitHelper;
import cn.neillee.dailyzhijiu.presenter.constract.StoryStaredContract;

import java.util.List;

import javax.inject.Inject;

/**
 * 作者：Neil on 2017/6/1 14:36.
 * 邮箱：cn.neillee@gmail.com
 */

public class StoryStaredPresenter extends RxPresenter<StoryStaredContract.View>
        implements StoryStaredContract.Presenter {
    private GreenDaoHelper mGreenDaoHelper;
    private RetrofitHelper mRetrofitHelper;

    @Inject
    StoryStaredPresenter(GreenDaoHelper greenDaoHelper, RetrofitHelper retrofitHelper) {
        mGreenDaoHelper = greenDaoHelper;
        mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getStaredStory() {
        List<StarRecord> starRecords = mGreenDaoHelper.queryAllStarRecord();
        if (starRecords == null || starRecords.size() == 0) mView.showEmptyStared();
        else mView.showStaredRecord(starRecords);
    }
}
