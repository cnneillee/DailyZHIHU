package com.neil.dailyzhihu.utils.share;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by Neil on 2016/4/20.
 */
public class QQShare {
    private static final String LOG_TAG = "QQShare";

    public static void qqShareImg(Context context, PlatformActionListener listener, String imageUri, String imageUrl, String type) {
        //初始化
        ShareSDK.initSDK(context);
        Platform pm = Util.getSharePlatform(type, listener);
        Platform.ShareParams sp;
        if (!TextUtils.isEmpty(imageUri))//使用本地图片
            sp = Util.buildShareParams(imageUri);
        else
            sp = Util.buildShareParams(imageUrl);
        Util.excute(pm, sp);
    }


    public static void qqShareLink(Context context, PlatformActionListener listener, String title, String titleUrl, String text, String imageUri, String imageUrl, String type) {
        //初始化
        ShareSDK.initSDK(context);
        Platform pm = Util.getSharePlatform(type, listener);
        Platform.ShareParams sp;
        if (!TextUtils.isEmpty(imageUri))//使用本地图片
            sp = Util.buildShareParams(imageUri);
        else
            sp = Util.buildShareParams(imageUrl);
        sp.setTitle(title);
        sp.setTitleUrl(titleUrl);
        sp.setText(text);
        Util.excute(pm, sp);
    }

    public static void qqShareMusic(Context context, PlatformActionListener listener, String title, String titleUrl, String text, String imageUri, String imageUrl, String musicUrl, String type) {
        //初始化
        ShareSDK.initSDK(context);
        Platform pm = Util.getSharePlatform(type, listener);
        Platform.ShareParams sp;
        if (!TextUtils.isEmpty(imageUri))//使用本地图片
            sp = Util.buildShareParams(imageUri);
        else
            sp = Util.buildShareParams(imageUrl);
        sp.setTitle(title);
        sp.setTitleUrl(titleUrl);
        sp.setText(text);
        sp.setMusicUrl(musicUrl);
        Util.excute(pm, sp);
    }
}
