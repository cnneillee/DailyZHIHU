/*
 * Copyright 2014 Soichiro Kashima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.neil.dailyzhihu.ui.column;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.neil.dailyzhihu.adapter.ColumnStoryListBaseAdapter;
import com.neil.dailyzhihu.api.API;
import com.neil.dailyzhihu.listener.OnContentLoadedListener;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.bean.orignal.ColumnStoryListBean;
import com.neil.dailyzhihu.ui.story.CertainStoryActivity;
import com.neil.dailyzhihu.ui.widget.BaseActivity;
import com.neil.dailyzhihu.api.AtyExtraKeyConstant;
import com.neil.dailyzhihu.utils.Formater;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.load.LoaderFactory;
import com.nineoldandroids.view.ViewHelper;
import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CertainColumnActivity extends BaseActivity implements ObservableScrollViewCallbacks, AdapterView.OnItemClickListener {

    private static final String LOG_TAG = CertainColumnActivity.class.getSimpleName();

    @Bind(R.id.image)
    ImageView mImageView;
    // mListBackgroundView makes ListView's background except header view.
    @Bind(R.id.list_background)
    View mListBackgroundView;
    @Bind(R.id.list)
    ObservableListView mListView;
    @Bind(R.id.toolbar)
    Toolbar mToolbarView;

    private int mParallaxImageHeight;

    private int sectionId = -1;
    private String sectionName = null;
    private String sectionImg = null;
    private View.OnClickListener upBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_section);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbarView);
        mToolbarView.setNavigationIcon(R.drawable.ic_action_back);
        mToolbarView.setNavigationOnClickListener(upBtnListener);
        TypedValue typedValue = new TypedValue();
        this.getTheme().resolveAttribute(R.attr.barBgColor, typedValue, true);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, typedValue.data));

        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);

        mListView = (ObservableListView) findViewById(R.id.list);
        mListView.setScrollViewCallbacks(this);
        // Set padding view for ListView. This is the flexible space.
        View paddingView = new View(this);
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                mParallaxImageHeight);
        paddingView.setLayoutParams(lp);

        // This is required to disable header's list selector effect
        paddingView.setClickable(true);

        mListView.addHeaderView(paddingView);
        setDummyData(mListView);

        getExtras();
        if (sectionId > 0) fillContent();
        mListView.setOnItemClickListener(this);
    }

    private int getExtras() {
        if (getIntent().getExtras() != null) {
            sectionId = getIntent().getIntExtra(AtyExtraKeyConstant.SECTION_ID, -2);
            sectionName = getIntent().getStringExtra(AtyExtraKeyConstant.SECTION_NAME);
            sectionImg = getIntent().getStringExtra(AtyExtraKeyConstant.DEFAULT_IMG_URL);
        }
        return sectionId;
    }

    private void fillContent() {
        LoaderFactory.getContentLoader().loadContent(Formater.formatUrl(API.SECTION_PREFIX, sectionId),
                new OnContentLoadedListener() {
                    @Override
                    public void onSuccess(String content, String url) {
                        Logger.json(content);
                        ColumnStoryListBean columnStoryListBean = GsonDecoder.getDecoder().decoding(content, ColumnStoryListBean.class);
                        ColumnStoryListBaseAdapter adapter = new ColumnStoryListBaseAdapter(CertainColumnActivity.this, columnStoryListBean);
                        adapter.setDefaultImgUrl(sectionImg);
                        mListView.setAdapter(adapter);
                    }
                });
        setActionBarText();
    }

    private void setActionBarText() {
        ActionBar ab = getSupportActionBar();
        if (ab == null || sectionId < 0)
            return;
        //sectionName也可以从当前的sectionStoryList中取得
        ab.setTitle("栏目纵览" + "·" + sectionName);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ColumnStoryListBean.ColumnStory bean = (ColumnStoryListBean.ColumnStory) parent.getAdapter().getItem(position);
        int storyId = bean.getStoryId();
        String imgUrl = (bean.getImage() == null) ? sectionImg : bean.getImage();
        Intent intent = new Intent(CertainColumnActivity.this, CertainStoryActivity.class);
        intent.putExtra(AtyExtraKeyConstant.STORY_ID, storyId);
        intent.putExtra(AtyExtraKeyConstant.DEFAULT_IMG_URL, imgUrl);
        CertainColumnActivity.this.startActivity(intent);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(mListView.getCurrentScrollY(), false, false);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.ZHIHUBlue);
        float alpha = Math.min(1, (float) scrollY / mParallaxImageHeight);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        ViewHelper.setTranslationY(mImageView, -scrollY / 2);

        // Translate list background
        ViewHelper.setTranslationY(mListBackgroundView, Math.max(0, -scrollY + mParallaxImageHeight));
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
