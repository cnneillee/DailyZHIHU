package com.neil.dailyzhihu.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.neil.dailyzhihu.MyApplication;

/**
 * 作者：Neil on 2016/4/16 23:50.
 * 邮箱：cn.neillee@gmail.com
 */
public class Settings {

    public static boolean noPicMode = false;
    public static boolean isNightMode = false;
    public static boolean isExitConfirm = true;
    public static boolean needRecreate = false;
    public static boolean hasImgSplash = false;

    public static final String XML_NAME = "settings";

    public static final String NO_PIC_MODE = "no_pic_mode";

    public static final String NIGHT_MODE = "night_mode";

    public static final String EXIT_CONFIRM = "exit_confirm";

    public static final String CLEAR_CACHE = "clear_cache";

    public static final String HAS_IMG_SPLASH = "has_img_splash";

    private static Settings sInstance;

    private SharedPreferences mPrefs;

    public static Settings getInstance() {
        if (sInstance == null) {
            sInstance = new Settings(MyApplication.AppContext);
        }
        return sInstance;
    }

    private Settings(Context context) {
        mPrefs = context.getSharedPreferences(XML_NAME, Context.MODE_PRIVATE);
    }

    public Settings putBoolean(String key, boolean value) {
        mPrefs.edit().putBoolean(key, value).apply();
        return this;
    }

    public boolean getBoolean(String key, boolean def) {
        return mPrefs.getBoolean(key, def);
    }

    public Settings putInt(String key, int value) {
        mPrefs.edit().putInt(key, value).apply();
        return this;
    }

    public int getInt(String key, int defValue) {
        return mPrefs.getInt(key, defValue);
    }

    public Settings putString(String key, String value) {
        mPrefs.edit().putString(key, value).apply();
        return this;
    }

    public String getString(String key, String defValue) {
        return mPrefs.getString(key, defValue);
    }

}
