package com.neil.dailyzhihu.ui.aty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;

import com.neil.dailyzhihu.model.http.api.AtyExtraKeyConstant;
import com.neil.dailyzhihu.ui.main.MainActivity;
import com.neil.dailyzhihu.utils.Settings;

/**
 * 作者：Neil on 2017/3/23 11:48.
 * 邮箱：cn.neillee@gmail.com
 */

public class LogoSplashActivity extends Activity {
    Settings mSettings = Settings.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent();
        int splashSetting = mSettings.getInt(Settings.SPLASH_SETTING, 0);
        boolean firstTime = mSettings.getBoolean(Settings.FIRST_TIME, true);
        if (firstTime) {
            intent.setClass(LogoSplashActivity.this, GuideActivity.class);
            mSettings.putBoolean(Settings.FIRST_TIME, false);
        }else{
            if (splashSetting == 0) {
                intent.setClass(LogoSplashActivity.this, MainActivity.class);
            } else {
                intent.setClass(LogoSplashActivity.this, ImageSplashActivity.class);
                intent.putExtra(AtyExtraKeyConstant.SPLASH_TYPE, splashSetting);
            }
        }
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }
}
