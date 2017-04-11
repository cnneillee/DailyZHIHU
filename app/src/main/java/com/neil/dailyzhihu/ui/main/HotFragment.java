package com.neil.dailyzhihu.ui.main;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.base.BaseFragment;
import com.neil.dailyzhihu.model.bean.orignal.HotStoryListBean;
import com.neil.dailyzhihu.model.bean.orignal.OriginalStory;
import com.neil.dailyzhihu.model.http.api.AtyExtraKeyConstant;
import com.neil.dailyzhihu.presenter.MainFragmentPresenter;
import com.neil.dailyzhihu.presenter.constract.MainFragmentContract;
import com.neil.dailyzhihu.ui.adapter.HotStoryListBaseAdapter;
import com.neil.dailyzhihu.ui.story.StoryDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HotFragment extends BaseFragment<MainFragmentPresenter> implements ObservableScrollViewCallbacks,
        AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, MainFragmentContract.View {

    private static final String LOG_TAG = HotFragment.class.getSimpleName();

    @BindView(R.id.lv_hot)
    ObservableListView mLVHot;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;

    private HotStoryListBaseAdapter mHotAdapter;
    private List<HotStoryListBean.HotStory> mHotList;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot;
    }

    @Override
    protected void initEventAndData() {
        View header = LayoutInflater.from(mContext).inflate(R.layout.header_gap8dp, null, false);
        mLVHot.addHeaderView(header);
        mLVHot.setOnItemClickListener(this);
        mLVHot.setScrollViewCallbacks(this);
        mSrlRefresh.setOnRefreshListener(this);
        mSrlRefresh.setRefreshing(true);

        mHotList = new ArrayList<>();
        mHotAdapter = new HotStoryListBaseAdapter(mContext, mHotList);
        mLVHot.setAdapter(mHotAdapter);

        mPresenter.getNewsListData(MainFragmentContract.HOT, "");
        mSrlRefresh.setRefreshing(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HotStoryListBean.HotStory bean = (HotStoryListBean.HotStory) parent.getAdapter().getItem(position);
        Intent intent = new Intent(mContext, StoryDetailActivity.class);
        intent.putExtra(AtyExtraKeyConstant.STORY_ID, bean.getStoryId());
        intent.putExtra(AtyExtraKeyConstant.DEFAULT_IMG_URL, bean.getThumbnail());
        startActivity(intent);
    }

    @Override
    public void showContent(OriginalStory content) {
        mSrlRefresh.setRefreshing(false);

        List<HotStoryListBean.HotStory> hotStoryList = ((HotStoryListBean) content).getStories();
        mHotList.clear();
        for (int i = 0; i < hotStoryList.size(); i++) {
            mHotList.add(hotStoryList.get(i));
        }

        mHotAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String errorMsg) {
        // ERROR处理
        mSrlRefresh.setRefreshing(false);
    }

    @Override
    public void refresh(OriginalStory content) {
        showContent(content);
    }

    @Override
    public void onRefresh() {
        mPresenter.getNewsListData(MainFragmentContract.HOT, "");
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        AppCompatActivity activity = (AppCompatActivity) mContext;
        ActionBar ab = activity.getSupportActionBar();
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
