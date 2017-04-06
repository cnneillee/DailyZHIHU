package com.neil.dailyzhihu.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.neil.dailyzhihu.adapter.LatestStoryListBaseAdapter;
import com.neil.dailyzhihu.mvp.model.http.api.API;
import com.neil.dailyzhihu.mvp.model.bean.orignal.LatestStoryListBean;
import com.neil.dailyzhihu.listener.OnContentLoadedListener;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.LatestTopStoryPagerAdapter;
import com.neil.dailyzhihu.ui.story.StoryDetailActivity;
import com.neil.dailyzhihu.mvp.model.http.api.AtyExtraKeyConstant;
import com.neil.dailyzhihu.utils.DisplayUtil;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.load.LoaderFactory;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

public class LatestFragment extends Fragment implements ObservableScrollViewCallbacks,
        AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String LOG_TAG = LatestFragment.class.getSimpleName();

    // 最新新闻展示（头部为轮播新闻）
    @Bind(R.id.lv_latest)
    ObservableListView mLvLatest;
    // 下滑刷新
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;

    private Context mContext;
    private AutoScrollViewPager mTopStoryViewPager;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_latest, container, false);
        ButterKnife.bind(this, view);
        // 添加顶部新闻轮播
        FrameLayout header = (FrameLayout) inflater.inflate(R.layout.viewpager_top_story, null);
        mTopStoryViewPager = (AutoScrollViewPager) header.findViewById(R.id.view_pager);
        mLvLatest.addHeaderView(header);
        // 设置listview滑动事件监听，以让actionBar隐藏或显示
        mLvLatest.setScrollViewCallbacks(this);
        // 设置刷新事件监听
        mSrlRefresh.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        // 设置新闻项点击事件
        mLvLatest.setOnItemClickListener(this);
        // 首次进入刷新
        mSrlRefresh.post(new Runnable() {
            @Override
            public void run() {
                mSrlRefresh.setRefreshing(true);
            }
        });

        // 自动轮播
        mTopStoryViewPager.startAutoScroll(10000);
        // SwipeRefreshLayout加载时的效果显示
        mSrlRefresh.setProgressViewOffset(false, 0, DisplayUtil.dip2px(mContext, 24));
        mSrlRefresh.setRefreshing(true);
    }

    /**
     * 从网络加载最新新闻
     */
    private void loadLatestNewsFromInternet() {
        LoaderFactory.getContentLoaderVolley().loadContent(API.LATEST_NEWS,
                new OnContentLoadedListener() {
                    @Override
                    public void onSuccess(String content, String url) {
                        Logger.json(content);
                        mSrlRefresh.setRefreshing(false);
                        LatestStoryListBean latestStoryListBean = GsonDecoder.getDecoder().decoding(content, LatestStoryListBean.class);
                        if (latestStoryListBean == null) return;
                        Log.i(LOG_TAG, "LatestStoryListBean loaded:" + latestStoryListBean.getStories().size());
                        List<LatestStoryListBean.LatestStory> latestStoryList = latestStoryListBean.getStories();
                        List<LatestStoryListBean.TopStoriesBean> topStoriesBeanList = latestStoryListBean.getTopStories();
                        LatestStoryListBaseAdapter adapter = new LatestStoryListBaseAdapter(mContext, latestStoryList);
                        mLvLatest.setAdapter(adapter);
                        LatestTopStoryPagerAdapter topAdapter = new LatestTopStoryPagerAdapter(mContext, topStoriesBeanList);
                        mTopStoryViewPager.setAdapter(topAdapter);
                    }
                });
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
    }

    @Override
    public void onDownMotionEvent() {
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    // 点击单条新闻进行跳转
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LatestStoryListBean.LatestStory latestStory = (LatestStoryListBean.LatestStory) parent.getAdapter().getItem(position);
        Intent intent = new Intent(mContext, StoryDetailActivity.class);
        intent.putExtra(AtyExtraKeyConstant.STORY_ID, latestStory.getStoryId());
        intent.putExtra(AtyExtraKeyConstant.DEFAULT_IMG_URL, latestStory.getImage());
        mContext.startActivity(intent);
    }

    @Override
    public void onRefresh() {
        loadLatestNewsFromInternet();
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
        loadLatestNewsFromInternet();
    }

    protected void onInvisible() {
    }
}