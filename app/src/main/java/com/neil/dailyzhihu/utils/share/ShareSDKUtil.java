package com.neil.dailyzhihu.utils.share;

import android.content.Context;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

/**
 * 作者：Neil on 2017/3/5 21:28.
 * 邮箱：cn.neillee@gmail.com
 */

public class ShareSDKUtil {
    public static void shareText(Context context, PlatformActionListener actionListener, String platformName, String title, String content) {
        ShareSDK.initSDK(context);
        Platform platform = ShareSDK.getPlatform(platformName);
        if (platform == null) return;
        platform.setPlatformActionListener(actionListener);
        Platform.ShareParams params = new Platform.ShareParams();
        params.setShareType(Platform.SHARE_TEXT);
        params.setTitle(title);
        params.setText(content);
        platform.authorize();
        platform.share(params);
    }

    public static void shareImage(Context context, PlatformActionListener actionListener, String platformName, String title, String content, String imageUri) {
        ShareSDK.initSDK(context);
        Platform platform = ShareSDK.getPlatform(platformName);
        if (platform == null) return;
        platform.setPlatformActionListener(actionListener);
        Platform.ShareParams params = new Platform.ShareParams();
        params.setShareType(Platform.SHARE_IMAGE);
        params.setTitle(title);
        params.setText(content);
        if (imageUri.contains("http://") || imageUri.contains("https://")) {
            params.setImageUrl(imageUri);
        } else {
            params.setImagePath(imageUri);
        }
        platform.authorize();
        platform.share(params);
    }
}
