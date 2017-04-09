package com.neil.dailyzhihu.presenter;

import com.neil.dailyzhihu.ui.adapter.CommentTypesPagerAdapter;
import com.neil.dailyzhihu.base.RxPresenter;
import com.neil.dailyzhihu.listener.OnContentLoadListener;
import com.neil.dailyzhihu.model.bean.orignal.CommentListBean;
import com.neil.dailyzhihu.model.http.api.API;
import com.neil.dailyzhihu.presenter.constract.StoryCommentContract;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.load.LoaderFactory;

import javax.inject.Inject;


/**
 * 作者：Neil on 2017/4/5 13:57.
 * 邮箱：cn.neillee@gmail.com
 */

public class StoryCommentPresenter extends RxPresenter<StoryCommentContract.View> implements StoryCommentContract.Presenter {

    @Inject
    public StoryCommentPresenter() {
    }

//    /**
//     * Method injection is used here to safely reference {@code this} after the object is created.
//     * For more information, see Java Concurrency in Practice.
//     * 这里参见了 https://github.com/googlesamples/android-architecture/blob/todo-mvp-dagger/todoapp/app/src/main/java/com/example/android/architecture/blueprints/todoapp/addedittask/AddEditTaskPresenter.java
//     */
//    @Inject
//    void setupListeners() {
//        mView.setPresenter(this);
//    }

    @Override
    public void getCommentData(int id, final int commentType) {
        String url = API.STORY_COMMENT_PREFIX + id + CommentTypesPagerAdapter.CommentType.getType(commentType).urlSuffix;
        LoaderFactory.getContentLoader().loadContent(url, new OnContentLoadListener() {
            @Override
            public void onSuccess(String content, String url) {
                CommentListBean bean = GsonDecoder.getDecoder().decoding(content, CommentListBean.class);
                mView.showContent(bean);
            }

            @Override
            public void onFailure(String errMsg) {
                mView.showError(errMsg);
            }
        });
    }
}
