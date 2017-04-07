package com.neil.dailyzhihu.ui.setting;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.ui.NightModeBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：Neil on 2017/3/5 20:42.
 * 邮箱：cn.neillee@gmail.com
 */

public class SettingActivity extends NightModeBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private View.OnClickListener upBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
        mToolbar.setNavigationOnClickListener(upBtnListener);

        getFragmentManager().beginTransaction().replace(R.id.fl_setting_fragment, new SettingFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    public void callChangeNightMode() {
        this.changeNightMode();
        this.changeNeedRecreated();
    }

    public void callChangeExitConfirm() {
        this.changeExitConfirm();
    }

    public void callChangeNoPicMode() {
        this.changeNoPicMode();
        this.changeNeedRecreated();
    }
}
