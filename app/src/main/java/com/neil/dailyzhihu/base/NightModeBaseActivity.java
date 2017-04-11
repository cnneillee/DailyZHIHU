package com.neil.dailyzhihu.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.utils.AppUtil;
import com.neil.dailyzhihu.utils.Settings;

/**
 * 作者：Neil on 2017/3/19 19:59.
 * 邮箱：cn.neillee@gmail.com
 */

public abstract class NightModeBaseActivity extends AppCompatActivity {

    public Settings mSettings = Settings.getInstance();
    private int mLang = -1;

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

        if (Settings.isNightMode) {
            this.setTheme(R.style.AppNightTheme);
        } else {
            this.setTheme(R.style.AppDayTheme);
        }

        // Language
        mLang = AppUtil.getCurrentLanguage();
        if (mLang > -1) {
            AppUtil.changeLanguage(this, mLang);
        }
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Settings.needRecreate) {
            Settings.needRecreate = false;
            this.recreate();
        }
    }

    protected abstract void initViews();

    protected void changeNightMode() {
        Settings.isNightMode = !Settings.isNightMode;
        mSettings.putBoolean(Settings.NIGHT_MODE, Settings.isNightMode);
        this.recreate();
    }

    protected void changeLanguage() {
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
