package com.neil.dailyzhihu.utils.share;

import android.util.Log;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by Neil on 2016/4/20.
 */
public class Util {
    public final static String QQ_NAME = "QQ.NAME";
    public final static String QZONE_NAME = "QZONE.NAME";
    public final static String SINAWEIBO_NAME = "SinaWeibo.NAME";
    public final static String WECHAT_MOMENTS = "WechatMoments.NAME";
    public final static String WECHAT_FRIEND = "WechatShare.NAME";
    private static final String LOG_TAG = "Util";

    public static Platform.ShareParams buildShareParams(String imageUrl) {
        Log.e(LOG_TAG, "imageUrl- " + imageUrl);
        return buildShareParams(null, imageUrl);
    }

    public static Platform.ShareParams buildShareParams(String text, String imageUrl) {
        if (imageUrl == null)
            return null;
        Platform.ShareParams sp = new Platform.ShareParams();
        if (text != null)
            sp.setText(text); //分享文本
        if (imageUrl.contains("http://") || imageUrl.contains("https://"))
            sp.setImageUrl(imageUrl); //分享网络图片
        else
            sp.setImagePath(imageUrl); //分享本地图片
        Log.e(LOG_TAG, "imageUrl- " + imageUrl);
        return sp;
    }


    public static Platform getSharePlatform(String name, PlatformActionListener listener) {
        Platform platform = null;
        switch (name) {
            case QQ_NAME:
                platform = ShareSDK.getPlatform(QQ.NAME);
                break;
            case QZONE_NAME:
                platform = ShareSDK.getPlatform(QZone.NAME);
                break;
            case SINAWEIBO_NAME:
                platform = ShareSDK.getPlatform(SinaWeibo.NAME);
                break;
            case WECHAT_MOMENTS:
                platform = ShareSDK.getPlatform(WechatMoments.NAME);
                break;
            case WECHAT_FRIEND:
                platform = ShareSDK.getPlatform(cn.sharesdk.wechat.friends.Wechat.NAME);
                break;
        }
        return platform;
    }

    public static void appendShareParamsImg(Platform.ShareParams sp, String imgUrl) {
        if (sp == null)
            sp = new Platform.ShareParams();
        sp.setImageUrl(imgUrl);
    }

    public static void appendShareParamsTitle(Platform.ShareParams sp, String title) {
        if (sp == null)
            sp = new Platform.ShareParams();
        sp.setTitle(title);
    }

    public static void appendShareParamsTitleUrl(Platform.ShareParams sp, String titleUrl) {
        if (sp == null)
            sp = new Platform.ShareParams();
        sp.setTitleUrl(titleUrl);
    }

    public static void appendShareParamsText(Platform.ShareParams sp, String text) {
        if (sp == null)
            sp = new Platform.ShareParams();
        sp.setText(text);
    }

    public static void appendShareParamsMusicUrl(Platform.ShareParams sp, String musicUrl) {
        if (sp == null)
            sp = new Platform.ShareParams();
        sp.setText(musicUrl);
    }

    public static void superShareParams(Platform.ShareParams sp, String title, String titleUrl, String text, String musicUrl, String imgUrl, String imgUri) {

        sp.setText(text);
        sp.setTitle(title);
    }

    public static void excute(Platform pm, Platform.ShareParams sp) {
        // 执行分享
        if (pm != null && sp != null) {
            pm.authorize();
            pm.share(sp);
            Log.e(LOG_TAG, pm.getName() + " pm excuted");
        }
    }
}
