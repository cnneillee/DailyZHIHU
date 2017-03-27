package com.neil.dailyzhihu.ui.story;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
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
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.neil.dailyzhihu.api.API;
import com.neil.dailyzhihu.listener.OnContentLoadedListener;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.bean.orignal.CertainStoryBean;
import com.neil.dailyzhihu.ui.widget.BaseActivity;
import com.neil.dailyzhihu.ui.widget.ObservableWebView;
import com.neil.dailyzhihu.api.AtyExtraKeyConstant;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.SnackbarUtil;
import com.neil.dailyzhihu.utils.share.QRCodeUtil;
import com.neil.dailyzhihu.utils.storage.ImageExternalDirectoryUtil;
import com.neil.dailyzhihu.utils.load.LoaderFactory;
import com.neil.dailyzhihu.utils.img.ImageLoaderWrapper;
import com.neil.dailyzhihu.utils.storage.StorageOperatingHelper;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StoryDetailActivity extends BaseActivity implements ObservableScrollViewCallbacks {
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

    private Activity mContext = StoryDetailActivity.this;

    private int mStoryId;
    private String mStoryTitle;
    private String mDefaultImg;

    private static final float MAX_TEXT_SCALE_DELTA = 0.3f;

    private static final String LOG_TAG = StoryDetailActivity.class.getSimpleName();

    private OnContentLoadedListener mWebLoadListener = new OnContentLoadedListener() {
        @Override
        public void onSuccess(String content, String url) {
            // TODO 在较为特殊的情况下，知乎日报可能将某个主题日报的站外文章推送至知乎日报首页。type=0正常，type特殊情况
            CertainStoryBean certainStoryBean = GsonDecoder.getDecoder().decoding(content, CertainStoryBean.class);
            mStoryTitle = certainStoryBean.getTitle();
            Bitmap bm = ImageExternalDirectoryUtil.getBitmap(mContext, mStoryId);
            if (bm == null) {
                ImageLoaderWrapper loader = LoaderFactory.getImageLoader();
                String imgUrl = certainStoryBean.getImage() == null ? mDefaultImg : certainStoryBean.getImage();
                loader.displayImage(mImageView, imgUrl, null, null);
            } else {
                mImageView.setImageBitmap(bm);
                Log.e(LOG_TAG, "BITMAP LOADED FROM SD CARD");
            }

            String storyTitle = certainStoryBean.getTitle();
            setActionBarText(storyTitle);
            mTitleView.setText(storyTitle);

//            List<String> jsArr = mStoryContent.getJs();
            String cssContent = "";
            if (certainStoryBean.getCss() != null && certainStoryBean.getCss().size() > 0) {// 构建CSS
//                    cssContent = "<style type=\"text/css\">.content-image{width:100%;height:auto}" + mStoryContent.getCss().get(0) + "</style>";
                cssContent = "<link type=\"text/css\" rel=\"stylesheet\" href=\"http://shared.ydstatic.com/gouwuex/ext/css/extension_3_1.css?version=0.3.5&amp;buildday=22_02_2017_04_25\">" +
                        "\n<link type=\"text/css\" rel=\"stylesheet\" href=\"http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3\">\n" +
                        "<link type=\"text/css\" rel=\"stylesheet\" href=\"" + certainStoryBean.getCss().get(0) + "\">\n"
                        + "<style>.headline{display:none;}</style>";
            }
            String html = "<html><head>" + cssContent + "</head><body>" + certainStoryBean.getBody() + " </body></html>";
            mWebView.setHorizontalScrollBarEnabled(false);
            // style="width:100%;height:auto"
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

    @Override
    protected void initViews() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_story_detail);
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
        mDefaultImg = getIntent().getStringExtra(AtyExtraKeyConstant.DEFAULT_IMG_URL);
        if (mStoryId <= 0) return;
        fillingContent();
    }

    private void fillingContent() {
        String contentUrl = API.STORY_PREFIX + mStoryId;
        Log.e("HTML", "URL:" + contentUrl);
        LoaderFactory.getContentLoader().loadContent(contentUrl, mWebLoadListener);
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
                SnackbarUtil.ShortSnackbar(mRootView, mContext.getResources().getString(R.string.to_do), SnackbarUtil.Confirm).show();
                break;
            case R.id.menu_item_action_comment:
                Intent intent = new Intent(mContext, CertainStoryCommentActivity.class);
                intent.putExtra(AtyExtraKeyConstant.STORY_ID, mStoryId);
                startActivity(intent);
                break;
            case R.id.menu_item_action_star:
                //TODO 收藏
                SnackbarUtil.ShortSnackbar(mRootView, mContext.getResources().getString(R.string.to_do), SnackbarUtil.Confirm).show();
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
                            SnackbarUtil.ShortSnackbar(mRootView, "保存成功" + path, SnackbarUtil.Info).show();
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
        // ViewHelper.setScaleX(mTitleView, scale);
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

    // PopupWindow消失时，使屏幕恢复正常
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