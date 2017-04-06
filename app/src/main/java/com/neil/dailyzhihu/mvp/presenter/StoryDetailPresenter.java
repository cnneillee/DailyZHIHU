package com.neil.dailyzhihu.mvp.presenter;

import com.google.gson.Gson;
import com.neil.dailyzhihu.base.RxPresenter;
import com.neil.dailyzhihu.listener.OnContentLoadedListener;
import com.neil.dailyzhihu.mvp.model.bean.orignal.CertainStoryBean;
import com.neil.dailyzhihu.mvp.model.http.api.API;
import com.neil.dailyzhihu.mvp.presenter.constract.MainFragmentContract;
import com.neil.dailyzhihu.mvp.presenter.constract.StoryDetailContract;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.load.LoaderFactory;

/**
 * 作者：Neil on 2017/4/6 17:17.
 * 邮箱：cn.neillee@gmail.com
 */

public class StoryDetailPresenter extends RxPresenter<MainFragmentContract.View>
        implements StoryDetailContract.Presenter {
    private StoryDetailContract.View mView;

    public StoryDetailPresenter(StoryDetailContract.View view) {
        mView = view;
    }

    @Override
    public void getStoryData(int storyId) {
        LoaderFactory.getContentLoader().loadContent(API.STORY_PREFIX + storyId, new OnContentLoadedListener() {
            @Override
            public void onSuccess(String content, String url) {
                mView.showContent(GsonDecoder.getDecoder().decoding(content, CertainStoryBean.class));
            }
        });

    }
}
