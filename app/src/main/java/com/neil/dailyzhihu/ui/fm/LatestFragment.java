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

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.google.gson.Gson;
import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.UniversalStoryListAdapter;
import com.neil.dailyzhihu.bean.story.LatestStory;
import com.neil.dailyzhihu.ui.aty.StoryActivity;
import com.neil.dailyzhihu.utils.LoaderFactory;

import java.util.List;

import github.chenupt.springindicator.SpringIndicator;

/**
 * Created by Neil on 2016/3/23.
 */
public class LatestFragment extends Fragment implements ObservableScrollViewCallbacks, View.OnClickListener {
    private Context mContext;
    private ObservableListView lv;
    private List<LatestStory.StoriesBean> mDatas;
    //    private SpringIndicator springIndicator;
    private ViewPager viewPager;
    private SpringIndicator indicator;
    private int pagercurrentidx = -1;
    private List<LatestStory.TopStoriesBean> mTopStoriesBeanList;

    private ViewPager.SimpleOnPageChangeListener mSimpleOnPageChangeListener = new SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            pagercurrentidx = position;
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_latest, container, false);
        lv = (ObservableListView) view.findViewById(R.id.lv_latest);
        FrameLayout header = (FrameLayout) inflater.inflate(R.layout.viewpager_latest, null);
        viewPager = (ViewPager) header.findViewById(R.id.view_pager);
//        indicator = (SpringIndicator) header.findViewById(R.id.indicator);
        lv.addHeaderView(header);
        lv.setScrollViewCallbacks(this);
        viewPager.setOnPageChangeListener(mSimpleOnPageChangeListener);
        viewPager.setOnClickListener(this);
        //springIndicator = (SpringIndicator) view.findViewById(R.id.indicator);
        //viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        //加载数据
        LoaderFactory.getContentLoader().loadContent(Constant.URL_LATEST_NEWS, new OnContentLoadingFinishedListener() {
            @Override
            public void onFinish(String content) {
                Gson gson = new Gson();
                LatestStory latestStory = gson.fromJson(content, LatestStory.class);
                if (latestStory != null) {
                    mDatas = latestStory.getStories();
                    lv.setAdapter(new UniversalStoryListAdapter(mDatas, mContext));
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            LatestStory.StoriesBean storiesBean = (LatestStory.StoriesBean) parent.getAdapter().getItem(position);
                            Intent intent = new Intent(mContext, StoryActivity.class);
                            intent.putExtra(Constant.STORY_ID, storiesBean.getStoryId());
                            mContext.startActivity(intent);
                        }
                    });
                    mTopStoriesBeanList = latestStory.getTop_stories();
                    viewPager.setAdapter(new MyPagerAdapter(mTopStoriesBeanList));
                }
            }
        });
    }

    private View getPagerView(LatestStory.TopStoriesBean topStoryBean) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager, null, false);
        ImageView iv = (ImageView) v.findViewById(R.id.iv_img);
        TextView tv = (TextView) v.findViewById(R.id.tv_title);
        tv.setText(topStoryBean.getTitle());
        LoaderFactory.getImageLoader().displayImage(iv, topStoryBean.getImages().get(0), null);
        return v;
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        Log.e("LOG", "onScrollChanged");
    }

    @Override
    public void onDownMotionEvent() {
        Log.e("LOG", "onDownMotionEvent");
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        Log.e("LOG", "onUpOrCancelMotionEvent");
        AppCompatActivity activity = (AppCompatActivity) mContext;
        ActionBar ab = activity.getSupportActionBar();
        if (ab == null) {
            Log.e("LOG", "AB==null");
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
            LatestStory.TopStoriesBean bean = mTopStoriesBeanList.get(pagercurrentidx);
            int storyId = bean.getStoryId();
            Intent intent = new Intent(mContext, StoryActivity.class);
            intent.putExtra(Constant.STORY_ID, storyId);
            startActivity(intent);
        }
    }

    class MyPagerAdapter extends PagerAdapter {
        private List<LatestStory.TopStoriesBean> listBean;

        public MyPagerAdapter(List<LatestStory.TopStoriesBean> listBean) {
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
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
//            container.removeView(listBean.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
