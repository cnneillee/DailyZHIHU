package com.neil.dailyzhihu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.neil.dailyzhihu.MainActivity;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.bean.StartImg;
import com.neil.dailyzhihu.utils.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Neil on 2016/3/22.
 */
public class StartActivity extends AppCompatActivity {

    private final String START_IMG_HEAD = "http://news-at.zhihu.com/api/4/start-image/";
    @Bind(R.id.iv_start)
    ImageView ivStart;
    private SIZE size = SIZE.MEDIUM;

    public enum SIZE {
        MAX("1080*1776"), MEDIUM("480*728"), MIN("320*432"), LARGE("720*1184");

        private final String SIZE;

        @Override
        public String toString() {
            return super.toString();
        }

        private SIZE(String size) {
            this.SIZE = size;
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        System.out.println(getUrl(START_IMG_HEAD, size));
        ImageLoader.loadImage(ivStart, START_IMG_HEAD, new ImageLoader.OnFinishListener() {
            @Override
            public void onFinish(Object s) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(2000);
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

    private String getUrl(String head, SIZE size) {
        return head + size.toString();
    }
}
