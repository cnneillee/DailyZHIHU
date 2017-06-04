package cn.neillee.dailyzhijiu.ui.story;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import cn.neillee.dailyzhijiu.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.neillee.dailyzhijiu.base.BaseSimpleActivity;
import cn.neillee.dailyzhijiu.model.bean.orignal.StoryExtraInfoBean;
import cn.neillee.dailyzhijiu.utils.GsonDecoder;

import static cn.neillee.dailyzhijiu.model.http.api.AtyExtraKeyConstant.COMMENT_TYPE;
import static cn.neillee.dailyzhijiu.model.http.api.AtyExtraKeyConstant.STORY_EXTRAS;
import static cn.neillee.dailyzhijiu.model.http.api.AtyExtraKeyConstant.STORY_ID;

/**
 * 作者：Neil on 2017/4/6 00:49.
 * 邮箱：cn.neillee@gmail.com
 */

public class StoryCommentActivity extends BaseSimpleActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.vp_comment)
    ViewPager mViewPager;

    private List<Fragment> mFragmentList;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_story_comment;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        int storyId = bundle.getInt(STORY_ID, -1);
        String storyExtra = bundle.getString(STORY_EXTRAS);

        setupToolbar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.abc_ic_clear_mtrl_alpha);

        if (storyExtra != null) initFragmentPager(storyId, storyExtra);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    private void initFragmentPager(int storyId, String storyExtra) {
        mFragmentList = new ArrayList<>();
        final StoryCommentFragment longFragment = StoryCommentFragment.newInstance();
        Bundle longBundle = new Bundle();
        longBundle.putInt(STORY_ID, storyId);
        longBundle.putInt(COMMENT_TYPE, 0);
        longFragment.setArguments(longBundle);

        final StoryCommentFragment shortFragment = StoryCommentFragment.newInstance();
        final Bundle shortBundle = new Bundle();
        shortBundle.putInt(STORY_ID, storyId);
        shortBundle.putInt(COMMENT_TYPE, 1);
        shortFragment.setArguments(shortBundle);

        mFragmentList.add(longFragment);
        mFragmentList.add(shortFragment);

        StoryExtraInfoBean bean = GsonDecoder.getDecoder().decoding(storyExtra, StoryExtraInfoBean.class);
        final String longCommentTitle = getResources().getString(R.string.comment_long_comment) + "(" + bean.getLongComments() + ")";
        final String shortCommentTitle = getResources().getString(R.string.comment_short_comment) + "(" + bean.getShortComments() + ")";

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
