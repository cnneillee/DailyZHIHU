package com.neil.dailyzhihu.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.neil.dailyzhihu.api.API;
import com.neil.dailyzhihu.bean.orignallayer.RecentBean;
import com.neil.dailyzhihu.listener.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.HotStoryListAdapter;
import com.neil.dailyzhihu.bean.orignallayer.HotStory;
import com.neil.dailyzhihu.ui.story.CertainStoryActivity;
import com.neil.dailyzhihu.api.AtyExtraKeyConstant;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.load.LoaderFactory;
import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HotFragment extends Fragment implements ObservableScrollViewCallbacks,
        AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String LOG_TAG = HotFragment.class.getSimpleName();

    @Bind(R.id.lv_hot)
    ObservableListView lvHot;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;

    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 加载
        loadDataFromInternet();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        ButterKnife.bind(this, view);
//            lvHot.setScrollViewCallbacks(this);
        lvHot.setOnItemClickListener(this);
        mSrlRefresh.setOnRefreshListener(this);
        mSrlRefresh.setRefreshing(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getContext();
    }

    private void loadDataFromInternet() {
        LoaderFactory.getContentLoader().loadContent(API.HOT_NEWS,
                new OnContentLoadingFinishedListener() {
            @Override
            public void onFinish(String content) {
                Logger.json(content);
                mSrlRefresh.setRefreshing(false);
                HotStory hotStories = GsonDecoder.getDecoder().decoding(content, HotStory.class);
                HotStoryListAdapter adapter = new HotStoryListAdapter(mContext,hotStories);
                lvHot.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RecentBean bean = (RecentBean) parent.getAdapter().getItem(position);
        Intent intent = new Intent(mContext, CertainStoryActivity.class);
        intent.putExtra(AtyExtraKeyConstant.STORY_ID, bean.getStoryId());
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        Toast.makeText(mContext, "正在更新数据", Toast.LENGTH_SHORT).show();
        loadDataFromInternet();
    }
}
