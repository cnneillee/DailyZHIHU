package com.neil.dailyzhihu.ui.story;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.CommentListBaseAdapter;
import com.neil.dailyzhihu.di.component.DaggerStoryCommentComponent;
import com.neil.dailyzhihu.di.module.StoryCommentModule;
import com.neil.dailyzhihu.mvp.model.bean.orignal.CommentListBean;
import com.neil.dailyzhihu.mvp.presenter.StoryCommentPresenter;
import com.neil.dailyzhihu.mvp.presenter.constract.StoryCommentContract;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.neil.dailyzhihu.mvp.model.http.api.AtyExtraKeyConstant.COMMENT_TYPE;
import static com.neil.dailyzhihu.mvp.model.http.api.AtyExtraKeyConstant.STORY_ID;

/**
 * 作者：Neil on 2017/4/6 01:04.
 * 邮箱：cn.neillee@gmail.com
 */

public class StoryCommentFragment extends Fragment implements StoryCommentContract.View {
    @BindView(R.id.tv_addon_info)
    TextView mTvAddonInfo;
    @BindView(R.id.lv_comment)
    ListView mLvComment;

    @Inject
    StoryCommentPresenter mPresenter;

    public static StoryCommentFragment newInstance() {
        return new StoryCommentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.vp_item_comment, container, false);
        ButterKnife.bind(this, contentView);

        DaggerStoryCommentComponent.builder()
                .storyCommentModule(new StoryCommentModule(this))
                .build()
                .inject(this);

        Bundle bundle = getArguments();
        int storyId = bundle.getInt(STORY_ID);
        int commentType = bundle.getInt(COMMENT_TYPE);

        mPresenter.getCommentData(storyId, commentType);

        return contentView;
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

    @Override
    public void setPresenter(StoryCommentContract.Presenter presenter) {

    }
}
