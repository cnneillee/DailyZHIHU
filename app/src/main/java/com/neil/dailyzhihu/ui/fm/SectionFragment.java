package com.neil.dailyzhihu.ui.fm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableGridView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.UniversalBlockGridAdapter;
import com.neil.dailyzhihu.bean.cleanlayer.CleanSectionAndThemeBean;
import com.neil.dailyzhihu.ui.aty.SectionActivity;
import com.neil.dailyzhihu.ui.aty.SectionSettingActivity;
import com.neil.dailyzhihu.utils.db.catalog.a.DBFactory;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SectionFragment extends Fragment implements ObservableScrollViewCallbacks, AdapterView.OnItemClickListener, View.OnClickListener {
    private static final String LOG_TAG = SectionFragment.class.getSimpleName();
    @Bind(R.id.gv_sections)
    ObservableGridView gvSections;
    @Bind(R.id.tv)
    TextView tv;
    private List<CleanSectionAndThemeBean> mDatas;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_section, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getContext();
        mDatas = DBFactory.getsIDBSubscribSectionTabledao(mContext).queryAllSubscribSection();
        View header = LayoutInflater.from(mContext).inflate(R.layout.section_listview_header, null, false);
        gvSections.addHeaderView(header);
        gvSections.setAdapter(new UniversalBlockGridAdapter<>(mContext, mDatas));
        Log.e(LOG_TAG, " mDatas.size()-》" + mDatas.size());
        header.setOnClickListener(this);
        gvSections.setScrollViewCallbacks(this);
        gvSections.setOnItemClickListener(this);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intnt = new Intent(mContext, SectionSettingActivity.class);
                mContext.startActivity(intnt);
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
        CleanSectionAndThemeBean bean = mDatas.get(position);
        int sectionId = bean.getSectionId();
        String sectionName = bean.getName();
        Intent intent = new Intent(mContext, SectionActivity.class);
        intent.putExtra(Constant.SECTION_ID, sectionId);
        intent.putExtra(Constant.SECTION_NAME, sectionName);
        startActivity(intent);
    }

    //header被点击
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, SectionSettingActivity.class);
        mContext.startActivity(intent);
    }
}
