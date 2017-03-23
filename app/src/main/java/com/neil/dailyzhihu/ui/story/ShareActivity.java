package com.neil.dailyzhihu.ui.story;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.neil.dailyzhihu.api.AtyExtraKeyConstant;
import com.neil.dailyzhihu.ui.NightModeBaseActivity;
import com.neil.dailyzhihu.utils.SnackbarUtil;
import com.neil.dailyzhihu.utils.share.PlatformInfoUtils;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.listener.BitmapLoadCallback;
import com.neil.dailyzhihu.utils.BitmapUtil;
import com.neil.dailyzhihu.utils.storage.StorageOperatingHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：Neil on 2017/3/4 13:08.
 * 邮箱：cn.neillee@gmail.com
 */

public class ShareActivity extends NightModeBaseActivity implements BitmapLoadCallback {
    private static final String LOG_TAG = ShareActivity.class.getSimpleName();
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.vp_share)
    ViewPager mVpShare;
    private String mHtmlContent;
    private int mShareType;
    private String mStoryTitle;
    private String mStoryId;
    private String mStoryImageUrl;

    private List<Fragment> mFragmentList;

    private View.OnClickListener upBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ShareActivity.this.finish();
        }
    };

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_story_share);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_action_cancel);
        mToolbar.setNavigationOnClickListener(upBtnListener);
        getExtras();

        mFragmentList = new ArrayList<>();
        ShareFragment shareFragment = ShareFragment.newInstance(mShareType);
        ShareImageFragment shareImageFragment = ShareImageFragment.newInstance();
        mFragmentList.add(0, shareFragment);
        mFragmentList.add(1, shareImageFragment);

        String shareText = mStoryTitle + " via " + "http://daily.zhihu.com/story/" + mStoryId + "\n(powered by DailyZHIHU)\n";
        shareFragment.setStoryTitle(mStoryTitle);
        shareFragment.setShareText(shareText);
        shareFragment.setImageUrl(mStoryImageUrl);
        shareFragment.setStoryId(mStoryId);
        shareImageFragment.setHtmlContent(mHtmlContent);
        mVpShare.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        });
    }

    public void getExtras() {
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            mShareType = bundle.getInt(AtyExtraKeyConstant.STORY_SHARE_PLATFORM);
            mHtmlContent = bundle.getString(AtyExtraKeyConstant.STORY_SHARE_HTML_CONTENT);
            mStoryTitle = bundle.getString(AtyExtraKeyConstant.STORY_TITLE);
            mStoryId = bundle.getString(AtyExtraKeyConstant.STORY_ID);
            mStoryImageUrl = bundle.getString(AtyExtraKeyConstant.STORY_IMAGE_URL);

            String title = PlatformInfoUtils.SharePlatformSeries.getTitle(mShareType);
            mToolbar.setTitle(title);
        } else {
            SnackbarUtil.ShortSnackbar(mVpShare, "程序异常，请重试", SnackbarUtil.Info).show();
        }
    }

    private Bitmap mBitmap;
    private ImageView mImageView;

    @Override
    public void bitmapLoaded(Bitmap bm) {
        if (mBitmap != null) {
            mBitmap = BitmapUtil.scaleImage(mBitmap, mImageView.getWidth(), mImageView.getHeight());
            mImageView.setImageBitmap(mBitmap);
        }
        mBitmap = bm;
        String path = saveView2SDCard(bm);
        Log.e(LOG_TAG, "saveView2SDCard" + path);
    }

    @Override
    public void bitmapDisplay(ImageView iv) {
        this.mImageView = iv;
        if (mBitmap != null) {
            mBitmap = BitmapUtil.scaleImage(mBitmap, iv.getWidth(), iv.getHeight());
            mImageView.setImageBitmap(mBitmap);
        }
    }

    private String saveView2SDCard(Bitmap bmConent) {
        ShareFragment shareFragment = (ShareFragment) mFragmentList.get(0);
        String path = StorageOperatingHelper.savingBitmap2SD(this, bmConent, mStoryId);
        shareFragment.setStoryImagePath(path);
        return path;
    }
}

