package com.neil.dailyzhihu.ui.story;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.neil.dailyzhihu.api.API;
import com.neil.dailyzhihu.listener.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.bean.orignallayer.StoryContent;
import com.neil.dailyzhihu.ui.widget.BaseActivity;
import com.neil.dailyzhihu.ui.widget.ObservableWebView;
import com.neil.dailyzhihu.ui.widget.ShareStoryPopupWindow;
import com.neil.dailyzhihu.api.AtyExtraKeyConstant;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.share.QRCodeUtil;
import com.neil.dailyzhihu.utils.storage.ImageExternalDirectoryUtil;
import com.neil.dailyzhihu.utils.load.LoaderFactory;
import com.neil.dailyzhihu.utils.img.ImageLoaderWrapper;
import com.neil.dailyzhihu.utils.storage.StorageOperatingHelper;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CertainStoryActivity extends BaseActivity implements ObservableScrollViewCallbacks {
    @Bind(R.id.image)
    ImageView mImageView;
    @Bind(R.id.overlay)
    View mOverlayView;
    @Bind(R.id.scroll)
    ObservableScrollView mScrollView;
    @Bind(R.id.fab)
    FloatingActionButton mFab;
    @Bind(R.id.main)
    FrameLayout mRootView;
    @Bind(R.id.toolbar)
    Toolbar mToolBar;
    @Bind(R.id.title)
    TextView mTitleView;
    @Bind(R.id.webview)
    ObservableWebView mWebView;

    private int mActionBarSize;
    private int mFlexibleSpaceShowFabOffset;
    private int mFlexibleSpaceImageHeight;
    private int mFabMargin;
    private boolean mFabIsShown;

    private Activity mContext = CertainStoryActivity.this;

    private int mStoryId;
    private StoryContent mStoryContent;
    private String mStoryTitle;
    private String mStoryImageUrl;
    private String mStoryHtmlContent;

    private static final float MAX_TEXT_SCALE_DELTA = 0.3f;

    private static final String LOG_TAG = CertainStoryActivity.class.getSimpleName();

    private OnContentLoadingFinishedListener mWebLoadListener = new OnContentLoadingFinishedListener() {
        @Override
        public void onFinish(String content) {
            // TODO 在较为特殊的情况下，知乎日报可能将某个主题日报的站外文章推送至知乎日报首页。type=0正常，type特殊情况
            mStoryContent = GsonDecoder.getDecoder().decoding(content, StoryContent.class);
            mStoryTitle = mStoryContent.getTitle();
            mStoryImageUrl = mStoryContent.getImage();
            Bitmap bm = ImageExternalDirectoryUtil.getBitmap(mContext, mStoryId);
            if (bm == null) {
                ImageLoaderWrapper loader = LoaderFactory.getImageLoader();
                loader.displayImage(mImageView, mStoryContent.getImage(), null, null);
            } else {
                mImageView.setImageBitmap(bm);
                Log.e(LOG_TAG, "BITMAP LOADED FROM SD CARD");
            }

            String storyTitle = mStoryContent.getTitle();
            setActionBarText(storyTitle);
            mTitleView.setText(storyTitle);

//            List<String> jsArr = mStoryContent.getJs();
            String cssContent = "";
            if (mStoryContent.getCss() != null && mStoryContent.getCss().size() > 0) {// 构建CSS
//                    cssContent = "<style type=\"text/css\">.content-image{width:100%;height:auto}" + mStoryContent.getCss().get(0) + "</style>";
                cssContent = "<link type=\"text/css\" rel=\"stylesheet\" href=\"http://shared.ydstatic.com/gouwuex/ext/css/extension_3_1.css?version=0.3.5&amp;buildday=22_02_2017_04_25\">" +
                        "\n<link type=\"text/css\" rel=\"stylesheet\" href=\"http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3\">\n" +
                        "<link type=\"text/css\" rel=\"stylesheet\" href=\"" + mStoryContent.getCss().get(0) + "\">\n"
                        + "<style>.headline{display:none;}</style>";
            }
            String html = "<html><head>" + cssContent + "</head><body>" + mStoryContent.getBody() + " </body></html>";
            mStoryHtmlContent = html;
            mWebView.setHorizontalScrollBarEnabled(false);
            //style="width:100%;height:auto"
            WebSettings webSettings = mWebView.getSettings(); // webView: 类WebView的实例
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);  //就是这句
            mWebView.loadData(html, "text/html; charset=UTF-8", null);
            Log.e("HTML", html);
        }
    };

    private View.OnClickListener upBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mContext.finish();
        }
    };

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        ButterKnife.bind(this);

        setSupportActionBar(mToolBar);
        mToolBar.setNavigationIcon(R.drawable.ic_action_back);
        mToolBar.setNavigationOnClickListener(upBtnListener);
        initObservableViewUIParams();
        fillingLoadingValues();
    }

    // 初始化ObservableView相关参数
    private void initObservableViewUIParams() {
        mFlexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
        mFlexibleSpaceShowFabOffset = getResources().getDimensionPixelSize(R.dimen.flexible_space_show_fab_offset);
        mActionBarSize = getActionBarSize();

        mScrollView.setScrollViewCallbacks(this);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFabIsShown) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
                    String text = mStoryTitle + " via " + "http://daily.zhihu.com/story/" + mStoryId + "\n(powered by DailyZHIHU)\n";
                    intent.putExtra(Intent.EXTRA_TEXT, text);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(Intent.createChooser(intent, "分享到..."));

//                    ShareStoryPopupWindow sspw = new ShareStoryPopupWindow(mContext, mStoryId + "", mStoryTitle,
//                            mStoryImageUrl, mStoryHtmlContent);
//                    sspw.setOnDismissListener(new PopupWindow.OnDismissListener() {
//                        @Override
//                        public void onDismiss() {
//                            lightOn();
//                        }
//                    });
//                    //点击时弹出PopupWindow，屏幕变暗
////                    sspw.setAnimationStyle(R.style.ListphotoSelect);
//                    sspw.showAsDropDown(v, 0, 0);
//                    lightOff();
                }
            }
        });
        mFabMargin = getResources().getDimensionPixelSize(R.dimen.margin_standard);
        ViewHelper.setScaleX(mFab, 0);
        ViewHelper.setScaleY(mFab, 0);
        ScrollUtils.addOnGlobalLayoutListener(mWebView, new Runnable() {
            @Override
            public void run() {
                //正好显现出照片
                onScrollChanged(0, false, false);
            }
        });
    }

    private void fillingLoadingValues() {
        if (getIntent().getExtras() == null) return;
        mStoryId = getIntent().getIntExtra(AtyExtraKeyConstant.STORY_ID, 0);
        if (mStoryId <= 0) return;
        fillingContent();
    }

    private void fillingContent() {
        String URL = "http://news-at.zhihu.com/api/4/news/" + mStoryId;
        Log.e("HTML", "URL:" + URL);
        LoaderFactory.getContentLoader().loadContent(API.STORY_PREFIX + mStoryId, mWebLoadListener);
    }

    private void setActionBarText(String storyTitle) {
        ActionBar ab = getSupportActionBar();
        if (ab != null) ab.setTitle(storyTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_certain_story_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_action_text_size:
                //TODO 设置字体大小
                Snackbar.make(mRootView, mContext.getResources().getString(R.string.to_do), Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.menu_item_action_comment:
                Intent intent = new Intent(mContext, CertainStoryCommentActivity.class);
                intent.putExtra(AtyExtraKeyConstant.STORY_ID, mStoryId);
                startActivity(intent);
                break;
            case R.id.menu_item_action_star:
                //TODO 收藏
                Snackbar.make(mRootView, mContext.getResources().getString(R.string.to_do), Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.menu_item_action_qrcode:
                // 生成二维码
                final String shareUrl = API.WEB_STORY_PREFIX + mStoryId;

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_qr_display, null, false);
                ImageView ivQR = (ImageView) view.findViewById(R.id.iv_qrDisplay);
                AlertDialog dialog = builder.setView(view).setTitle("二维码分享").create();
                dialog.show();

                ViewGroup.LayoutParams pm = ivQR.getLayoutParams();
                final Bitmap bm = QRCodeUtil.getQRBitmap(shareUrl, pm.width, pm.height, null);
                ivQR.setImageBitmap(bm);

                ivQR.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        String path = StorageOperatingHelper.savingBitmap2SD(mContext, bm, shareUrl);
                        if (!TextUtils.isEmpty(path))
                            Snackbar.make(mRootView, "保存成功" + path, Snackbar.LENGTH_SHORT).show();
                        return true;
                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
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
//        ViewHelper.setScaleX(mTitleView, scale);
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
        ViewHelper.setTranslationX(mFab, mOverlayView.getWidth() - mFabMargin - mFab.getWidth());
        ViewHelper.setTranslationY(mFab, fabTranslationY);

        // Show/hide FAB
        if (fabTranslationY < mFlexibleSpaceShowFabOffset) {
            hideFab();
            mToolBar.setBackgroundColor(getResources().getColor(R.color.ZHIHUBlue));
        } else {
            showFab();
            mToolBar.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        }
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        ActionBar ab = this.getSupportActionBar();
        if (ab == null) return;
        if (scrollState == ScrollState.UP) {
            if (ab.isShowing()) ab.hide();
        } else if (scrollState == ScrollState.DOWN) {
            if (!ab.isShowing()) ab.show();
        }
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

    //PopupWindow消失时，使屏幕恢复正常
    private void lightOn() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1.0f;
        getWindow().setAttributes(lp);
    }

    private void lightOff() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
        getWindow().setAttributes(lp);
    }
}