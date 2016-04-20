package com.neil.dailyzhihu.ui.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;

import com.neil.dailyzhihu.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Neil on 2016/4/18.
 */
public class ShareMenuPopupWindow extends PopupWindow {
    private Context mContext;

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

    public ShareMenuPopupWindow(Context context, AdapterView.OnItemClickListener listener) {
        super(context);
        this.mContext = context;
        View popupView = LayoutInflater.from(mContext).inflate(R.layout.popup_share_menu, null, false);
        GridView gvShare = (GridView) popupView.findViewById(R.id.gv_sharePopup);
        List<HashMap<String, Object>> shareMenuData = getMenuData();
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

    public List<HashMap<String, Object>> getMenuData() {
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
