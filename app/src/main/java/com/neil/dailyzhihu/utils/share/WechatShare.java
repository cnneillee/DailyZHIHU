package com.neil.dailyzhihu.utils.share;

import android.content.Context;
import android.text.TextUtils;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by Neil on 2016/4/20.
 * <p/>
 * 绕过审核只对微信好友、微信朋友圈有效
 * 微信分享如果是绕过审核(配置信息BypassApproval属性设置为true为绕过审核),
 * 微信朋友圈可以分享单张图片或者图片与文字一起分享，
 * 微信好友可以进行文字或者单张图片或者文件进行分享,
 * 分享回调不会正确回调。 不绕过审核，微信三个平台中，
 * 好友的功能最完整，朋友圈不能分享表情、文件和应用，
 * 收藏不能分享表情和应用，表格下以好友为例子：
 * 参数说明 title：512Bytes以内
 * text：1KB以内
 * imageData：10M以内
 * imagePath：10M以内(传递的imagePath路径不能超过10KB)
 * imageUrl：10KB以内
 * musicUrl：10KB以内
 * url：10KB以内
 */
public class WechatShare {

    //分享文本
    public static void wechatShareText(Context context, PlatformActionListener listener, String title, String text, String type) {
        //初始化
        ShareSDK.initSDK(context);
        Platform pm = Util.getSharePlatform(type, listener);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setText(text);
        sp.setShareType(Platform.SHARE_TEXT);
        sp.setTitle(title);
        Util.excute(pm, sp);
    }

    //分享图片
    public static void wechatShareImg(Context context, PlatformActionListener listener, String title, String text, String imageUrl, String imageUri, String type) {
        //初始化
        ShareSDK.initSDK(context);
        Platform pm = Util.getSharePlatform(type, listener);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setShareType(Platform.SHARE_IMAGE);
        if (!TextUtils.isEmpty(text))
            sp.setText(text);//朋友圈不显示
        sp.setTitle(title);
        if (!TextUtils.isEmpty(imageUri))//使用本地图片
            sp = Util.buildShareParams(imageUri);
        else
            sp = Util.buildShareParams(imageUrl);
        Util.excute(pm, sp);
    }

    //分享图片
    public static void wechatShareWeb(Context context, PlatformActionListener listener, String title, String text, String imageUrl, String imageUri, String url, String type) {
        //初始化
        ShareSDK.initSDK(context);
        Platform pm = Util.getSharePlatform(type, listener);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        if (!TextUtils.isEmpty(text))
            sp.setText(text);
        sp.setTitle(title);
        if (!TextUtils.isEmpty(imageUri))//使用本地图片
            sp.setImagePath(imageUri);
        else
            sp.setImageUrl(imageUri);
        sp.setUrl(url);
        Util.excute(pm, sp);
    }
}
