package com.neil.dailyzhihu.utils.share;

import com.neil.dailyzhihu.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.douban.Douban;
import cn.sharesdk.evernote.Evernote;
import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.google.GooglePlus;
import cn.sharesdk.pocket.Pocket;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tumblr.Tumblr;
import cn.sharesdk.twitter.Twitter;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import cn.sharesdk.youdao.YouDao;

/**
 * 作者：Neil on 2017/3/4 15:53.
 * 邮箱：cn.neillee@gmail.com
 */

public class PlatformInfoUtils {
    public enum SharePlatformSeries {
        TENCENT(0, "微信、QQ分享"),
        SINA(1, "新浪微博分享"),
        FORERIGN(2, "墙内软件分享"),
        NATIVE(3, "墙外软件分享");
        int id;
        String title;

        SharePlatformSeries(int id, String title) {
            this.id = id;
            this.title = title;
        }

        public static String getTitle(int id) {
            switch (id) {
                case 0:
                    return TENCENT.title;
                case 1:
                    return SINA.title;
                case 2:
                    return FORERIGN.title;
                case 3:
                    return NATIVE.title;
            }
            return "";
        }
    }

    public enum SharePlatformInfo {
        /*腾讯系*/
        QQ("QQ", R.drawable.ssdk_oks_classic_qq),
        QQ_ZONE("QQ空间", R.drawable.ssdk_oks_classic_qzone),
        WECHAT_FRIENDS("微信好友", R.drawable.ssdk_oks_classic_wechat),
        WECHAT_MOMENTS("朋友圈", R.drawable.ssdk_oks_classic_wechatmoments),

        /*微博*/
        SINA_WEIBO("新浪微博", R.drawable.ssdk_oks_classic_sinaweibo),

        /*墙内软件*/
        EVERNOTE("Evernote", R.drawable.ssdk_oks_classic_evernote),
        DOUBAN("豆瓣", R.drawable.ssdk_oks_classic_douban),
        RENREN("Pocket", R.drawable.ssdk_oks_classic_pocket),
        YOUDAO("有道", R.drawable.ssdk_oks_classic_youdao),

        /*墙外软件*/
        GOOGLE_PLUS("Google plus", R.drawable.ssdk_oks_classic_googleplus),
        TWITTER("Twitter", R.drawable.ssdk_oks_classic_twitter),
        TUMBLR("Tumblr", R.drawable.ssdk_oks_classic_tumblr),
        FACEBOOK("Facebook", R.drawable.ssdk_oks_classic_facebook);

        String name;
        int resId;

        SharePlatformInfo(String name, int resId) {
            this.name = name;
            this.resId = resId;
        }
    }

    public static List<Map<String, Object>> getShareData(int series) {
        List<Map<String, Object>> mShareData = new ArrayList<>();

        switch (series) {
            case 0:
                Map<String, Object> wechatFriends = new HashMap<>();
                wechatFriends.put("text", SharePlatformInfo.WECHAT_FRIENDS.name);
                wechatFriends.put("drawable", SharePlatformInfo.WECHAT_FRIENDS.resId);
                wechatFriends.put("type", Wechat.NAME);
                Map<String, Object> wechatMoments = new HashMap<>();
                wechatMoments.put("text", SharePlatformInfo.WECHAT_MOMENTS.name);
                wechatMoments.put("drawable", SharePlatformInfo.WECHAT_MOMENTS.resId);
                wechatMoments.put("type", WechatMoments.NAME);
                Map<String, Object> qqMap = new HashMap<>();
                qqMap.put("text", SharePlatformInfo.QQ.name);
                qqMap.put("drawable", SharePlatformInfo.QQ.resId);
                qqMap.put("type", QQ.NAME);
                Map<String, Object> qzoneMap = new HashMap<>();
                qzoneMap.put("text", SharePlatformInfo.QQ_ZONE.name);
                qzoneMap.put("drawable", SharePlatformInfo.QQ_ZONE.resId);
                qzoneMap.put("type", QZone.NAME);

                mShareData.add(wechatFriends);
                mShareData.add(wechatMoments);
                mShareData.add(qqMap);
                mShareData.add(qzoneMap);
                break;
            case 1:
                Map<String, Object> sinaMap = new HashMap<>();
                sinaMap.put("text", SharePlatformInfo.SINA_WEIBO.name);
                sinaMap.put("drawable", SharePlatformInfo.SINA_WEIBO.resId);
                sinaMap.put("type", SinaWeibo.NAME);
                mShareData.add(sinaMap);
                break;
            case 2:
                Map<String, Object> evernote = new HashMap<>();
                evernote.put("text", SharePlatformInfo.EVERNOTE.name);
                evernote.put("drawable", SharePlatformInfo.EVERNOTE.resId);
                evernote.put("type", Evernote.NAME);
                Map<String, Object> douban = new HashMap<>();
                douban.put("text", SharePlatformInfo.DOUBAN.name);
                douban.put("drawable", SharePlatformInfo.DOUBAN.resId);
                douban.put("type", Douban.NAME);
                Map<String, Object> pocket = new HashMap<>();
                pocket.put("text", SharePlatformInfo.RENREN.name);
                pocket.put("drawable", SharePlatformInfo.RENREN.resId);
                pocket.put("type", Pocket.NAME);
                Map<String, Object> youdao = new HashMap<>();
                youdao.put("text", SharePlatformInfo.YOUDAO.name);
                youdao.put("drawable", SharePlatformInfo.YOUDAO.resId);
                youdao.put("type", YouDao.NAME);

                mShareData.add(evernote);
                mShareData.add(douban);
                mShareData.add(pocket);
                mShareData.add(youdao);
                break;
            case 3:
                Map<String, Object> googlePlus = new HashMap<>();
                googlePlus.put("text", SharePlatformInfo.GOOGLE_PLUS.name);
                googlePlus.put("drawable", SharePlatformInfo.GOOGLE_PLUS.resId);
                googlePlus.put("type", GooglePlus.NAME);
                Map<String, Object> twitter = new HashMap<>();
                twitter.put("text", SharePlatformInfo.TWITTER.name);
                twitter.put("drawable", SharePlatformInfo.TWITTER.resId);
                twitter.put("type", Twitter.NAME);
                Map<String, Object> faccebook = new HashMap<>();
                faccebook.put("text", SharePlatformInfo.FACEBOOK.name);
                faccebook.put("drawable", SharePlatformInfo.FACEBOOK.resId);
                faccebook.put("type", Facebook.NAME);
                Map<String, Object> tumblr = new HashMap<>();
                tumblr.put("text", SharePlatformInfo.TUMBLR.name);
                tumblr.put("drawable", SharePlatformInfo.TUMBLR.resId);
                tumblr.put("type", Tumblr.NAME);

                mShareData.add(googlePlus);
                mShareData.add(twitter);
                mShareData.add(tumblr);
                mShareData.add(faccebook);
                break;
        }
        return mShareData;
    }
}
