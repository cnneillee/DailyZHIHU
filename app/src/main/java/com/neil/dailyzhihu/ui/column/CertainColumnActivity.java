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
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.neil.dailyzhihu.adapter.ColumnStoryListBaseAdapter;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.mvp.model.bean.orignal.ColumnStoryListBean;
import com.neil.dailyzhihu.mvp.presenter.ColumnDetailPresenter;
import com.neil.dailyzhihu.mvp.presenter.constract.ColumnDetailContract;
import com.neil.dailyzhihu.ui.story.StoryDetailActivity;
import com.neil.dailyzhihu.ui.widget.BaseActivity;
import com.neil.dailyzhihu.mvp.model.http.api.AtyExtraKeyConstant;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CertainColumnActivity extends BaseActivity implements
        AdapterView.OnItemClickListener, ColumnDetailContract.View {

    private static final String LOG_TAG = CertainColumnActivity.class.getSimpleName();

    @Bind(R.id.list)
    ListView mListView;
    @Bind(R.id.toolbar)
    Toolbar mToolbarView;

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
        setContentView(R.layout.activity_column_detail);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbarView);
        mToolbarView.setNavigationIcon(R.drawable.ic_action_back);
        mToolbarView.setNavigationOnClickListener(upBtnListener);

        mListView = (ListView) findViewById(R.id.list);

        getExtras();
        if (sectionId > 0) {
            ColumnDetailPresenter presenter = new ColumnDetailPresenter(this);
            presenter.getColumnDetailData(sectionId);
        }
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

    private void setActionBarText() {
        ActionBar ab = getSupportActionBar();
        if (ab == null || sectionId < 0)
            return;
        //sectionName也可以从当前的sectionStoryList中取得
        ab.setTitle(getResources().getString(R.string.activity_column) + " · " + sectionName);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ColumnStoryListBean.ColumnStory bean = (ColumnStoryListBean.ColumnStory) parent.getAdapter().getItem(position);
        int storyId = bean.getStoryId();
        String imgUrl = (bean.getImage() == null) ? sectionImg : bean.getImage();
        Intent intent = new Intent(CertainColumnActivity.this, StoryDetailActivity.class);
        intent.putExtra(AtyExtraKeyConstant.STORY_ID, storyId);
        intent.putExtra(AtyExtraKeyConstant.DEFAULT_IMG_URL, imgUrl);
        CertainColumnActivity.this.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    public void setPresenter(ColumnDetailContract.Presenter presenter) {

    }

    @Override
    public void showContent(ColumnStoryListBean bean) {
        ColumnStoryListBaseAdapter adapter = new ColumnStoryListBaseAdapter(CertainColumnActivity.this, bean);
        adapter.setDefaultImgUrl(sectionImg);
        mListView.setAdapter(adapter);
        setActionBarText();
    }
}
