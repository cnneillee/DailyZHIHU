package com.neil.dailyzhihu.ui.aty;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.utils.ShareHelper;


/**
 * Created by Neil on 2016/4/19.
 */
public class ImageStoryActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    RadioGroup mRgImgStoryTheme;
    TextView mTvContent;
    TextView mTvShare;
    TextView mTvSave;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_story);
        mRgImgStoryTheme = (RadioGroup) findViewById(R.id.rg_img_story_theme);
        mTvContent = (TextView) findViewById(R.id.tv_content);
        mTvShare = (TextView) findViewById(R.id.tv_share);
        mTvSave = (TextView) findViewById(R.id.tv_save);
        mRgImgStoryTheme.setOnCheckedChangeListener(this);
        mTvShare.setOnClickListener(this);
        mTvSave.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_img_story_theme_white:
                Toast.makeText(this, "优雅白", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rb_img_story_theme_black:
                Toast.makeText(this, "深邃黑", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_share:
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
                ShareHelper.shareMsg(this, "ImageStoryActivity", "范冰冰", "正文内容-放冰冰很美",
                        "/storage/emulated/0/123456.jpg");//MagazineUnlock/magazine-unlock-03-2.3.097-bigpicture_03_2
                break;
            case R.id.tv_save:
                Toast.makeText(this, "保存", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
