package com.neil.dailyzhihu.presenter;

import com.neil.dailyzhihu.base.RxPresenter;
import com.neil.dailyzhihu.model.db.GreenDaoHelper;
import com.neil.dailyzhihu.model.db.StarRecord;
import com.neil.dailyzhihu.model.http.RetrofitHelper;
import com.neil.dailyzhihu.presenter.constract.StoryStaredContract;

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
