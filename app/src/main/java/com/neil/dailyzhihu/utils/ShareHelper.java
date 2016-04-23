package com.neil.dailyzhihu.utils;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by Neil on 2016/4/19.
 * 两种分享场景：
 * 1、生成图片分享（只是单纯发图片）
 * 2、文章分享（小段文字+链接）
 */
public class ShareHelper {
    private final static String LOG_TAG = ShareHelper.class.getSimpleName();

    private final static String QQ_NAME = "QQ.NAME";
    private final static String SINAWEIBO_NAME = "SinaWeibo.NAME";
    private final static String WECHAT_MOMENTS = "WechatMoments.NAME";
    private final static String WECHAT_FRIEND = "WechatShare.NAME";

    private static Platform.ShareParams buildShareParams(String text, String imageUrl) {
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

    private static Platform.ShareParams buildShareParams(String imageUrl) {
        Log.e(LOG_TAG, "imageUrl- " + imageUrl);
        return buildShareParams(null, imageUrl);
    }

    private static Platform getSharePlatform(String name, PlatformActionListener listener) {
        Platform platform = null;
        switch (name) {
            case QQ_NAME:
                platform = ShareSDK.getPlatform(QQ.NAME);
                break;
            case SINAWEIBO_NAME:
                platform = ShareSDK.getPlatform(SinaWeibo.NAME);
                break;
            case WECHAT_MOMENTS:
                platform = ShareSDK.getPlatform(WechatMoments.NAME);
                break;
            case WECHAT_FRIEND:
                platform = ShareSDK.getPlatform(Wechat.NAME);
                break;
        }
        return platform;
    }

    private static void universalPlatformShare(Context context, String text, String imageUrl, String platformName, PlatformActionListener listener) {
        //初始化
        ShareSDK.initSDK(context);
        //设置分享参数
        Platform.ShareParams sp = buildShareParams(text, imageUrl);
        //3、非常重要：获取平台对象
        Platform pm = getSharePlatform(platformName, listener);
        // 执行分享
        if (pm != null && sp != null) {
            pm.authorize();
            pm.share(sp);
            Log.e(LOG_TAG, platformName + "pm excuted successful");
        }
    }

    public static void sinaWeiboShare(Context context, String text, String imageUrl, PlatformActionListener listener) {
        universalPlatformShare(context, text, imageUrl, SINAWEIBO_NAME, listener);
    }

    private static void excute(Platform pm, Platform.ShareParams sp) {
        // 执行分享
        if (pm != null && sp != null) {
            pm.authorize();
            pm.share(sp);
            Log.e(LOG_TAG, pm.getName() + " pm excuted");
        }
    }

    public static void qqShare(Context context, String text, String imageUrl, PlatformActionListener listener) {
        universalPlatformShare(context, text, imageUrl, QQ_NAME, listener);
    }

    public static void weChatFriendShare(Context context, String title, String text, String imageUrl, PlatformActionListener listener) {
        //初始化
        ShareSDK.initSDK(context);
        Platform pm = getSharePlatform(WECHAT_FRIEND, listener);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setShareType(Platform.SHARE_TEXT);
        if (imageUrl.contains("http://") || imageUrl.contains("https://"))
            sp.setImageUrl(imageUrl); //分享网络图片
        else
            sp.setImagePath(imageUrl); //分享本地图片
        sp.setTitle(title);
        sp.setText(text);
        // 执行分享
        if (pm != null && sp != null) {
            pm.authorize();
            pm.share(sp);
            Log.e(LOG_TAG, "weChatFriendShare" + "pm excuted successful");
        }
    }

    public static void weChatCircle(Context context, String title, String text, String imageUrl, PlatformActionListener listener) {
        //初始化
        ShareSDK.initSDK(context);
        Platform pm = getSharePlatform(WECHAT_MOMENTS, listener);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setShareType(Platform.SHARE_TEXT);
        if (imageUrl.contains("http://") || imageUrl.contains("https://"))
            sp.setImageUrl(imageUrl); //分享网络图片
        else
            sp.setImagePath(imageUrl); //分享本地图片
        sp.setTitle(title);
        sp.setText(text);
        // 执行分享
        if (pm != null && sp != null) {
            pm.authorize();
            pm.share(sp);
            Log.e(LOG_TAG, "weChatFriendShare" + "pm excuted successful");
        }
    }

    public static void saveToClipboard(String str, Context context) {
        ClipboardManager cbm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cbm.setText(str);
    }

    /**
     * 分享功能
     *
     * @param context       上下文
     * @param activityTitle Activity的名字
     * @param msgTitle      消息标题
     * @param msgText       消息内容
     * @param imgPath       图片路径，不分享图片则传null
     */
    public static void orignalMsgShare(Context context, String activityTitle, String msgTitle, String msgText,
                                       String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File f = new File(imgPath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/jpg");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
            Log.e(LOG_TAG, imgPath + f + String.valueOf(f.exists()) + f.isFile() + "");
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, activityTitle));
    }

    /**
     * 分享功能
     *
     * @param context       上下文
     * @param activityTitle Activity的名字
     * @param msgTitle      消息标题
     * @param msgText       消息内容
     * @param imgPath       图片路径，不分享图片则传null
     */
    public static void orignalMsgShareWithFilter(Context context, String activityTitle, String msgTitle, String msgText,
                                                 String imgPath, String filter) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent = filterIntent(intent, filter, context);
        if (intent != null)
            context.startActivity(intent);
        else
            Toast.makeText(context, "没有匹配的应用", Toast.LENGTH_SHORT).show();
    }

    private static Intent filterIntent(Intent sendIntent, String type, Context context) {
        boolean found = false;
        sendIntent.setType("image/jpeg");
        // gets the list of intents that can be loaded.
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(sendIntent, 0);
        if (!resInfo.isEmpty()) {
            for (ResolveInfo info : resInfo) {
                if (info.activityInfo.packageName.toLowerCase().contains(type) ||
                        info.activityInfo.name.toLowerCase().contains(type)) {
                    sendIntent.setPackage(info.activityInfo.packageName);
                    found = true;
                    break;
                }
            }
            if (found)
                return sendIntent;
        }
        return null;
    }

    public static void onKeyShareText(Context context, String title, String text, String imgUrl) {
        onKeyShare(context, title, text, "", imgUrl);
    }

    public static void onKeyShare(Context context, String title, String text, String imgPath, String imgUrl) {
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题：微信、QQ（新浪微博不需要标题）
        oks.setTitle(title);  //最多30个字符

        // text是分享文本：所有平台都需要这个字段
        oks.setText(text);  //最多40个字符

        // imagePath是图片的本地路径：除Linked-In以外的平台都支持此参数
        //oks.setImagePath(Environment.getExternalStorageDirectory() + "/meinv.jpg");//确保SDcard下面存在此张图片
        oks.setImagePath(imgPath);
        //网络图片的url：所有平台
        oks.setImageUrl(imgUrl);//网络图片rul

        // url：仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");   //网友点进链接后，可以看到分享的详情

        // Url：仅在QQ空间使用
        oks.setTitleUrl("http://www.baidu.com");  //网友点进链接后，可以看到分享的详情

        // 启动分享GUI
        oks.show(context);
    }
}
