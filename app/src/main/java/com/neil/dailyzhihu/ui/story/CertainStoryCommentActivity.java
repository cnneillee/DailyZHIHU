package com.neil.dailyzhihu.ui.story;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.LongCommentListAdapter;
import com.neil.dailyzhihu.api.API;
import com.neil.dailyzhihu.bean.orignallayer.LongComment;
import com.neil.dailyzhihu.listener.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.api.AtyExtraKeyConstant;
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

public class CertainStoryCommentActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.view_pager_comments)
    ViewPager mViewPagerComments;
    private CommentPagerAdapter mViewPagerAdapter;

    private List<View> mViews = new ArrayList<>();

    private enum CommentType {
        LONG("长评论", "/long-comments", 0), SHORT("短评论", "/short-comments", 1);
        String cn;
        String en;
        int index;

        CommentType(String cnName, String enName, int index) {
            this.cn = cnName;
            this.en = enName;
            this.index = index;
        }
    }

    private int mStoryId;
    private Activity mContext = CertainStoryCommentActivity.this;

    private View.OnClickListener upBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mContext.onBackPressed();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certain_story_comment);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_action_cancel);
        mToolbar.setNavigationOnClickListener(upBtnListener);

        loadIntentExtras();
        initViewPager();
        loadComments();
    }

    private void initViewPager() {
        mViews.clear();
        mViews.add(CommentType.LONG.index, LayoutInflater.from(mContext).inflate(R.layout.vp_item_comment, null, false));
        mViews.add(CommentType.SHORT.index, LayoutInflater.from(mContext).inflate(R.layout.vp_item_comment, null, false));

        mViewPagerAdapter = new CommentPagerAdapter();
        mViewPagerComments.setAdapter(mViewPagerAdapter);

        mViewPagerComments.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                mToolbar.setSubtitle(position == 0 ? CommentType.LONG.cn : CommentType.SHORT.cn);
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

    private void loadComments() {
        if (mStoryId == -1) return;

        LoaderFactory.getContentLoader().loadContent(API.STORY_COMMENT_PREFIX + mStoryId + CommentType.LONG.en,
                new OnContentLoadingFinishedListener() {
            @Override
            public void onFinish(String content) {
                Logger.json(content);
                LongComment longComment = GsonDecoder.getDecoder().decoding(content, LongComment.class);
                if (longComment == null) return;
                View view = mViews.get(CommentType.LONG.index);
                ListView lvComments = (ListView) view.findViewById(R.id.lv_comment);
                List<LongComment.CommentsBean> data = longComment.getComments();
                lvComments.setAdapter(new LongCommentListAdapter(mContext, data));
                if (mViewPagerAdapter != null) mViewPagerAdapter.notifyDataSetChanged();
            }
        });

        LoaderFactory.getContentLoader().loadContent(API.STORY_COMMENT_PREFIX + mStoryId + CommentType.SHORT.en, new OnContentLoadingFinishedListener() {
            @Override
            public void onFinish(String content) {
                Logger.json(content);
                LongComment longComment = GsonDecoder.getDecoder().decoding(content, LongComment.class);
                if (longComment == null) return;
                View view = mViews.get(CommentType.SHORT.index);
                ListView lvComments = (ListView) view.findViewById(R.id.lv_comment);
                List<LongComment.CommentsBean> data = longComment.getComments();
                lvComments.setAdapter(new LongCommentListAdapter(mContext, data));
                if (mViewPagerAdapter != null) mViewPagerAdapter.notifyDataSetChanged();
            }
        });
    }

    public void loadIntentExtras() {
        if (getIntent() == null) return;
        mStoryId = getIntent().getIntExtra(AtyExtraKeyConstant.STORY_ID, -1);
    }

    private class CommentPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView(mViews.get(position));
        }


        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            final View view = mViews.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "标题" + position;
        }
    }
}
