package com.neil.dailyzhihu.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.api.API;
import com.neil.dailyzhihu.bean.ShareRecord;
import com.neil.dailyzhihu.bean.orignal.CertainStoryBean;
import com.neil.dailyzhihu.ui.story.ImageStoryActivity;
import com.neil.dailyzhihu.api.AtyExtraKeyConstant;
import com.neil.dailyzhihu.utils.share.ShareHelper;
import com.neil.dailyzhihu.utils.share.QQShare;
import com.neil.dailyzhihu.utils.share.SinaShare;
import com.neil.dailyzhihu.utils.share.Util;
import com.neil.dailyzhihu.utils.share.WechatShare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * 作者：Neil on 2016/4/18 11:09.
 * 邮箱：cn.neillee@gmail.com
 */
public class ShareMenuPopupWindow extends PopupWindow {
    private Context mContext;
    private CertainStoryBean mCertainStoryBean;
    private FrameLayout mMainLayout;

    @SuppressLint("PrivateResource")
    public ShareMenuPopupWindow(Context context, AdapterView.OnItemClickListener listener, List<HashMap<String, Object>> shareMenuData) {
        super(context);
        this.mContext = context;
        View popupView = LayoutInflater.from(mContext).inflate(R.layout.popup_share_menu, null, false);
        GridView gvShare = (GridView) popupView.findViewById(R.id.gv_sharePopup);
        gvShare.setAdapter(new SimpleAdapter(context, shareMenuData, R.layout.item_popup_share_menu,
                new String[]{"shareImg", "shareStr"}, new int[]{R.id.iv_img, R.id.tv_intro}));
        gvShare.setOnItemClickListener(listener);
        //设置SelectPicPopupWindow的View
        this.setContentView(popupView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(android.support.design.R.anim.abc_grow_fade_in_from_bottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xbbbbbbbb);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
    }

    public ShareMenuPopupWindow(Context context, CertainStoryBean story, FrameLayout mainLayout) {
        super(context);
        this.mContext = context;
        this.mCertainStoryBean = story;
        this.mMainLayout = mainLayout;
        View popupView = LayoutInflater.from(mContext).inflate(R.layout.popup_share_menu, null, false);
        GridView gvShare = (GridView) popupView.findViewById(R.id.gv_sharePopup);
        List<HashMap<String, Object>> shareMenuData = getMenuData();
        gvShare.setAdapter(new SimpleAdapter(context, shareMenuData, R.layout.item_popup_share_menu,
                new String[]{"shareImg", "shareStr"}, new int[]{R.id.iv_img, R.id.tv_intro}));
        gvShare.setOnItemClickListener(mOnItemClickListener);
        //设置SelectPicPopupWindow的View
        this.setContentView(popupView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(android.support.design.R.anim.abc_grow_fade_in_from_bottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xbbbbbbbb);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //设置主界面的前景色
        mainLayout.setForeground(dw);
    }

    // 分享列表项点击事件
    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String title = mCertainStoryBean.getTitle();
            String shareUrl = mCertainStoryBean.getShareUrl();
            String img = mCertainStoryBean.getImage();
            String text = title + "\tmore? via zhihuDaily--->\t" + shareUrl;
            switch (position) {
                case 0://生成图片
                    Intent intent = new Intent(mContext, ImageStoryActivity.class);
                    intent.putExtra(AtyExtraKeyConstant.STORY_BODY, mCertainStoryBean.getBody());
                    intent.putExtra(AtyExtraKeyConstant.STORY_ID, mCertainStoryBean.getId());
                    mContext.startActivity(intent);
                    break;
                case 1://微信好友
                    WechatShare.wechatShareText(mContext, mListener, title, text, Util.WECHAT_FRIEND);
                    break;
                case 2://票圈
                    WechatShare.wechatShareText(mContext, mListener, title, text, Util.WECHAT_MOMENTS);
                    break;
                case 3://空间
                    QQShare.qqShareLink(mContext, mListener, title, shareUrl, text, "", img, Util.QZONE_NAME);
                    break;
                case 4://QQ
                    QQShare.qqShareLink(mContext, mListener, title, shareUrl, text, "", img, Util.QQ_NAME);
                    break;
                case 5://新浪微博
                    SinaShare.sinaShareLink(mContext, mListener, text, "", img, shareUrl);
                    break;
                case 6://复制链接
                    ShareHelper.saveToClipboard(shareUrl, mContext);
                    Toast.makeText(mContext, "成功复制到剪贴板", Toast.LENGTH_SHORT).show();
                    break;
                case 7://更多
                    String storyText = makeShareText();
                    if (storyText == null)
                        return;
                    Toast.makeText(mContext, "更多分享", Toast.LENGTH_SHORT).show();
                    ShareHelper.orignalMsgShare(mContext, "CertainStoryActivity", storyText, storyText, null);
                    ShareRecord shareRecord = new ShareRecord(mCertainStoryBean.getId(),
                            System.currentTimeMillis(), "UNKNOWN", "More-Link", "20160427");
                    Toast.makeText(mContext, "分享成功", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        private String makeShareText() {
            String shareText = null;
            if (mCertainStoryBean != null){
                shareText = String.format("%s->%s\nvia DailyZHIHU", mCertainStoryBean.getTitle(),
                        (API.STORY_PREFIX + mCertainStoryBean.getId()));
            }
            return shareText;
        }
    };

    private PlatformActionListener mListener = new PlatformActionListener() {
        /*
      * 分享回调事件（如下三个OnComplete、OnError、O）
      */
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            ShareRecord shareRecord = new ShareRecord(mCertainStoryBean.getId(), System.currentTimeMillis(),
                    platform.getName(), "SDKShare-Link", "20160427");
//        int resultCode = (int) DBFactory.getsIDBShareRecordDetailStoryTabledao(this).addShareRecord(shareRecord);
//        if (resultCode > 0)
            Toast.makeText(mContext, "分享成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
        }

        @Override
        public void onCancel(Platform platform, int i) {
        }

        public void share(CertainStoryBean story) {
            if (story == null)
                return;
            ShareHelper.onKeyShareText(mContext, story.getTitle(),
                    story.getTitle() + "via zhihuDaily" + story.getShareUrl(), story.getImage());
        }

    };

    @Override
    public void setOnDismissListener(OnDismissListener onDismissListener) {
        super.setOnDismissListener(onDismissListener);
        mMainLayout.setForeground(null);
    }

    private List<HashMap<String, Object>> getMenuData() {
        String[] menuShareStr = mContext.getResources().getStringArray(R.array.share_menu_name);
        //int[] menuShareImg = mContext.getResources().getIntArray(R.array.share_menu_img);
        int[] menuShareImg = {
                R.drawable.menu_share_pic_item,
                R.drawable.menu_share_wechat_item,
                R.drawable.menu_share_friendcircle_item,
                R.drawable.menu_share_qzone_item,
                R.drawable.menu_share_qq_item,
                R.drawable.menu_share_sina_item,
                R.drawable.menu_share_copylink_item,
                R.drawable.menu_share_more_item
        };
        List<HashMap<String, Object>> mMenuData = new ArrayList<>();
        for (int i = 0; i < menuShareImg.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("shareStr", menuShareStr[i]);
            map.put("shareImg", menuShareImg[i]);
            mMenuData.add(map);
        }
        return mMenuData;
    }
}
