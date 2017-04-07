package com.neil.dailyzhihu.ui.story;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.ui.NightModeBaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.neil.dailyzhihu.mvp.model.http.api.AtyExtraKeyConstant.COMMENT_TYPE;
import static com.neil.dailyzhihu.mvp.model.http.api.AtyExtraKeyConstant.STORY_ID;

/**
 * 作者：Neil on 2017/4/6 00:49.
 * 邮箱：cn.neillee@gmail.com
 */

public class StoryCommentActivity extends NightModeBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.vp_comment)
    ViewPager mViewPager;

    private List<Fragment> mFragmentList;

    private View.OnClickListener upBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            StoryCommentActivity.this.finish();
        }
    };

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_story_comment);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        int storyId = bundle.getInt(STORY_ID, -1);

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_action_cancel);
        mToolbar.setNavigationOnClickListener(upBtnListener);

        mFragmentList = new ArrayList<>();
        StoryCommentFragment longFragment = StoryCommentFragment.newInstance();
        Bundle longBundle = new Bundle();
        longBundle.putInt(STORY_ID, storyId);
        longBundle.putInt(COMMENT_TYPE, 0);
        longFragment.setArguments(longBundle);

        StoryCommentFragment shortFragment = StoryCommentFragment.newInstance();
        final Bundle shortBundle = new Bundle();
        shortBundle.putInt(STORY_ID, storyId);
        shortBundle.putInt(COMMENT_TYPE, 1);
        shortFragment.setArguments(shortBundle);

        mFragmentList.add(longFragment);
        mFragmentList.add(shortFragment);

        final String longCommentTitle = getResources().getString(R.string.long_comment);
        final String shortCommentTitle = getResources().getString(R.string.short_comment);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return position == 0 ? longCommentTitle : shortCommentTitle;
            }
        });
        mTabs.setupWithViewPager(mViewPager);
    }
}
