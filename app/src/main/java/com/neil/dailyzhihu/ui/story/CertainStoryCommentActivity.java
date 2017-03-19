package com.neil.dailyzhihu.ui.story;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewStubCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.CommentPagerAdapter;
import com.neil.dailyzhihu.adapter.LongCommentListAdapter;
import com.neil.dailyzhihu.api.API;
import com.neil.dailyzhihu.bean.orignallayer.LongComment;
import com.neil.dailyzhihu.listener.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.api.AtyExtraKeyConstant;
import com.neil.dailyzhihu.ui.NightModeBaseActivity;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.load.LoaderFactory;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

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
    private CommentPagerAdapter mViewPagerAdapter;

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
        setContentView(R.layout.activity_certain_story_comment);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_action_cancel);
        mToolbar.setNavigationOnClickListener(upBtnListener);

        loadIntentExtras();
        initViewPager();
    }

    private void initViewPager() {

        mViewPagerAdapter = new CommentPagerAdapter(mContext, mStoryId + "");
        mViewPagerComments.setAdapter(mViewPagerAdapter);

        mViewPagerComments.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                mToolbar.setSubtitle(position == 0 ? CommentPagerAdapter.CommentType.LONG.cn : CommentPagerAdapter.CommentType.SHORT.cn);
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
