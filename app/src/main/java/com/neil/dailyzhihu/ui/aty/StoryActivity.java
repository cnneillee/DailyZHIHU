package com.neil.dailyzhihu.ui.aty;

import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.google.gson.Gson;
import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.LongCommentListAdapter;
import com.neil.dailyzhihu.bean.LongComment;
import com.neil.dailyzhihu.bean.StoryContent;
import com.neil.dailyzhihu.ui.widget.BaseActivity;
import com.neil.dailyzhihu.ui.widget.ShareMenuPopupWindow;
import com.neil.dailyzhihu.utils.Formater;
import com.neil.dailyzhihu.utils.LoaderFactory;
import com.neil.dailyzhihu.utils.ShareHelper;
import com.neil.dailyzhihu.utils.img.ImageLoaderWrapper;
import com.neil.dailyzhihu.utils.share.QQShare;
import com.neil.dailyzhihu.utils.share.SinaShare;
import com.neil.dailyzhihu.utils.share.Util;
import com.neil.dailyzhihu.utils.share.WechatShare;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

public class StoryActivity extends BaseActivity implements ObservableScrollViewCallbacks, AdapterView.OnItemClickListener, PlatformActionListener {

    private static final float MAX_TEXT_SCALE_DELTA = 0.3f;

    private ImageView mImageView;
    private View mOverlayView;
    private ObservableScrollView mScrollView;
    private TextView mTitleView;
    private View mFab;
    private int mActionBarSize;
    private int mFlexibleSpaceShowFabOffset;
    private int mFlexibleSpaceImageHeight;
    private int mFabMargin;
    private boolean mFabIsShown;

    private TextView tvContent;
    private TextView tvImgCopyRight;
//    private TextView tvTitle;

    private static final String LOG_TAG = StoryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        int storyId = getExtras();
//        storyId = 8150357;
        if (storyId > 0)
            fillingContent(storyId);

        initActivity();
        tvContent = (TextView) findViewById(R.id.tvContent);
//        tvImgCopyRight = (TextView) findViewById(R.id.img_copyright);
//        tvTitle= (TextView) findViewById(R.id.title);

        mFlexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
        mFlexibleSpaceShowFabOffset = getResources().getDimensionPixelSize(R.dimen.flexible_space_show_fab_offset);
        mActionBarSize = getActionBarSize();

        mImageView = (ImageView) findViewById(R.id.image);
        mOverlayView = findViewById(R.id.overlay);
        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);
        mTitleView = (TextView) findViewById(R.id.title);
        mTitleView.setText(getTitle());
        setTitle(null);
        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildingCommentPopupWindow();
                Toast.makeText(StoryActivity.this, "FAB is clicked", Toast.LENGTH_SHORT).show();
            }
        });
        mFabMargin = getResources().getDimensionPixelSize(R.dimen.margin_standard);
        ViewHelper.setScaleX(mFab, 0);
        ViewHelper.setScaleY(mFab, 0);

        ScrollUtils.addOnGlobalLayoutListener(mScrollView, new Runnable() {
            @Override
            public void run() {
                //挡住照片
                // mScrollView.scrollTo(0, mFlexibleSpaceImageHeight - mActionBarSize);
                //正好显现出照片
                onScrollChanged(0, false, false);
                // If you'd like to start from scrollY == 0, don't write like this:
                //mScrollView.scrollTo(0, 0);
                // The initial scrollY is 0, so it won't invoke onScrollChanged().
                // To do this, use the following:
                //onScrollChanged(0, false, false);

                // You can also achieve it with the following codes.
                // This causes scroll change from 1 to 0.
                //mScrollView.scrollTo(0, 1);
                //mScrollView.scrollTo(0, 0);
            }
        });
    }

    private PopupWindow pw = null;

    private void buildingCommentPopupWindow() {
        if (pw != null)
            pw.showAsDropDown(mFab);
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.popupwindow_comments_menu, null);
        ListView lv = (ListView) contentView.findViewById(R.id.lv_pwmenu);
        lv.setAdapter(new ArrayAdapter<>(this,
                R.layout.item_lv_menu_small_text, new String[]{"长评论", "短评论", "分享"}));
        lv.setOnItemClickListener(this);
        pw = new PopupWindow(contentView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, false);
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setOutsideTouchable(true);
        pw.showAsDropDown(mFab);
    }

    private void viewShortComments() {
        AlertDialog.Builder builder = new AlertDialog.Builder(StoryActivity.this);
        View view = LayoutInflater.from(StoryActivity.this).
                inflate(R.layout.view_long_comments, null, false);
        final ListView lv2 = (ListView) view.findViewById(R.id.lv_longComments);
        AlertDialog dialog = builder.setView(view).
                setTitle("长评论").
                setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();

        dialog.show();
        LoaderFactory.getContentLoader().loadContent("http://news-at.zhihu.com/api/4/story/" +
                storyId + "/short-comments", new OnContentLoadingFinishedListener() {
            @Override
            public void onFinish(String s) {
                Gson gson = new Gson();
                LongComment longComment = gson.fromJson(s, LongComment.class);
                List<LongComment.CommentsBean> mDatas = longComment.getComments();
                lv2.setAdapter(new LongCommentListAdapter(StoryActivity.this, mDatas));
                Toast.makeText(StoryActivity.this, "lv:" + lv2.toString() + "adapter有:" + mDatas.size(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewLongComments() {
        AlertDialog.Builder builder = new AlertDialog.Builder(StoryActivity.this);
        View view = LayoutInflater.from(StoryActivity.this).
                inflate(R.layout.view_long_comments, null, false);
        final ListView lv1 = (ListView) view.findViewById(R.id.lv_longComments);
        AlertDialog dialog = builder.setView(view).
                setTitle("长评论").
                setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();

        dialog.show();
        LoaderFactory.getContentLoader().loadContent("http://news-at.zhihu.com/api/4/story/" +
                storyId + "/long-comments", new OnContentLoadingFinishedListener() {
            @Override
            public void onFinish(String content) {
                Gson gson = new Gson();
                LongComment longComment = gson.fromJson(content, LongComment.class);
                List<LongComment.CommentsBean> mDatas = longComment.getComments();
                lv1.setAdapter(new LongCommentListAdapter(StoryActivity.this, mDatas));
                Toast.makeText(StoryActivity.this, "lv:" + lv1.toString() + "adapter有:" + mDatas.size(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getExtras() {
        int storyId = -1;
        if (getIntent().getExtras() != null) {
            storyId = getIntent().getIntExtra(Constant.STORY_ID, 0);
        }
        return storyId;
    }

    private int storyId = -1;
    private String storyTtitle;
    private StoryContent story;

    private void fillingContent(final int storyId) {
        this.storyId = storyId;

        LoaderFactory.getContentLoader().loadContent(Constant.STORY_HEAD + storyId, new OnContentLoadingFinishedListener() {
            @Override
            public void onFinish(String content) {
                Gson gson = new Gson();
                story = gson.fromJson(content, StoryContent.class);
                String body = story.getBody();
                //TODO 在较为特殊的情况下，知乎日报可能将某个主题日报的站外文章推送至知乎日报首页。
                //type=0正常，type特殊情况
                //TODO 图片加载
                ImageLoaderWrapper loader = LoaderFactory.getImageLoader();
                loader.displayImage(mImageView, story.getImage(), null);
                storyTtitle = story.getTitle();
                mTitleView.setText(storyTtitle);
//                tvImgCopyRight.setText(story.getImage_source());
                if (body == null)
                    return;
                tvContent.setText(Html.fromHtml(body));
                tvContent.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initActivity() {

    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        // Translate overlay and image
        float flexibleRange = mFlexibleSpaceImageHeight - mActionBarSize;
        int minOverlayTransitionY = mActionBarSize - mOverlayView.getHeight();
        ViewHelper.setTranslationY(mOverlayView, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
        ViewHelper.setTranslationY(mImageView, ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0));

        // Change alpha of overlay
        ViewHelper.setAlpha(mOverlayView, ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));

        // Scale title text
        float scale = 1 + ScrollUtils.getFloat((flexibleRange - scrollY) / flexibleRange, 0, MAX_TEXT_SCALE_DELTA);
        ViewHelper.setPivotX(mTitleView, 0);
        ViewHelper.setPivotY(mTitleView, 0);
        ViewHelper.setScaleX(mTitleView, scale);
        ViewHelper.setScaleY(mTitleView, scale);

        // Translate title text
        int maxTitleTranslationY = (int) (mFlexibleSpaceImageHeight - mTitleView.getHeight() * scale);
        int titleTranslationY = maxTitleTranslationY - scrollY;
        ViewHelper.setTranslationY(mTitleView, titleTranslationY);

        // Translate FAB
        int maxFabTranslationY = mFlexibleSpaceImageHeight - mFab.getHeight() / 2;
        float fabTranslationY = ScrollUtils.getFloat(
                -scrollY + mFlexibleSpaceImageHeight - mFab.getHeight() / 2,
                mActionBarSize - mFab.getHeight() / 2,
                maxFabTranslationY);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            // On pre-honeycomb, ViewHelper.setTranslationX/Y does not set margin,
            // which causes FAB's OnClickListener not working.
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFab.getLayoutParams();
            lp.leftMargin = mOverlayView.getWidth() - mFabMargin - mFab.getWidth();
            lp.topMargin = (int) fabTranslationY;
            mFab.requestLayout();
        } else {
            ViewHelper.setTranslationX(mFab, mOverlayView.getWidth() - mFabMargin - mFab.getWidth());
            ViewHelper.setTranslationY(mFab, fabTranslationY);
        }

        // Show/hide FAB
        if (fabTranslationY < mFlexibleSpaceShowFabOffset) {
            hideFab();
        } else {
            showFab();
        }
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    private void showFab() {
        if (!mFabIsShown) {
            ViewPropertyAnimator.animate(mFab).cancel();
            ViewPropertyAnimator.animate(mFab).scaleX(1).scaleY(1).setDuration(200).start();
            mFabIsShown = true;
        }
    }

    private void hideFab() {
        if (mFabIsShown) {
            ViewPropertyAnimator.animate(mFab).cancel();
            ViewPropertyAnimator.animate(mFab).scaleX(0).scaleY(0).setDuration(200).start();
            mFabIsShown = false;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0://长评论
                Toast.makeText(StoryActivity.this, "查看长评论", Toast.LENGTH_SHORT).show();
                viewLongComments();
                break;
            case 1://短评论
                Toast.makeText(StoryActivity.this, "查看短评论", Toast.LENGTH_SHORT).show();
                viewShortComments();
                break;
            case 2://分享
                Toast.makeText(StoryActivity.this, "分享", Toast.LENGTH_SHORT).show();
                showShareModule();
//                share(story);
                break;
        }
    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String title = story.getTitle();
            String shareUrl = story.getShare_url();
            String img = story.getImage();
            String text = title + "\tmore? via zhihuDaily--->\t" + shareUrl;
            switch (position) {
                case 0://生成图片
                    Intent intent = new Intent(StoryActivity.this, ImageStoryActivity.class);
                    intent.putExtra(Constant.STORY_BODY, story.getBody());
                    startActivity(intent);
                    break;
                case 1://微信好友
                    WechatShare.wechatShareText(StoryActivity.this, StoryActivity.this, title, text, Util.WECHAT_FRIEND);
                    break;
                case 2://票圈
                    WechatShare.wechatShareText(StoryActivity.this, StoryActivity.this, title, text, Util.WECHAT_MOMENTS);
                    break;
                case 3://空间
                    QQShare.qqShareLink(StoryActivity.this, StoryActivity.this, title, shareUrl, text, "", img, Util.QZONE_NAME);
                    break;
                case 4://QQ
                    QQShare.qqShareLink(StoryActivity.this, StoryActivity.this, title, shareUrl, text, "", img, Util.QQ_NAME);
                    break;
                case 5://新浪微博
                    SinaShare.sinaShareLink(StoryActivity.this, StoryActivity.this, text, "", img, shareUrl);
                    break;
                case 6://复制链接
                    saveToClipboard(shareUrl);
                    Toast.makeText(StoryActivity.this, "成功复制到剪贴板", Toast.LENGTH_SHORT).show();
                    break;
                case 7://更多
                    String storyText = makeShareText();
                    if (storyText == null)
                        return;
                    Toast.makeText(StoryActivity.this, "更多分享", Toast.LENGTH_SHORT).show();
                    ShareHelper.orignalMsgShare(StoryActivity.this, "StoryActivity", storyText, storyText, null);
                    break;
            }
        }

        private String makeShareText() {
            if (storyId > 0)
                return storyTtitle + Constant.STORY_HEAD + storyId + "\nvia DailyZHIHU";
            return null;
        }
    };

    private String getFullUrl(int storyId) {
        if (storyId > 0)
            return Formater.formatUrl(Constant.URL_SHARE_STORY_HEAD, storyId);
        return null;
    }

    private void saveToClipboard(String str) {
        ClipboardManager cbm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        cbm.setText(str);
    }

    private void showShareModule() {
        ShareMenuPopupWindow popupWindow = new ShareMenuPopupWindow(this, mOnItemClickListener);
        //显示窗口
        popupWindow.showAtLocation(this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
    }

    public void share(StoryContent story) {
        if (story == null)
            return;
        String shareUrl = story.getShare_url();
        Log.e(LOG_TAG, shareUrl);
        ShareHelper.onKeyShareText(StoryActivity.this, story.getTitle(),
                story.getTitle() + "via zhihuDaily" + story.getShare_url(), story.getImage());
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }
}