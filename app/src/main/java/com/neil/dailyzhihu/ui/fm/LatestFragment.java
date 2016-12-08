package com.neil.dailyzhihu.ui.fm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.HotNewsRecyclerAdapter;
import com.neil.dailyzhihu.adapter.UniversalStoryListAdapter;
import com.neil.dailyzhihu.bean.CleanDataHelper;
import com.neil.dailyzhihu.bean.cleanlayer.CleanHotStoryListBean;
import com.neil.dailyzhihu.bean.cleanlayer.CleanLatestStoryListBean;
import com.neil.dailyzhihu.bean.cleanlayer.CleanStoryListBean;
import com.neil.dailyzhihu.bean.cleanlayer.SimpleStory;
import com.neil.dailyzhihu.bean.cleanlayer.TopStory;
import com.neil.dailyzhihu.bean.listener.SimpleOnContentLoadingListener;
import com.neil.dailyzhihu.bean.orignallayer.HotStory;
import com.neil.dailyzhihu.bean.orignalloader.OrignalLoaderFactory;
import com.neil.dailyzhihu.ui.aty.StoryActivity;
import com.neil.dailyzhihu.utils.DisplayUtil;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.LoaderFactory;
import com.neil.dailyzhihu.utils.cnt.UniversalContentLoaderTest;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

public class LatestFragment extends Fragment implements ObservableScrollViewCallbacks, View.OnClickListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String LOG_TAG = LatestFragment.class.getSimpleName();

    // 最新新闻展示（头部为轮播新闻）
    @Bind(R.id.lv_latest)
    ObservableListView mLvLatest;
    // 下滑刷新
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    @Bind(R.id.rv_hotest)
    RecyclerView mRvHotest;

    private Context mContext;
    private List<SimpleStory> mDatas;
    private AutoScrollViewPager mTopStoryViewPager;
    private int pagercurrentidx = -1;
    private List<TopStory> mTopStoriesBeanList;
    private int dbFlag = 0;

    private SimpleOnPageChangeListener mSimpleOnPageChangeListener = new SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            pagercurrentidx = position;
        }
    };

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_latest, container, false);
        ButterKnife.bind(this, view);
        // 添加顶部新闻轮播
        FrameLayout header = (FrameLayout) inflater.inflate(R.layout.viewpager_top_story, null);
        mTopStoryViewPager = (AutoScrollViewPager) header.findViewById(R.id.view_pager);
        mLvLatest.addHeaderView(header);
        // 添加顶部新闻轮播点击事件
        mTopStoryViewPager.setOnPageChangeListener(mSimpleOnPageChangeListener);
        mTopStoryViewPager.setOnClickListener(this);
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
        // 加载
        loadLatestNewsFromInternet();
        loadHotNewsFromInternet();
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
        OrignalLoaderFactory.getContentLoaderVolley().loadCleanStoryListBean(mContext, Constant.URL_LATEST_NEWS, new SimpleOnContentLoadingListener() {
            @Override
            public void onFinish(CleanStoryListBean cleanStoryListBean) {
                CleanLatestStoryListBean cleanLatestStoryListBean = (CleanLatestStoryListBean) cleanStoryListBean;
                if (cleanLatestStoryListBean == null) return;
                mDatas = cleanLatestStoryListBean.getSimpleStoryList();
                mLvLatest.setAdapter(new UniversalStoryListAdapter(mDatas, mContext));
                mTopStoriesBeanList = cleanLatestStoryListBean.getTopStoryList();
                mTopStoryViewPager.setAdapter(new MyPagerAdapter(mTopStoriesBeanList));
                mTopStoryViewPager.getAdapter().notifyDataSetChanged();
                mSrlRefresh.setRefreshing(false);
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(mContext, "ERROR!!", Toast.LENGTH_SHORT).show();
                mSrlRefresh.setRefreshing(false);
            }
        }, UniversalContentLoaderTest.Flag.LATEST);
    }

    /**
     * 从网络加载最新新闻
     */
    private void loadHotNewsFromInternet() {
        LoaderFactory.getContentLoader().loadContent(Constant.URL_HOT_NEWS, new OnContentLoadingFinishedListener() {
            @Override
            public void onFinish(String content) {
                mSrlRefresh.setRefreshing(false);
                //原始数据
                HotStory hotStories = GsonDecoder.getDecoder().decoding(content, HotStory.class);
                //格式化数据
                CleanHotStoryListBean cleanHotStoryListBean = CleanDataHelper.cleanOrignalStory(hotStories);
                if (cleanHotStoryListBean == null) return;
                List<SimpleStory> simpleStoryList = cleanHotStoryListBean.getSimpleStoryList();
                mRvHotest.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, true));
                mRvHotest.setAdapter(new HotNewsRecyclerAdapter(simpleStoryList, mContext));
            }
        });
    }

    /**
     * 获得顶部轮播单页页卡
     *
     * @param topStory 顶部单页内容
     * @return 单页页卡
     */
    private View getPagerView(TopStory topStory) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager_top_story, null, false);
        ImageView iv = (ImageView) v.findViewById(R.id.iv_img);
        TextView tv = (TextView) v.findViewById(R.id.tv_title);
        tv.setText(topStory.getTitle());
        LoaderFactory.getImageLoader().displayImage(iv, topStory.getImageUrl(), null);
        return v;
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
    public void onClick(View v) {
        if (mTopStoriesBeanList != null && pagercurrentidx >= 0) {
            TopStory bean = mTopStoriesBeanList.get(pagercurrentidx);
            int storyId = bean.getStoryId();
            Intent intent = new Intent(mContext, StoryActivity.class);
            intent.putExtra(Constant.STORY_ID, storyId);
            startActivity(intent);
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
        SimpleStory simpleStory = (SimpleStory) parent.getAdapter().getItem(position);
        Intent intent = new Intent(mContext, StoryActivity.class);
        intent.putExtra(Constant.STORY_ID, simpleStory.getStoryId());
        mContext.startActivity(intent);
    }

    @Override
    public void onRefresh() {
        loadLatestNewsFromInternet();
        loadHotNewsFromInternet();
    }

    /**
     * top viewpager（顶部轮播新闻）
     */
    private class MyPagerAdapter extends PagerAdapter {
        private List<TopStory> listBean;

        MyPagerAdapter(List<TopStory> listBean) {
            this.listBean = listBean;
        }

        @Override
        public int getCount() {
            if (listBean != null)
                return listBean.size();
            return 0;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = getPagerView(listBean.get(position));
            view.setTag(listBean.get(position));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
