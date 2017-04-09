package com.neil.dailyzhihu.ui.topic;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.github.ksoichiro.android.observablescrollview.ObservableGridView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.ui.adapter.TopicGridBaseAdapter;
import com.neil.dailyzhihu.base.BaseActivity;
import com.neil.dailyzhihu.model.bean.orignal.TopicListBean;
import com.neil.dailyzhihu.model.http.api.API;
import com.neil.dailyzhihu.model.http.api.AtyExtraKeyConstant;
import com.neil.dailyzhihu.presenter.BlockGridPresenter;
import com.neil.dailyzhihu.presenter.constract.BlockGridContract;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：Neil on 2017/1/19 17:53.
 * 邮箱：cn.neillee@gmail.com
 * 主题日报窗口
 */
public class NavTopicsActivity extends BaseActivity<BlockGridPresenter> implements
        AdapterView.OnItemClickListener, ObservableScrollViewCallbacks, BlockGridContract.View {
    @BindView(R.id.gv_themes)
    ObservableGridView gvThemes;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private List<TopicListBean.TopicBean> mTopicBeanList;
    private TopicGridBaseAdapter mTopicAdapter;

    private View.OnClickListener upBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            NavTopicsActivity.this.finish();
        }
    };

    @Override
    protected void initEventAndData() {
        setToolbar(mToolbar, getResources().getString(R.string.nav_topics));
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);

        gvThemes.setOnItemClickListener(this);
        gvThemes.setScrollViewCallbacks(this);

        mTopicBeanList = new ArrayList<>();
        mTopicAdapter = new TopicGridBaseAdapter(mContext, mTopicBeanList);
        gvThemes.setAdapter(mTopicAdapter);

        mPresenter.getBlockData(API.THEMES);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_topics;
    }

    @Override
    public void showContent(String content) {
        Logger.json(content);
        TopicListBean themes = GsonDecoder.getDecoder().decoding(content, TopicListBean.class);
        List<TopicListBean.TopicBean> topicBeanList = themes.getOthers();
        mTopicBeanList.clear();
        for (int i = 0; i < topicBeanList.size(); i++) {
            mTopicBeanList.add(topicBeanList.get(i));
        }
        mTopicAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String errMsg) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TopicListBean.TopicBean bean = mTopicBeanList.get(position);
        int sectionId = bean.getStoryId();
        String topicImg = bean.getImage();
        Intent intent = new Intent(this, TopicDetailActivity.class);
        intent.putExtra(AtyExtraKeyConstant.THEME_ID, sectionId);
        intent.putExtra(AtyExtraKeyConstant.DEFAULT_IMG_URL, topicImg);
        startActivity(intent);
    }

    /**
     * 列表项滑动后对Actionbar施加影响
     *
     * @param scrollState 滑动状态
     */
    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        ActionBar ab = this.getSupportActionBar();
        if (ab == null) {
            return;
        }
        if (scrollState == ScrollState.UP) {
            if (ab.isShowing()) {
                ab.hide();
            }
        } else if (scrollState == ScrollState.DOWN) {
            if (!ab.isShowing()) {
                ab.show();
            }
        }
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }
}
