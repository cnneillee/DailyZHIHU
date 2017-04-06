package com.neil.dailyzhihu.ui.story;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.CommentTypesPagerAdapter;
import com.neil.dailyzhihu.mvp.model.http.api.AtyExtraKeyConstant;
import com.neil.dailyzhihu.ui.NightModeBaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：Neil on 2017/2/19 21:45.
 * 邮箱：cn.neillee@gmail.com
 */

public class CertainStoryCommentActivity extends NightModeBaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.view_pager_comments)
    ViewPager mViewPagerComments;
    private CommentTypesPagerAdapter mViewPagerAdapter;

    private int mStoryId;
    private Activity mContext = CertainStoryCommentActivity.this;

    private View.OnClickListener upBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mContext.onBackPressed();
        }
    };

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_story_comment);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_action_cancel);
        mToolbar.setNavigationOnClickListener(upBtnListener);

        loadIntentExtras();
        initViewPager();
    }

    private void initViewPager() {

        mViewPagerAdapter = new CommentTypesPagerAdapter(mContext, mStoryId + "");
        mViewPagerComments.setAdapter(mViewPagerAdapter);

        mViewPagerComments.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                mToolbar.setSubtitle(position == 0 ? CommentTypesPagerAdapter.CommentType.LONG.nameRes
                        : CommentTypesPagerAdapter.CommentType.SHORT.nameRes);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    public void loadIntentExtras() {
        if (getIntent() == null) return;
        mStoryId = getIntent().getIntExtra(AtyExtraKeyConstant.STORY_ID, -1);
    }
}
