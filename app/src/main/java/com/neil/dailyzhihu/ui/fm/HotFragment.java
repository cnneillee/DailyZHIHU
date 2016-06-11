package com.neil.dailyzhihu.ui.fm;

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
import android.widget.TextView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.UniversalStoryListAdapter;
import com.neil.dailyzhihu.bean.CleanDataHelper;
import com.neil.dailyzhihu.bean.cleanlayer.CleanHotStoryListBean;
import com.neil.dailyzhihu.bean.cleanlayer.SimpleStory;
import com.neil.dailyzhihu.bean.orignallayer.HotStory;
import com.neil.dailyzhihu.ui.aty.StoryActivity;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.LoaderFactory;
import com.neil.dailyzhihu.utils.db.catalog.a.DBFactory;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HotFragment extends Fragment implements ObservableScrollViewCallbacks, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String LOG_TAG = HotFragment.class.getSimpleName();

    @Bind(R.id.lv_hot)
    ObservableListView lvHot;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    @Bind(R.id.tv_updateTime)
    TextView mTvUpdateTime;
    private Context mContext;

    private int dbFlag = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        ButterKnife.bind(this, view);
        if (mContext != null) {
//            lvHot.setScrollViewCallbacks(this);
            lvHot.setOnItemClickListener(this);
            mSrlRefresh.setOnRefreshListener(this);
            if (!readDataFromDB()) {
                Log.e(LOG_TAG, "loadDataFromInternet");
                loadDataFromInternet();
            }
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getContext();
    }

    private void loadDataFromInternet() {
        LoaderFactory.getContentLoader().loadContent(Constant.HOT_NEWS, new OnContentLoadingFinishedListener() {
            @Override
            public void onFinish(String content) {
                mSrlRefresh.setRefreshing(false);
                mTvUpdateTime.setText("上次更新于：" + System.currentTimeMillis() + "");
                //原始数据
                HotStory hotStories = (HotStory) GsonDecoder.getDecoder().decoding(content, HotStory.class);
                if (hotStories == null) return;
                //格式化数据
                CleanHotStoryListBean cleanHotStoryListBean = CleanDataHelper.cleanOrignalStory(hotStories);
                if (cleanHotStoryListBean == null) return;
                List<SimpleStory> simpleStoryList = cleanHotStoryListBean.getSimpleStoryList();
                //TODO 在这里可以加入当前所有story的评论加载，写入数据库
                writeIntoDB(simpleStoryList);
                lvHot.setAdapter(new UniversalStoryListAdapter(simpleStoryList, mContext));
            }
        });
    }

    private boolean readDataFromDB() {
        List<SimpleStory> simpleStoryList = DBFactory.getsIDBSpecialSimpleStoryTabledao(mContext).queryAllSimpleStory(dbFlag);
        if (simpleStoryList == null) return false;
        Log.e(LOG_TAG, "simpleStoryList.SIZE:" + simpleStoryList.size());
        if (simpleStoryList.size() >= 0) {
            lvHot.setAdapter(new UniversalStoryListAdapter(simpleStoryList, mContext));
            return true;
        }
        return false;
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
        SimpleStory bean = (SimpleStory) parent.getAdapter().getItem(position);
        Intent intent = new Intent(mContext, StoryActivity.class);
        intent.putExtra(Constant.STORY_ID, bean.getStoryId());
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        Toast.makeText(mContext, "正在更新数据", Toast.LENGTH_SHORT).show();
    }
}
