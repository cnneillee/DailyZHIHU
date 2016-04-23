package com.neil.dailyzhihu.ui.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.neil.dailyzhihu.R;


/**
 * Created by Neil on 2016/4/22.
 */
public class CommentsPopupwindow extends PopupWindow {
    public CommentsPopupwindow(Context context, String[] items, AdapterView.OnItemClickListener listener) {
        super(context);
        View contentView = LayoutInflater.from(context).inflate(R.layout.popupwindow_comments_menu, null);
        ListView lv = (ListView) contentView.findViewById(R.id.lv_pwmenu);
        lv.setAdapter(new ArrayAdapter<>(context, R.layout.item_lv_menu_small_text, items));
        lv.setOnItemClickListener(listener);
        this.setContentView(contentView);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(android.support.design.R.anim.abc_grow_fade_in_from_bottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x9A000000);
        this.setBackgroundDrawable(dw);
        this.setOutsideTouchable(true);
    }
}
