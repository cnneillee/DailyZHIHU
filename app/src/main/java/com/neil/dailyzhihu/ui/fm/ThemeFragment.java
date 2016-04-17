package com.neil.dailyzhihu.ui.fm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.github.ksoichiro.android.observablescrollview.ObservableGridView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.UniversalBlockGridView;
import com.neil.dailyzhihu.bean.block.ThemeList;
import com.neil.dailyzhihu.ui.aty.ThemeActivity;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.LoaderFactory;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Neil on 2016/3/23.
 */
public class ThemeFragment extends Fragment implements ObservableScrollViewCallbacks, AdapterView.OnItemClickListener {
    @Bind(R.id.gv_themes)
    ObservableGridView gvThemes;
    private Context mContext;
    private List<ThemeList.OthersBean> mDatas;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_theme, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getContext();
        LoaderFactory.getContentLoader().loadContent(Constant.THEMES, new OnContentLoadingFinishedListener() {
            @Override
            public void onFinish(String content) {
                ThemeList themes = (ThemeList) GsonDecoder.getDecoder().decoding(content, ThemeList.class);
                mDatas = themes.getOthers();
                gvThemes.setAdapter(new UniversalBlockGridView(mContext, mDatas));
            }
        });
        gvThemes.setScrollViewCallbacks(this);
        gvThemes.setOnItemClickListener(this);
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
        ThemeList.OthersBean bean = mDatas.get(position);
        int sectionId = bean.getStoryId();
        Intent intent = new Intent(mContext, ThemeActivity.class);
        intent.putExtra(Constant.THEME_ID, sectionId);
        startActivity(intent);
    }
}
