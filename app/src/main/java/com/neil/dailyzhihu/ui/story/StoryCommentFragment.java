package com.neil.dailyzhihu.ui.story;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.CommentListBaseAdapter;
import com.neil.dailyzhihu.base.BaseFragment;
import com.neil.dailyzhihu.mvp.model.bean.orignal.CommentListBean;
import com.neil.dailyzhihu.mvp.presenter.StoryCommentPresenter;
import com.neil.dailyzhihu.mvp.presenter.constract.StoryCommentContract;

import butterknife.BindView;

import static com.neil.dailyzhihu.mvp.model.http.api.AtyExtraKeyConstant.COMMENT_TYPE;
import static com.neil.dailyzhihu.mvp.model.http.api.AtyExtraKeyConstant.STORY_ID;

/**
 * 作者：Neil on 2017/4/6 01:04.
 * 邮箱：cn.neillee@gmail.com
 */

public class StoryCommentFragment extends BaseFragment<StoryCommentPresenter> implements StoryCommentContract.View {
    @BindView(R.id.tv_addon_info)
    TextView mTvAddonInfo;
    @BindView(R.id.lv_comment)
    ListView mLvComment;

    public static StoryCommentFragment newInstance() {
        return new StoryCommentFragment();
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.vp_item_comment;
    }

    @Override
    protected void initEventAndData() {
        Bundle bundle = getArguments();
        int storyId = bundle.getInt(STORY_ID);
        int commentType = bundle.getInt(COMMENT_TYPE);
        mPresenter.getCommentData(storyId, commentType);
    }

    @Override
    public void showContent(CommentListBean commentList) {
        CommentListBaseAdapter adapter = new CommentListBaseAdapter(getActivity(), commentList.getComments());
        mLvComment.setAdapter(adapter);
    }

    @Override
    public void showError(String msg) {
        mTvAddonInfo.setVisibility(View.VISIBLE);
    }

}
