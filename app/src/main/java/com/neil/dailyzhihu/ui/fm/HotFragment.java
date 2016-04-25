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
import com.neil.dailyzhihu.bean.orignallayer.RecentBean;
import com.neil.dailyzhihu.ui.aty.StoryActivity;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.LoaderFactory;
import com.neil.dailyzhihu.utils.db.catalog.HottestStoryCatalogDBFactory;
import com.neil.dailyzhihu.utils.db.catalog.StoryCatalog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HotFragment extends Fragment implements ObservableScrollViewCallbacks, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String LOG_TAG = HotFragment.class.getSimpleName();

    @Bind(R.id.lv_hot)
    ObservableListView lvHot;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getContext();
        lvHot.setScrollViewCallbacks(this);
        lvHot.setOnItemClickListener(this);
        mSrlRefresh.setOnRefreshListener(this);
//        if (readDataFromDB()) {
//            return;
//        }
        LoaderFactory.getContentLoader().loadContent(Constant.HOT_NEWS, new OnContentLoadingFinishedListener() {
            @Override
            public void onFinish(String content) {
                //原始数据
                HotStory hotStories = (HotStory) GsonDecoder.getDecoder().decoding(content, HotStory.class);
                if (hotStories == null) return;
                //格式化数据
                CleanHotStoryListBean cleanHotStoryListBean = CleanDataHelper.cleanHotStory(hotStories);
                if (cleanHotStoryListBean == null) return;
                List<SimpleStory> simpleStoryList = cleanHotStoryListBean.getSimpleStoryList();
                //TODO 在这里可以加入当前所有story的评论加载，写入数据库
                // writeIntoDB(recentBean);
                lvHot.setAdapter(new UniversalStoryListAdapter(simpleStoryList, mContext));
            }
        });
    }

//    private boolean readDataFromDB() {
//        long currentMillies = System.currentTimeMillis();
//        String yyyyMMdd = new SimpleDateFormat("yyyyMMdd").format(new Date(currentMillies));
////        List<StoryCatalog> catalogList = HottestStoryCatalogDBFactory.getInstance(mContext).queryStoryCatalogByDownloadedDate(yyyyMMdd);
//        List<StoryCatalog> catalogList = HottestStoryCatalogDBFactory.getInstance(mContext).queryAllStoryCatalog();
//        if (catalogList == null) return false;
//        List<RecentBean> recentBean = HottestStoryCatalogDBFactory.convertStoryCatalog2Beans(catalogList);
//        Log.e(LOG_TAG, "recentBean:" + recentBean.size());
//        if (recentBean != null && recentBean.size() >= 0) {
//            lvHot.setAdapter(new UniversalStoryListAdapter(recentBean, mContext));
//            return true;
//        }
//        return false;
//    }

    private int writeIntoDB(List<RecentBean> recentBean) {
        int resultCode = 1;
        if (recentBean != null && mContext != null) {
            for (int i = 0; i < recentBean.size(); i++) {
                int resultCodeFlag = -1;
                String storyId = recentBean.get(i).getStoryId() + "";
                String imageUrl = recentBean.get(i).getThumbnail();
                String title = recentBean.get(i).getTitle();
                StoryCatalog storyCatalog = new StoryCatalog(storyId, title, imageUrl, null, null, null, System.currentTimeMillis() + "");
                resultCodeFlag = (int) HottestStoryCatalogDBFactory.getInstance(mContext).addStoryCatalog(storyCatalog);
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
//        mSrlRefresh.setRefreshing(false);
    }
}
