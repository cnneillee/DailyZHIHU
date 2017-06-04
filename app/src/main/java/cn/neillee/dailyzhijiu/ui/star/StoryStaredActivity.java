package cn.neillee.dailyzhijiu.ui.star;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import cn.neillee.dailyzhijiu.R;
import cn.neillee.dailyzhijiu.base.BaseActivity;
import cn.neillee.dailyzhijiu.base.BaseReecyclerViewAdapter;
import cn.neillee.dailyzhijiu.model.db.StarRecord;
import cn.neillee.dailyzhijiu.model.http.api.AtyExtraKeyConstant;
import cn.neillee.dailyzhijiu.presenter.StoryStaredPresenter;
import cn.neillee.dailyzhijiu.presenter.constract.StoryStaredContract;
import cn.neillee.dailyzhijiu.ui.adapter.StarRecordRecyclerAdapter;
import cn.neillee.dailyzhijiu.ui.story.StoryDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
        mAdapter.setOnItemClickListener(new BaseReecyclerViewAdapter.OnItemClickListener<StarRecordRecyclerAdapter.ViewHolder>() {
            @Override
            public void onItemClick(StarRecordRecyclerAdapter.ViewHolder holder, int position, long id) {
                Intent intent = new Intent(StoryStaredActivity.this, StoryDetailActivity.class);
                StarRecord record = mStarRecords.get(position);
                intent.putExtra(AtyExtraKeyConstant.STORY_ID, record.getStoryId());
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
            boolean unstared = data.getBooleanExtra(AtyExtraKeyConstant.UNSTARED, false);
            int storyId = data.getIntExtra(AtyExtraKeyConstant.STORY_ID, 0);
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
