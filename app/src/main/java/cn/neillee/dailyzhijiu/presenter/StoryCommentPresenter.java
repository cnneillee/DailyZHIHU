package cn.neillee.dailyzhijiu.presenter;

import cn.neillee.dailyzhijiu.base.RxPresenter;
import cn.neillee.dailyzhijiu.model.bean.orignal.CommentListBean;
import cn.neillee.dailyzhijiu.model.http.RetrofitHelper;
import cn.neillee.dailyzhijiu.presenter.constract.StoryCommentContract;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * 作者：Neil on 2017/4/5 13:57.
 * 邮箱：cn.neillee@gmail.com
 */

public class StoryCommentPresenter extends RxPresenter<StoryCommentContract.View> implements StoryCommentContract.Presenter {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    StoryCommentPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
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
        mRetrofitHelper.fetchNewsComment(id, commentType == 0 ? "long-comments" : "short-comments")
                .enqueue(new Callback<CommentListBean>() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if (response.isSuccessful()) {
                            CommentListBean bean = (CommentListBean) response.body();
                            mView.showContent(bean);
                        } else Logger.e("Error[%d] in request NewsComment", response.code());
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        mView.showError(t.getMessage());
                    }
                });
    }
}
