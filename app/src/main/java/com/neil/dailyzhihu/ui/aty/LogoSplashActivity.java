package com.neil.dailyzhihu.ui.aty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;

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
        Settings.hasImgSplash = mSettings.getBoolean(Settings.HAS_IMG_SPLASH, true);
        Intent intent = new Intent();
        if (Settings.hasImgSplash) {
            intent.setClass(LogoSplashActivity.this, ImageSplashActivity.class);
        } else {
            intent.setClass(LogoSplashActivity.this, MainActivity.class);
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
