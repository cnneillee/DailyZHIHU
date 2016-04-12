package com.neil.dailyzhihu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.MainActivity;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.utils.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Neil on 2016/3/22.
 */
public class StartActivity extends AppCompatActivity {

    @Bind(R.id.iv_start)
    ImageView ivStart;
    private String startImgSize = Constant.START_IMG_SIZE_LARGE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);

        ImageLoader.loadImage(ivStart, startImgSize, new ImageLoader.OnFinishListener() {
            @Override
            public void onFinish(Object s) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(Constant.START_ACTY_LAST_MILLIES);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(StartActivity.this, MainActivity.class);
                        StartActivity.this.startActivity(intent);
                    }
                }.start();
            }
        });
    }
}
