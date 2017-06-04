package cn.neillee.dailyzhijiu.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.neil.dailyzhijiu.R;
import cn.neillee.dailyzhijiu.base.BaseFragment;
import cn.neillee.dailyzhijiu.model.bean.orignal.LatestStoryListBean;
import cn.neillee.dailyzhijiu.model.bean.orignal.LatestStoryListBean.LatestStory;
import cn.neillee.dailyzhijiu.model.bean.orignal.LatestStoryListBean.TopStoriesBean;
import cn.neillee.dailyzhijiu.model.bean.orignal.OriginalStory;
import cn.neillee.dailyzhijiu.model.http.api.AtyExtraKeyConstant;
import cn.neillee.dailyzhijiu.presenter.MainFragmentPresenter;
import cn.neillee.dailyzhijiu.presenter.constract.MainFragmentContract;
import cn.neillee.dailyzhijiu.ui.adapter.LatestStoryListBaseAdapter;
import cn.neillee.dailyzhijiu.ui.adapter.LatestTopStoryPagerAdapter;
import cn.neillee.dailyzhijiu.ui.story.StoryDetailActivity;
import cn.neillee.dailyzhijiu.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

public class LatestFragment extends BaseFragment<MainFragmentPresenter> implements ObservableScrollViewCallbacks,
        AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, MainFragmentContract.View {

    private static final String LOG_TAG = LatestFragment.class.getSimpleName();

    @BindView(R.id.lv_latest)
    ObservableListView mLvLatest;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;

    private List<TopStoriesBean> mTopStoriesBeanList;
    private List<LatestStory> mLatestStoryList;
    private LatestStoryListBaseAdapter mLatestAdapter;
    private LatestTopStoryPagerAdapter mTopAdapter;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_latest;
    }

    @Override
    protected void initEventAndData() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        FrameLayout mainHeader = (FrameLayout) inflater.inflate(R.layout.header_top_story, null, false);
        mLvLatest.addHeaderView(mainHeader);
        AutoScrollViewPager topStoryViewPager = (AutoScrollViewPager) mainHeader.findViewById(R.id.view_pager);
        topStoryViewPager.startAutoScroll(10000);

        mLvLatest.setScrollViewCallbacks(this);
        mLvLatest.setOnItemClickListener(this);

        mTopStoriesBeanList = new ArrayList<>();
        mLatestStoryList = new ArrayList<>();
        mLatestAdapter = new LatestStoryListBaseAdapter(mContext, mLatestStoryList);
        mTopAdapter = new LatestTopStoryPagerAdapter(mContext, mTopStoriesBeanList);
        mLvLatest.setAdapter(mLatestAdapter);
        topStoryViewPager.setAdapter(mTopAdapter);

        mSrlRefresh.setOnRefreshListener(this);
        // 首次进入刷新
        mSrlRefresh.post(new Runnable() {
            @Override
            public void run() {
                mSrlRefresh.setRefreshing(true);
            }
        });
        mSrlRefresh.setProgressViewOffset(false, 0, DisplayUtil.dip2px(mContext, 24));
        mSrlRefresh.setRefreshing(true);
    }

    @Override
    public void showContent(OriginalStory bean) {
        mSrlRefresh.setRefreshing(false);

        List<LatestStory> latestStoryList = ((LatestStoryListBean) bean).getStories();
        List<TopStoriesBean> topStoriesBeanList = ((LatestStoryListBean) bean).getTopStories();

        mLatestStoryList.clear();
        mTopStoriesBeanList.clear();
        for (int i = 0; i < latestStoryList.size(); i++) {
            mLatestStoryList.add(latestStoryList.get(i));
        }
        for (int i = 0; i < topStoriesBeanList.size(); i++) {
            mTopStoriesBeanList.add(topStoriesBeanList.get(i));
        }
        mLatestAdapter.notifyDataSetChanged();
        mTopAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String errorMsg) {
        mSrlRefresh.setRefreshing(false);
    }

    @Override
    public void refresh(OriginalStory content) {
        showContent(content);
    }

    @Override
    public void onRefresh() {
        mPresenter.getNewsListData(MainFragmentContract.LATEST, "");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LatestStory latestStory = (LatestStory) parent.getAdapter().getItem(position);
        Intent intent = new Intent(mContext, StoryDetailActivity.class);
        intent.putExtra(AtyExtraKeyConstant.STORY_ID, latestStory.getStoryId());
        intent.putExtra(AtyExtraKeyConstant.DEFAULT_IMG_URL, latestStory.getImage());
        mContext.startActivity(intent);
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

    // 标志位，标志已经初始化完成，因为setUserVisibleHint是在onCreateView之前调用的，
    // 在视图未初始化的时候，在lazyLoad当中就使用的话，就会有空指针的异常
    private boolean isPrepared;

    //标志当前页面是否可见
    private boolean isVisible;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // 懒加载
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void lazyLoad() {
        if (!isVisible || !isPrepared) {
            return;
        }
        //getData();//数据请求
        mPresenter.getNewsListData(MainFragmentContract.LATEST, "");
    }

    protected void onInvisible() {
    }
}