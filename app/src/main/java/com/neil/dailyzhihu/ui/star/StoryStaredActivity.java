package com.neil.dailyzhihu.ui.star;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.base.BaseActivity;
import com.neil.dailyzhihu.base.BaseReecyclerViewAdapter;
import com.neil.dailyzhihu.model.db.StarRecord;
import com.neil.dailyzhihu.model.http.api.AtyExtraKeyConstant;
import com.neil.dailyzhihu.presenter.StoryStaredPresenter;
import com.neil.dailyzhihu.presenter.constract.StoryStaredContract;
import com.neil.dailyzhihu.ui.adapter.StarRecordRecyclerAdapter;
import com.neil.dailyzhihu.ui.story.StoryDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.neil.dailyzhihu.model.http.api.AtyExtraKeyConstant.STORY_ID;
import static com.neil.dailyzhihu.model.http.api.AtyExtraKeyConstant.UNSTARED;

/**
 * 作者：Neil on 2017/6/1 14:32.
 * 邮箱：cn.neillee@gmail.com
 */

public class StoryStaredActivity extends BaseActivity<StoryStaredPresenter>
        implements StoryStaredContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.empty_layout)
    LinearLayout mEmptyLayout;
    @BindView(R.id.rv_stared_story)
    RecyclerView mRVStaredStory;
    private List<StarRecord> mStarRecords;
    private StarRecordRecyclerAdapter mAdapter;

    public static final int REQUEST_CODE = 1;

    @Override
    protected void initEventAndData() {
        setToolbar(mToolbar, getResources().getString(R.string.activity_star));
        mStarRecords = new ArrayList<>();
        mAdapter = new StarRecordRecyclerAdapter(this, mStarRecords);
        mAdapter.setOnItemClickListener(new BaseReecyclerViewAdapter
                .OnItemClickListener<StarRecordRecyclerAdapter.ViewHolder>() {
            @Override
            public void onItemClick(StarRecordRecyclerAdapter.ViewHolder holder, int position, long id) {
                Intent intent = new Intent(StoryStaredActivity.this, StoryDetailActivity.class);
                StarRecord record = mStarRecords.get(position);
                intent.putExtra(STORY_ID, record.getStoryId());
                intent.putExtra(AtyExtraKeyConstant.DEFAULT_IMG_URL, record.getImage());
                mContext.startActivityForResult(intent, REQUEST_CODE);
            }
        });
        mRVStaredStory.setLayoutManager(new LinearLayoutManager(this));
        mRVStaredStory.setAdapter(mAdapter);
        mPresenter.getStaredStory();
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_story_stared;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            boolean unstared = data.getBooleanExtra(UNSTARED, false);
            int storyId = data.getIntExtra(STORY_ID, 0);
            if (unstared && storyId != 0) {
                StarRecord toDelete = null;
                for (StarRecord record : mStarRecords)
                    if (record.getStoryId() == storyId) toDelete = record;
                if (toDelete != null) {
                    mStarRecords.remove(toDelete);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void showEmptyStared() {
        mEmptyLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showStaredRecord(List<StarRecord> recordList) {
        mStarRecords.clear();
        mStarRecords.addAll(recordList);
        mAdapter.notifyDataSetChanged();
        mEmptyLayout.setVisibility(View.GONE);
    }
}
