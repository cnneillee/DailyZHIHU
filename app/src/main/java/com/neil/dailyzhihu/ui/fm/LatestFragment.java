package com.neil.dailyzhihu.ui.fm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.google.gson.Gson;
import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.UniversalStoryListAdapter;
import com.neil.dailyzhihu.bean.CleanDataHelper;
import com.neil.dailyzhihu.bean.cleanlayer.CleanLatestStoryListBean;
import com.neil.dailyzhihu.bean.cleanlayer.SimpleStory;
import com.neil.dailyzhihu.bean.cleanlayer.TopStory;
import com.neil.dailyzhihu.bean.orignallayer.LatestStory;
import com.neil.dailyzhihu.ui.aty.StoryActivity;
import com.neil.dailyzhihu.utils.LoaderFactory;
import com.neil.dailyzhihu.utils.db.catalog.a.DBFactory;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LatestFragment extends Fragment implements ObservableScrollViewCallbacks, View.OnClickListener, AdapterView.OnItemClickListener {
    private static final String LOG_TAG = "LatestFragment";
    @Bind(R.id.lv_latest)
    ObservableListView mLvLatest;

    private Context mContext;
    private List<SimpleStory> mDatas;
    private ViewPager mTopStoryViewPager;
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
        FrameLayout header = (FrameLayout) inflater.inflate(R.layout.viewpager_top_story, null);
        mTopStoryViewPager = (ViewPager) header.findViewById(R.id.view_pager);
        mLvLatest.addHeaderView(header);
        mLvLatest.setScrollViewCallbacks(this);
        mTopStoryViewPager.setOnPageChangeListener(mSimpleOnPageChangeListener);
        mTopStoryViewPager.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        mLvLatest.setOnItemClickListener(this);
        if (readDataFromDB()) {
            return;
        }
        //加载数据
        LoaderFactory.getContentLoader().loadContent(Constant.URL_LATEST_NEWS, new OnContentLoadingFinishedListener() {
            @Override
            public void onFinish(String content) {
                Gson gson = new Gson();
                LatestStory latestStory = gson.fromJson(content, LatestStory.class);
                if (latestStory != null) {
                    CleanLatestStoryListBean cleanLatestStoryListBean = CleanDataHelper.cleanLatestStory(latestStory);
                    mDatas = cleanLatestStoryListBean.getSimpleStoryList();
                    //TODO 在这里可以加入当前所有story的评论加载，写入数据库
                    writeIntoDB(mDatas);
                    mLvLatest.setAdapter(new UniversalStoryListAdapter(mDatas, mContext));
                    mTopStoriesBeanList = cleanLatestStoryListBean.getTopStoryList();
                    Log.e(LOG_TAG, "mTopStoriesBeanList" + mTopStoriesBeanList.size());
                    mTopStoryViewPager.setAdapter(new MyPagerAdapter(mTopStoriesBeanList));
                }
            }
        });
    }

    private int writeIntoDB(List<SimpleStory> simpleStoryList) {
        int resultCode = 1;
        if (simpleStoryList != null && mContext != null) {
            for (int i = 0; i < simpleStoryList.size(); i++) {
                SimpleStory simpleStory = simpleStoryList.get(i);
                int resultCodeFlag = (int) DBFactory.getsIDBSpecialSimpleStoryTabledao(mContext).addSimpleStory(simpleStory, dbFlag);
                Log.e(LOG_TAG, "resultCodeFlag:" + resultCodeFlag);
                if (resultCodeFlag < 0)
                    resultCode = resultCodeFlag;
            }
        }
        if (resultCode >= 0)
            Toast.makeText(mContext, "数据成功", Toast.LENGTH_SHORT).show();
        return resultCode;
    }

    private boolean readDataFromDB() {
        List<SimpleStory> simpleStoryList = DBFactory.getsIDBSpecialSimpleStoryTabledao(mContext).queryAllSimpleStory(dbFlag);
        if (simpleStoryList == null) return false;
        Log.e(LOG_TAG, "simpleStoryList.SIZE:" + simpleStoryList.size());
        if (simpleStoryList != null && simpleStoryList.size() >= 0) {
            mLvLatest.setAdapter(new UniversalStoryListAdapter(simpleStoryList, mContext));
            return true;
        }
        return false;
    }

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SimpleStory simpleStory = (SimpleStory) parent.getAdapter().getItem(position);
        Intent intent = new Intent(mContext, StoryActivity.class);
        intent.putExtra(Constant.STORY_ID, simpleStory.getStoryId());
        mContext.startActivity(intent);
    }

    class MyPagerAdapter extends PagerAdapter {
        private List<TopStory> listBean;

        public MyPagerAdapter(List<TopStory> listBean) {
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
