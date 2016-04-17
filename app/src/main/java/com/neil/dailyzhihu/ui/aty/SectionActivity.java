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

package com.neil.dailyzhihu.ui.aty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.UniversalStoryListAdapter;
import com.neil.dailyzhihu.bean.story.SectionStoryList;
import com.neil.dailyzhihu.ui.widget.BaseActivity;
import com.neil.dailyzhihu.utils.Formater;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.LoaderFactory;
import com.nineoldandroids.view.ViewHelper;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SectionActivity extends BaseActivity implements ObservableScrollViewCallbacks, AdapterView.OnItemClickListener {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbarView);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.primary)));

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
        if (sectionId > 0)
            fillContent();
        mListView.setOnItemClickListener(this);
    }

    private int getExtras() {
        if (getIntent().getExtras() != null) {
            sectionId = getIntent().getIntExtra(Constant.SECTION_ID, -2);
            sectionName = getIntent().getStringExtra(Constant.SECTION_NAME);
        }
        return sectionId;
    }

    private void fillContent() {
        LoaderFactory.getContentLoader().loadContent(Formater.formatUrl(Constant.SECTIONS_HEAD, sectionId), new OnContentLoadingFinishedListener() {
            @Override
            public void onFinish(String content) {
                SectionStoryList sectionStoryList = (SectionStoryList) GsonDecoder.getDecoder().decoding(content, SectionStoryList.class);
//                tvTimestamp.setText(sectionStoryList.getTimestamp() + "");
                //图片过小，就不显示了
                //LoaderFactory.getImageLoader().displayImage(mImageView, sectionBGImgUrl, null);
                mListView.setAdapter(new UniversalStoryListAdapter(sectionStoryList.getStories(), SectionActivity.this));
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
        SectionStoryList.StoriesBean bean = (SectionStoryList.StoriesBean) parent.getAdapter().getItem(position);
        int storyId = bean.getStoryId();
        Intent intent = new Intent(SectionActivity.this, StoryActivity.class);
        intent.putExtra(Constant.STORY_ID, storyId);
        SectionActivity.this.startActivity(intent);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(mListView.getCurrentScrollY(), false, false);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.primary);
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
}
