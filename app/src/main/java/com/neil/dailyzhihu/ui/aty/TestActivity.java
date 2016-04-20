package com.neil.dailyzhihu.ui.aty;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.neil.dailyzhihu.OneKeyShareCallback;
import com.neil.dailyzhihu.R;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.sina.weibo.SinaWeibo;


public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        System.out.println("创建");
        ShareSDK.initSDK(this);
    }

    public void share(View v) {
        Log.e("---comming---", "进入点击按钮事件");
        showShare();

    }

    // 添加分享调用代码
    private void showShare() {

        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();

        oks.setDialogMode();
        // 关闭sso授权
        oks.disableSSOWhenAuthorize();
        oks.addHiddenPlatform(SinaWeibo.NAME);

        // 分享时Notification的图标和文字
//        oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        // oks.setTitle(getString(R.string.share));
        oks.setTitle("微蚁");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://www.baidu.com");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是微博的文本");
        //oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/05/21/oESpJ78_533x800.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//				oks.setImagePath("/sdcard/12-1.jpg");//使用这张图片必须保证您的SDcard下面存在这样的一张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://money.bmob.cn");

//				oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/05/21/oESpJ78_533x800.jpg");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("分享应用");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://www.baidu.com");

        OneKeyShareCallback callback = new OneKeyShareCallback();
        //oks.setCallback(callback);

        oks.setSilent(false);

//				 通过OneKeyShareCallback来修改不同平台分享的内容
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            public void onShare(Platform platform, cn.sharesdk.framework.Platform.ShareParams paramsToShare) {
                System.out.println("不是短信的话就进入");
                if (!platform.getName().equals("ShortMessage")) {
                    paramsToShare.setText("我的");
                    paramsToShare.setImageUrl("http://f1.sharesdk.cn/imgs/2014/05/21/oESpJ78_533x800.jpg");
                }
            }
        });
//				 启动分享GUI
        oks.show(this);
    }


    protected void onStop() {
        super.onStop();
        ShareSDK.stopSDK();
    }


}
