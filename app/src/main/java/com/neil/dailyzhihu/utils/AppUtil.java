package com.neil.dailyzhihu.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.app.DailyApp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * 作者：Neil on 2017/3/5 17:34.
 * 邮箱：cn.neillee@gmail.com
 */

public class AppUtil {

    private static final String LOG_TAG = AppUtil.class.getSimpleName();
    private static Context mContext = DailyApp.AppContext;

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
            versionCode = Formater.fromatUpdateVersionInfo(context, pkginfo.versionName, pkginfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            versionCode = context.getResources().getString(R.string.error_in_getting_version_info);
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取版本号
     *
     * @param context 上下文环境
     * @return 版本号信息
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            PackageInfo pkginfo = context.getPackageManager().getPackageInfo(((Activity) context).getApplication().getPackageName(), 0);
            versionCode = pkginfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static void copyText2Clipboard(Context context, String content) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", content);
        cm.setPrimaryClip(clipData);
    }

    /**
     * 获得当前系统的亮度值： 0~255
     */
    /**
     * 可调节的最大亮度值
     */
    public static final int MAX_BRIGHTNESS = 255;

    public static int getSysScreenBrightness() {
        int screenBrightness = MAX_BRIGHTNESS;
        try {
            screenBrightness = android.provider.Settings.System.getInt(mContext.getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            Log.e(LOG_TAG, "获取系统亮度失败：" + e);
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
            Log.e(LOG_TAG, "获取系统亮度失败：" + e);
        }
    }

    public static String bytes2kmgb(int bytes) {
        float result = (float) (bytes / 1024.0);
        if (result > 1024) {
            result = (float) (bytes / (1024 * 1024.0));
            if (result >= 1024) {
                return String.format("%.2f GB", bytes / (1024 * 1024.0 * 1024.0));
            } else {
                return String.format("%.2f MB", result);
            }
        } else {
            return String.format("%.2f KB", result);
        }
    }


    /**
     * 检查WIFI是否连接
     */
    public static boolean isWifiConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) DailyApp.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiInfo != null;
    }

    /**
     * 检查手机网络(4G/3G/2G)是否连接
     */
    public static boolean isMobileNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) DailyApp.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileNetworkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return mobileNetworkInfo != null;
    }

    /**
     * 检查是否有可用网络
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) DailyApp.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }

    /**
     * 保存图片到本地
     *
     * @param context
     * @param url
     * @param bitmap
     */
    public static Uri saveBitmapToFile(Context context, String url, Bitmap bitmap, View container, boolean isShare) {
        String fileName = url.substring(url.lastIndexOf("/"), url.lastIndexOf(".")) + ".png";
        File fileDir = new File(Constant.PATH_DATA);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
        File imageFile = new File(fileDir, fileName);
        Uri uri = Uri.fromFile(imageFile);
        if (isShare && imageFile.exists()) {
            return uri;
        }
        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            boolean isCompress = bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
            if (isCompress) {
                SnackbarUtil.ShortSnackbar(container, "保存妹纸成功n(*≧▽≦*)n", SnackbarUtil.Info);
            } else {
                SnackbarUtil.ShortSnackbar(container, "保存妹纸失败ヽ(≧Д≦)ノ", SnackbarUtil.Alert);
            }
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            SnackbarUtil.ShortSnackbar(container, "保存妹纸失败ヽ(≧Д≦)ノ", SnackbarUtil.Alert);
        }
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), imageFile.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        return uri;
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
