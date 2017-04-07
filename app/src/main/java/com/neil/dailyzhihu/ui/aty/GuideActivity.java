package com.neil.dailyzhihu.ui.aty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：Neil on 2017/4/4 02:41.
 * 邮箱：cn.neillee@gmail.com
 */

public class GuideActivity extends AppCompatActivity {
    @BindView(R.id.vp_guide)
    ViewPager mVpGuide;
    @BindView(R.id.iv_dot1)
    ImageView mIvDot1;
    @BindView(R.id.iv_dot2)
    ImageView mIvDot2;
    @BindView(R.id.iv_dot3)
    ImageView mIvDot3;
    @BindView(R.id.tv_jump)
    TextView mTvJump;

    private List<View> mViews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        setDots(1);
        mViews = new ArrayList<>();
        mViews.add(LayoutInflater.from(this).inflate(R.layout.guide_xml1, null, false));
        mViews.add(LayoutInflater.from(this).inflate(R.layout.guide_xml2, null, false));
        mViews.add(LayoutInflater.from(this).inflate(R.layout.guide_xml3, null, false));
        mVpGuide.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mViews.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mViews.get(position));
                return mViews.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mViews.get(position));
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });
        mVpGuide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setDots(position + 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTvJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setDots(int position) {
        mIvDot1.setEnabled(false);
        mIvDot2.setEnabled(false);
        mIvDot3.setEnabled(false);
        switch (position) {
            case 1:
                mIvDot1.setEnabled(true);
                break;
            case 2:
                mIvDot2.setEnabled(true);
                break;
            case 3:
                mIvDot3.setEnabled(true);
                break;
        }
    }
}
