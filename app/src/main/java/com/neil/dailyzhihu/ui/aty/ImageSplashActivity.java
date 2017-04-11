package com.neil.dailyzhihu.ui.aty;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.base.BaseActivity;
import com.neil.dailyzhihu.model.http.api.AtyExtraKeyConstant;
import com.neil.dailyzhihu.presenter.ImageSplashPresenter;
import com.neil.dailyzhihu.presenter.constract.ImageSplashContract;
import com.neil.dailyzhihu.ui.main.MainActivity;
import com.neil.dailyzhihu.utils.Formater;
import com.neil.dailyzhihu.utils.load.LoaderFactory;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import butterknife.BindView;

/**
 * 作者：Neil on 2016/3/22 19:08.
 * 邮箱：cn.neillee@gmail.com
 */
public class ImageSplashActivity extends BaseActivity<ImageSplashPresenter>
        implements ImageSplashContract.View {
    @BindView(R.id.view_container)
    ViewSwitcher mSwitcher;
    @BindView(R.id.iv_splash)
    ImageView mSplash;
    @BindView(R.id.tv_img_source)
    TextView mTvImgSource;

    private static final int IMG_LOADED = 0;
    private static final int TIME_UP = 1;
    private static final int DISPLAY_END = 2;
    private static final int MAX_IMG_LOADED_MILLIS = 1500;
    private static final int MAX_IMG_DISPLAY_MILLIS = 2000;

    private boolean mIsImgLoaded = false;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case IMG_LOADED:
                    mIsImgLoaded = true;
                    mSwitcher.showNext();
                    mSplash.setAnimation(AnimationUtils.loadAnimation(ImageSplashActivity.this, R.anim.splash));
                    mHandler.sendEmptyMessageDelayed(DISPLAY_END, MAX_IMG_DISPLAY_MILLIS);
                    break;
                case TIME_UP:
                    if (!mIsImgLoaded) {
                        Intent intent = new Intent(ImageSplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        ImageSplashActivity.this.finish();
                    }
                    break;
                case DISPLAY_END:
                    Intent intent = new Intent(ImageSplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    ImageSplashActivity.this.finish();
                    break;
            }
        }
    };

    @Override
    protected void initEventAndData() {
        int splashType = getIntent().getExtras().getInt(AtyExtraKeyConstant.SPLASH_TYPE, 1);
        mHandler.sendEmptyMessageDelayed(TIME_UP, MAX_IMG_LOADED_MILLIS);
        mPresenter.getSplash(splashType);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
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
        return R.layout.activity_start;
    }

    @Override
    public void showImage(String imgUrl, String intro) {
        mTvImgSource.setText(Formater.fromatOneDayOnPicInfo(ImageSplashActivity.this, intro));
        if (TextUtils.isEmpty(imgUrl)) {
            mHandler.sendEmptyMessage(DISPLAY_END);
            return;
        }
        LoaderFactory.getImageLoader().displayImage(mSplash, imgUrl, null, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                mHandler.sendEmptyMessage(IMG_LOADED);
            }
        });
    }

    @Override
    public void showError(String errMsg) {
        mHandler.sendEmptyMessage(DISPLAY_END);
    }

    @Override
    public void onBackPressed() {

    }
}
