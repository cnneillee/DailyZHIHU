package cn.neillee.dailyzhijiu.ui.story;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import cn.neillee.dailyzhijiu.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.neillee.dailyzhijiu.base.BaseFragment;
import cn.neillee.dailyzhijiu.model.bean.orignal.CommentListBean;
import cn.neillee.dailyzhijiu.presenter.StoryCommentPresenter;
import cn.neillee.dailyzhijiu.presenter.constract.StoryCommentContract;
import cn.neillee.dailyzhijiu.ui.adapter.CommentListBaseAdapter;
import cn.neillee.dailyzhijiu.utils.AppUtil;
import cn.neillee.dailyzhijiu.utils.SnackbarUtil;

import static cn.neillee.dailyzhijiu.model.http.api.AtyExtraKeyConstant.COMMENT_TYPE;
import static cn.neillee.dailyzhijiu.model.http.api.AtyExtraKeyConstant.STORY_ID;

/**
 * 作者：Neil on 2017/4/6 01:04.
 * 邮箱：cn.neillee@gmail.com
 */

public class StoryCommentFragment extends BaseFragment<StoryCommentPresenter>
        implements StoryCommentContract.View, AdapterView.OnItemLongClickListener {
    @BindView(R.id.empty_layout)
    LinearLayout mEmptyLayout;
    @BindView(R.id.lv_comment)
    ListView mLvComment;

    private List<CommentListBean.CommentsBean> mCommentsBeanList;
    private CommentListBaseAdapter mCommentListBaseAdapter;

    public static StoryCommentFragment newInstance() {
        return new StoryCommentFragment();
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_vp_comment;
    }

    @Override
    protected void initEventAndData() {
        Bundle bundle = getArguments();
        int storyId = bundle.getInt(STORY_ID);
        int commentType = bundle.getInt(COMMENT_TYPE);

        mCommentsBeanList = new ArrayList<>();
        mCommentListBaseAdapter = new CommentListBaseAdapter(getActivity(), mCommentsBeanList);
        mLvComment.setAdapter(mCommentListBaseAdapter);
        mPresenter.getCommentData(storyId, commentType);
        mLvComment.setOnItemLongClickListener(this);
    }

    @Override
    public void showContent(CommentListBean commentList) {
        if (commentList.getComments() == null
                || commentList.getComments().size() <= 0) {
            mLvComment.setVisibility(View.GONE);
            mEmptyLayout.setVisibility(View.VISIBLE);
        } else {
            mEmptyLayout.setVisibility(View.GONE);
            mLvComment.setVisibility(View.VISIBLE);
        }
        mCommentsBeanList.clear();
        mCommentsBeanList.addAll(commentList.getComments());
        mCommentListBaseAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String msg) {
        mEmptyLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        CommentListBean.CommentsBean commentBean = mCommentsBeanList.get(position);
        String text = String.format("%s[%d Likes]：{%s}\n", commentBean.getAuthor(),
                commentBean.getLikes(), commentBean.getContent());
        if (commentBean.getReplyTo() != null) {
            CommentListBean.CommentsBean.ReplyToBean replyToBean = commentBean.getReplyTo();
            if (replyToBean.getStatus() == 0) { // OK
                text = String.format("%s\nreplyTo：\n%s：{%s}", text,
                        replyToBean.getAuthor(), replyToBean.getContent());
            } else {
                text = String.format("%s\nreplyTo：\n%s：[%s]", text,
                        replyToBean.getAuthor(), replyToBean.getErrMsg());
            }
        }
        AppUtil.copyText2Clipboard(mContext, text);
        SnackbarUtil.ShortSnackbarWithTheme(mContext, getView(), getString(
                R.string.notify_comment_copied)).show();
        return false;
    }
}
