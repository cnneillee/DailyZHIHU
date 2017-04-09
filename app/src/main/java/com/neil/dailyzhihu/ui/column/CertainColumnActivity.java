package com.neil.dailyzhihu.ui.column;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.ColumnStoryListBaseAdapter;
import com.neil.dailyzhihu.base.BaseActivity;
import com.neil.dailyzhihu.mvp.model.bean.orignal.ColumnStoryListBean;
import com.neil.dailyzhihu.mvp.model.http.api.AtyExtraKeyConstant;
import com.neil.dailyzhihu.mvp.presenter.ColumnDetailPresenter;
import com.neil.dailyzhihu.mvp.presenter.constract.ColumnDetailContract;
import com.neil.dailyzhihu.ui.story.StoryDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CertainColumnActivity extends BaseActivity<ColumnDetailPresenter> implements
        AdapterView.OnItemClickListener, ColumnDetailContract.View {

    private static final String LOG_TAG = CertainColumnActivity.class.getSimpleName();

    @BindView(R.id.list)
    ListView mListView;
    @BindView(R.id.toolbar)
    Toolbar mToolbarView;

    private int sectionId = -1;
    private String sectionName = null;
    private String sectionImg = null;

    private ColumnStoryListBaseAdapter mColumnStoryListBaseAdapter;
    private List<ColumnStoryListBean.ColumnStory> mColumnStoryList;

    @Override
    protected void initEventAndData() {
        setToolbar(mToolbarView, "");
        mListView = (ListView) findViewById(R.id.list);
        mListView.setOnItemClickListener(this);
        getExtras();
        mColumnStoryList = new ArrayList<>();
        mColumnStoryListBaseAdapter = new ColumnStoryListBaseAdapter(this, mColumnStoryList);
        mListView.setAdapter(mColumnStoryListBaseAdapter);
        if (sectionId > 0) {
            mPresenter.getColumnDetailData(sectionId);
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_column_detail;
    }

    @Override
    public void showContent(ColumnStoryListBean bean) {
        mColumnStoryList.clear();
        for (int i = 0; i < bean.getStories().size(); i++) {
            mColumnStoryList.add(bean.getStories().get(i));
        }
        mColumnStoryListBaseAdapter.notifyDataSetChanged();
        setActionBarText();
    }

    @Override
    public void showError(String errMsg) {
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
}
