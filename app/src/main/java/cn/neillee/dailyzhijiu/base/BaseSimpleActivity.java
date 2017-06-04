package cn.neillee.dailyzhijiu.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cn.neillee.dailyzhijiu.R;

import cn.neillee.dailyzhijiu.Constant;
import cn.neillee.dailyzhijiu.utils.AppUtil;
import cn.neillee.dailyzhijiu.utils.Settings;

/**
 * 作者：Neil on 2017/3/19 19:59.
 * 邮箱：cn.neillee@gmail.com
 */

public abstract class BaseSimpleActivity extends AppCompatActivity {

    public Settings mSettings = Settings.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Settings
        Settings.isExitConfirm = mSettings.getBoolean(Settings.EXIT_CONFIRM, true);
        Settings.isNightMode = mSettings.getBoolean(Settings.NIGHT_MODE, false);
        Settings.noPicMode = mSettings.getBoolean(Settings.NO_PIC_MODE, false);

        // change Brightness
        if (Settings.isNightMode && AppUtil.getSysScreenBrightness() > Constant.NIGHT_BRIGHTNESS) {
            AppUtil.setSysScreenBrightness(Constant.NIGHT_BRIGHTNESS);
        } else if (!Settings.isNightMode && AppUtil.getSysScreenBrightness() == Constant.NIGHT_BRIGHTNESS) {
            AppUtil.setSysScreenBrightness(Constant.DAY_BRIGHTNESS);
        }

        // theme
        this.setTheme(Settings.isNightMode ? R.style.AppNightTheme : R.style.AppDayTheme);

        // Language
        int lang = AppUtil.getCurrentLanguage();
        if (lang > -1) AppUtil.changeLanguage(this, lang);

        setContentView(getLayoutID());
        initViews();
    }

    protected abstract int getLayoutID();

    protected abstract void initViews();

    protected void setupToolbar(Toolbar toolbar) {
        setupToolbar(toolbar, "");
    }

    protected void setupToolbar(Toolbar toolbar, String title) {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(title != null);
        if (title != null) actionBar.setTitle(title.equals("") ? getTitle() : title);
    }

    protected void setupToolbar(Toolbar toolbar, int titleId) {
        setupToolbar(toolbar, getString(titleId));
    }

    protected void changeNightMode() {
        Settings.isNightMode = !Settings.isNightMode;
        mSettings.putBoolean(Settings.NIGHT_MODE, Settings.isNightMode);
        this.recreate();
    }

    protected void changeExitConfirm() {
        Settings.isExitConfirm = !Settings.isExitConfirm;
        this.mSettings.putBoolean(Settings.EXIT_CONFIRM, Settings.isExitConfirm);
    }

    protected void changeNoPicMode() {
        Settings.noPicMode = !Settings.noPicMode;
        this.mSettings.putBoolean(Settings.NO_PIC_MODE, Settings.noPicMode);
    }

    protected void changeNeedRecreated() {
        Settings.needRecreate = true;
    }
}
