package com.neil.dailyzhihu.utils.update;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import com.neil.dailyzhihu.model.http.api.API;
import com.neil.dailyzhihu.model.bean.orignal.UpdateInfoBean;
import com.neil.dailyzhihu.listener.OnContentLoadedListener;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.load.LoaderFactory;
import com.orhanobut.logger.Logger;

/**
 * 更新管理器
 * <p>
 * Created by wangchenlong on 16/1/6.
 */
@SuppressWarnings("unused")
public class UpdateAppUtils {

    @SuppressWarnings("unused")
    private static final String TAG = "DEBUG-WCL: " + UpdateAppUtils.class.getSimpleName();

    /**
     * 检查更新
     */
    @SuppressWarnings("unused")
    public static void checkUpdate(final UpdateCallback updateCallback) {
        LoaderFactory.getContentLoader().loadContent(API.CHECK_FOR_UPDATES, new OnContentLoadedListener() {
            @Override
            public void onSuccess(String content, String url) {
                UpdateInfoBean updateInfoBean = GsonDecoder.getDecoder().decoding(content, UpdateInfoBean.class);
                onNext(updateInfoBean, updateCallback);
            }
        });
    }

    // 显示信息
    private static void onNext(UpdateInfoBean updateInfo, UpdateCallback updateCallback) {
        Logger.t(TAG).e("返回数据: " + updateInfo.toString());
        if (updateInfo.getUrl() == null) {
            updateCallback.onError(); // 失败
        } else {
            updateCallback.onSuccess(updateInfo);
        }
    }



    // 错误信息
    private static void onError(Throwable throwable, UpdateCallback updateCallback) {
        updateCallback.onError();
    }

    /**
     * 下载Apk, 并设置Apk地址,
     * 默认位置: /storage/sdcard0/Download
     *
     * @param context    上下文
     * @param updateInfo 更新信息
     * @param infoName   通知名称
     * @param storeApk   存储的Apk
     */
    @SuppressWarnings("unused")
    public static void downloadApk(Context context, UpdateInfoBean updateInfo, String infoName, String storeApk) {
        if (!isDownloadManagerAvailable()) {
            return;
        }

        String description = updateInfo.getDescription();
        String appUrl = updateInfo.getUrl();

        if (appUrl == null || appUrl.isEmpty()) {
            Logger.t(TAG).e("请填写\"App下载地址\"");
            return;
        }

        appUrl = appUrl.trim(); // 去掉首尾空格

        if (!appUrl.startsWith("http")) {
            appUrl = "http://" + appUrl; // 添加Http信息
        }

        Logger.t(TAG).e("appUrl: " + appUrl);

        DownloadManager.Request request;
        try {
            request = new DownloadManager.Request(Uri.parse(appUrl));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        request.setTitle(infoName);
        request.setDescription(description);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, storeApk);

        Context appContext = context.getApplicationContext();
        DownloadManager manager = (DownloadManager)
                appContext.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    // 最小版本号大于9
    private static boolean isDownloadManagerAvailable() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    // 错误回调
    public interface UpdateCallback {
        void onSuccess(UpdateInfoBean updateInfo);
        void onError();
    }
}