package com.neil.dailyzhihu.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import com.neil.dailyzhihu.MyApplication;

/**
 * 作者：Neil on 2017/3/5 17:34.
 * 邮箱：cn.neillee@gmail.com
 */

public class AppUtil {

    private static final String LOG_TAG = AppUtil.class.getSimpleName();
    private static Context mContext = MyApplication.AppContext;

    /**
     * 获取版本号信息
     *
     * @param context 上下文环境
     * @return 版本号信息
     */
    public static String getVersionInfo(Context context) {
        String versionCode;
        try {
            PackageInfo pkginfo = context.getPackageManager().getPackageInfo(((Activity) context).getApplication().getPackageName(), 0);
            versionCode = "当前版本号：" + pkginfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            versionCode = "版本号获取失败";
            e.printStackTrace();
        }
        return versionCode;
    }

    public static void copyText2Clipboard(Context context,String content){
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", content);
        cm.setPrimaryClip(clipData);
    }

    /**
     * 获得当前系统的亮度值： 0~255
     */
    /** 可调节的最大亮度值 */
    public static final int MAX_BRIGHTNESS = 255;

    public static int getSysScreenBrightness() {
        int screenBrightness = MAX_BRIGHTNESS;
        try {
            screenBrightness = android.provider.Settings.System.getInt(mContext.getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            Log.e(LOG_TAG,"获取系统亮度失败："+e);
        }
        return screenBrightness;
    }

    /**
     * 设置当前系统的亮度值:0~255
     */
    public static void setSysScreenBrightness(int brightness) {
        try {
            ContentResolver resolver = mContext.getContentResolver();
            Uri uri = android.provider.Settings.System.getUriFor(android.provider.Settings.System.SCREEN_BRIGHTNESS);
            android.provider.Settings.System.putInt(resolver, android.provider.Settings.System.SCREEN_BRIGHTNESS, brightness);
            resolver.notifyChange(uri, null); // 实时通知改变
        } catch (Exception e) {
            Log.e(LOG_TAG,"获取系统亮度失败："+e);
        }
    }
}
