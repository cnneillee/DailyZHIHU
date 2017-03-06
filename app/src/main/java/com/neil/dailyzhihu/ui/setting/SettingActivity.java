package com.neil.dailyzhihu.ui.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.neil.dailyzhihu.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：Neil on 2017/3/5 20:42.
 * 邮箱：cn.neillee@gmail.com
 */

public class SettingActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private View.OnClickListener upBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
        mToolbar.setNavigationOnClickListener(upBtnListener);

        getFragmentManager().beginTransaction().replace(R.id.fl_setting_fragment,new SettingFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
