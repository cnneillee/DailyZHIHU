package com.neil.dailyzhihu.ui.setting;

import android.support.v7.widget.Toolbar;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.base.BaseSimpleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：Neil on 2017/3/5 20:42.
 * 邮箱：cn.neillee@gmail.com
 */

public class SettingActivity extends BaseSimpleActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);

        setupToolbar(mToolbar, R.string.activity_setting);

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

    public void callChangeLanguage() {
        this.recreate();
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
