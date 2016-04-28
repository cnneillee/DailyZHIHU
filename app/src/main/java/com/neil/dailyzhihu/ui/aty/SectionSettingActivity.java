package com.neil.dailyzhihu.ui.aty;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.bean.CleanDataHelper;
import com.neil.dailyzhihu.bean.orignallayer.SectionList;
import com.neil.dailyzhihu.bean.cleanlayer.CleanSectionAndThemeBean;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.LoaderFactory;
import com.neil.dailyzhihu.utils.db.catalog.a.DBFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SectionSettingActivity extends AppCompatActivity {
    private static final String LOG_TAG = SectionSettingActivity.class.getSimpleName();
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.fab)
    FloatingActionButton mFab;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    private boolean flag = false;
    private List<CleanSectionAndThemeBean> mCleanSectionAndThemeBeanList;

    private boolean toAdd = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_setting);
        ButterKnife.bind(this);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag) {
                    flag = true;
                    mRecyclerView.getAdapter().notifyDataSetChanged();
                    mFab.setImageDrawable(getResources().getDrawable(R.drawable.ic_night));
                } else {
                    MyAdapter adapter = (MyAdapter) mRecyclerView.getAdapter();
                    List<Integer> checkedPostionList = adapter.getIsCheckedPostionList();
                    Log.e(LOG_TAG, "checkedPostionList-" + checkedPostionList);
                    long currentMillies = System.currentTimeMillis();
                    for (int i = 0; i < checkedPostionList.size(); i++) {
                        int sectionId = mCleanSectionAndThemeBeanList.get(checkedPostionList.get(i)).getSectionId();
                        long result = DBFactory.getsIDBSubscribSectionTabledao(SectionSettingActivity.this).addSubscribSection(sectionId, currentMillies);
                        Log.e(LOG_TAG, "result-" + result);
                    }
                }
            }
        });
        LoaderFactory.getContentLoader().loadContent(Constant.SECTIONS, new OnContentLoadingFinishedListener() {
            @Override
            public void onFinish(String content) {
                SectionList sectionList = (SectionList) GsonDecoder.getDecoder().decoding(content, SectionList.class);
                List<SectionList.DataBean> mDatas = sectionList.getData();
                mCleanSectionAndThemeBeanList = new ArrayList<>();
                for (int i = 0; i < mDatas.size(); i++) {
                    mCleanSectionAndThemeBeanList.add(CleanDataHelper.convertDataBean2CleanSectionBean(mDatas.get(i)));
                }
                Log.e(LOG_TAG, "mDatas=" + mDatas.size() + " mCleanSectionAndThemeBeanList:" + mCleanSectionAndThemeBeanList.size());
                MyAdapter myAdapter = new MyAdapter(SectionSettingActivity.this, mCleanSectionAndThemeBeanList);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(SectionSettingActivity.this, 2);
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setAdapter(myAdapter);
            }
        });
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        List<CleanSectionAndThemeBean> data;
        Context mContext;
        List<Integer> isCheckedPostionList = new ArrayList<>();

        MyAdapter(Context context, List<CleanSectionAndThemeBean> cleanSectionAndThemeBean) {
            data = cleanSectionAndThemeBean;
            this.mContext = context;
        }

        public List<Integer> getIsCheckedPostionList() {
            return isCheckedPostionList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_gv_universal_block, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.mIvTitle.setText(data.get(position).getName());
            LoaderFactory.getImageLoader().displayImage(holder.mIvImg, data.get(position).getThumbnail(), null);
            holder.mTvDescribsion.setText(data.get(position).getDescription());
            holder.mCheckBox.setTag(position);
            if (isCheckedPostionList.contains(new Integer(position)))
                holder.mCheckBox.setChecked(true);
            else holder.mCheckBox.setChecked(false);
            holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int position = (int) buttonView.getTag();
                    if (isChecked) {
                        if (!isCheckedPostionList.contains(new Integer(position)))
                            isCheckedPostionList.add(new Integer(position));
                    } else {
                        if (isCheckedPostionList.contains(new Integer(position)))
                            isCheckedPostionList.remove(new Integer(position));
                    }
                }
            });
            if (flag)
                holder.mCheckBox.setVisibility(View.VISIBLE);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView mIvTitle;
            ImageView mIvImg;
            TextView mTvDescribsion;
            CheckBox mCheckBox;

            public MyViewHolder(View itemView) {
                super(itemView);
                mIvTitle = (TextView) itemView.findViewById(R.id.iv_title);
                mIvImg = (ImageView) itemView.findViewById(R.id.iv_img);
                mTvDescribsion = (TextView) itemView.findViewById(R.id.tv_describsion);
                mCheckBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            }
        }
    }
}