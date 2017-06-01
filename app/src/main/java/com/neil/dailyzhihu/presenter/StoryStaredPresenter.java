package com.neil.dailyzhihu.presenter;

import com.neil.dailyzhihu.base.RxPresenter;
import com.neil.dailyzhihu.model.db.CachedStory;
import com.neil.dailyzhihu.model.db.GreenDaoHelper;
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
        List<CachedStory> cachedStories = mGreenDaoHelper.queryAllStarStory();
        if (cachedStories == null || cachedStories.size() == 0) mView.showEmptyStared();
        else mView.showStaredStory(cachedStories);
    }
}
