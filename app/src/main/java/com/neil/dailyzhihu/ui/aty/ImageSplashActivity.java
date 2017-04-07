package com.neil.dailyzhihu.ui.aty;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.google.gson.Gson;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.listener.OnContentLoadListener;
import com.neil.dailyzhihu.mvp.model.bean.orignal.GankSplashBean;
import com.neil.dailyzhihu.mvp.model.bean.orignal.HuaBanSplashBean;
import com.neil.dailyzhihu.mvp.model.bean.orignal.ZhihuSplashBean;
import com.neil.dailyzhihu.mvp.model.http.api.API;
import com.neil.dailyzhihu.mvp.model.http.api.AtyExtraKeyConstant;
import com.neil.dailyzhihu.ui.main.MainActivity;
import com.neil.dailyzhihu.utils.Formater;
import com.neil.dailyzhihu.utils.load.LoaderFactory;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：Neil on 2016/3/22 19:08.
 * 邮箱：cn.neillee@gmail.com
 */
public class ImageSplashActivity extends AppCompatActivity {
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
                    break;
                case TIME_UP:
                    if (!mIsImgLoaded) {
                        Intent intent = new Intent(ImageSplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        ImageSplashActivity.this.finish();
                    } else {
                        mSwitcher.showNext();
                        mSplash.setAnimation(AnimationUtils.loadAnimation(ImageSplashActivity.this, R.anim.splash));
                        mHandler.sendEmptyMessageDelayed(DISPLAY_END, MAX_IMG_DISPLAY_MILLIS);
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);

        int splashType = getIntent().getExtras().getInt(AtyExtraKeyConstant.SPLASH_TYPE, 1);
        mHandler.sendEmptyMessageDelayed(TIME_UP, MAX_IMG_LOADED_MILLIS);

        String url = API.ZHIHU_SPLASH;
        switch (splashType) {
            case 1:
                url = API.PUSH_SPLASH;
                break;
            case 2:
                url = API.ERCIYUAN_SPLASH;
                break;
            case 3:
                url = API.ZHIHU_SPLASH;
                break;
            case 4:
                url = API.GANK_SPLASH;
                break;
        }

        LoaderFactory.getContentLoader().loadContent(url, new OnContentLoadListener() {
            @Override
            public void onSuccess(String content, String url) {
                Gson gson = new Gson();
                String imgUrl = "";
                String imgSign = "";
                switch (url) {
                    case API.PUSH_SPLASH:
                    case API.ERCIYUAN_SPLASH: {
                        HuaBanSplashBean splashBean = gson.fromJson(content, HuaBanSplashBean.class);
                        if (splashBean.getPins() != null || splashBean.getPins().size() > 0) {
                            imgUrl = API.HUABAN_IMG_HEADER + splashBean.getPins().get(0).getFile().getKey();
                            imgSign = splashBean.getPins().get(0).getRaw_text();
                        }
                        break;
                    }
                    case API.ZHIHU_SPLASH: {
                        ZhihuSplashBean splashBean = gson.fromJson(content, ZhihuSplashBean.class);
                        imgUrl = splashBean.getLaunch_ads() == null || splashBean.getLaunch_ads().size() == 0 ?
                                "" : splashBean.getLaunch_ads().get(0).getImage();
                        imgSign = getResources().getString(R.string.daily_splash);
                        break;
                    }
                    case API.GANK_SPLASH: {
                        GankSplashBean splashBean = gson.fromJson(content, GankSplashBean.class);
                        imgUrl = splashBean.isError() || splashBean.getResults().size() == 0 ?
                                "" : splashBean.getResults().get(0).getUrl();
                        imgSign = splashBean.isError() || splashBean.getResults().size() == 0 ?
                                "" : splashBean.getResults().get(0).getSource();
                        break;
                    }
                }
                mTvImgSource.setText(Formater.fromatOneDayOnPicInfo(ImageSplashActivity.this, imgSign));
                if (TextUtils.isEmpty(imgUrl)) {
                    mHandler.sendEmptyMessage(DISPLAY_END);
                    return;
                }
                LoaderFactory.getImageLoader().displayImage(mSplash, imgUrl, null, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        super.onLoadingComplete(imageUri, view, loadedImage);
                        mHandler.sendEmptyMessage(IMG_LOADED);
                    }
                });
            }

            @Override
            public void onFailure(String errMsg) {
                
            }
        });
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }
}
