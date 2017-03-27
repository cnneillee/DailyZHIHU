package com.neil.dailyzhihu.ui.aty;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.google.gson.Gson;
import com.neil.dailyzhihu.api.API;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.bean.orignal.StartImgBean;
import com.neil.dailyzhihu.ui.main.MainActivity;
import com.neil.dailyzhihu.utils.load.LoaderFactory;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：Neil on 2016/3/22 19:08.
 * 邮箱：cn.neillee@gmail.com
 */
public class ImageSplashActivity extends AppCompatActivity {
    @Bind(R.id.view_container)
    ViewSwitcher mSwitcher;
    @Bind(R.id.iv_splash)
    ImageView mSplash;
    @Bind(R.id.tv_img_source)
    TextView mTvImgSource;

    private String startImgSize = API.START_IMG_SIZE_LARGE;
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

        mHandler.sendEmptyMessageDelayed(TIME_UP, MAX_IMG_LOADED_MILLIS);

        new Thread() {
            @Override
            public void run() {
                int sleepMillies = (int) (2000 * Math.random());
                try {
                    Thread.sleep(sleepMillies);
                    Logger.e("sleepMillies：" + sleepMillies);
                    mockData();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.run();
//        LoaderFactory.getContentLoader().loadContent(startImgSize, new OnContentLoadedListener() {
//            @Override
//            public void onSuccess(String content, String url) {
//                Gson gson = new Gson();
//                StartImgBean startImgBean = gson.fromJson(content, StartImgBean.class);
//                String imgUrl = startImgBean.getImg();
//                String imgSign = startImgBean.getText();
//                mTvImgSource.setText("每日一图 · " + imgSign);
//                LoaderFactory.getImageLoader().displayImage(mSplash, imgUrl, null, new SimpleImageLoadingListener() {
//                    @Override
//                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                        super.onLoadingComplete(imageUri, view, loadedImage);
//                        mHandler.sendEmptyMessage(IMG_LOADED);
//                    }
//                });
//            }
//        });
    }

    public void mockData() {
        String content = "{text: \"© Fido Dido\",img: \"http://p2.zhimg.com/10/7b/107bb4894b46d75a892da6fa80ef504a.jpg\"}  ";
        Gson gson = new Gson();
        StartImgBean startImgBean = gson.fromJson(content, StartImgBean.class);
        String imgUrl = startImgBean.getImg();
        String imgSign = startImgBean.getText();
        mTvImgSource.setText("每日一图 · " + imgSign);
        LoaderFactory.getImageLoader().displayImage(mSplash, imgUrl, null, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                mHandler.sendEmptyMessage(IMG_LOADED);
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
