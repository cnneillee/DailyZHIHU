package com.neil.dailyzhihu.utils.share;

import android.content.Context;
import android.text.TextUtils;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by Neil on 2016/4/20.
 */
public class SinaShare {
    private static final String LOG_TAG = "SinaShare";

    public static void sinaShareText(Context context, PlatformActionListener listener, String text) {
        //初始化
        ShareSDK.initSDK(context);
        Platform pm = Util.getSharePlatform(Util.SINAWEIBO_NAME, listener);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setText(text);
        Util.excute(pm, sp);
    }

    public static void sinaShareImg(Context context, PlatformActionListener listener, String text, String imageUri, String imageUrl) {
        //初始化
        ShareSDK.initSDK(context);
        Platform pm = Util.getSharePlatform(Util.SINAWEIBO_NAME, listener);
        Platform.ShareParams sp;
        if (!TextUtils.isEmpty(imageUri))//使用本地图片
            sp = Util.buildShareParams(imageUri);
        else
            sp = Util.buildShareParams(imageUrl);
        sp.setText(text);
        Util.excute(pm, sp);
    }

    public static void sinaShareLink(Context context, PlatformActionListener listener, String text, String imageUri, String imageUrl, String url) {
        //初始化
        ShareSDK.initSDK(context);
        Platform pm = Util.getSharePlatform(Util.SINAWEIBO_NAME, listener);
        Platform.ShareParams sp;
        if (!TextUtils.isEmpty(imageUri))//使用本地图片
            sp = Util.buildShareParams(imageUri);
        else
            sp = Util.buildShareParams(imageUrl);
        sp.setText(text);
        sp.setUrl(url);
        Util.excute(pm, sp);
    }
}
