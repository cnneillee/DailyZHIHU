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
import android.widget.SimpleAdapter;

import com.neil.dailyzhihu.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Neil on 2016/4/22.
 */
public class StoryFabMenuPopupwindow extends PopupWindow {
    private static final String MENU_DRAWABLE_RES_ID = "drawable";
    private static final String MENU_STRING = "string";

    public StoryFabMenuPopupwindow(Context context, String[] items, int[] resIds, AdapterView.OnItemClickListener listener) {
        super(context);
        View contentView = LayoutInflater.from(context).inflate(R.layout.popupwindow_comments_menu, null);
        ListView lv = (ListView) contentView.findViewById(R.id.lv_pwmenu);
        List<Map<String, Object>> data = getMenuData(items, resIds);
        lv.setAdapter(new SimpleAdapter(context, data, R.layout.item_menu_popupwindow, new String[]{MENU_DRAWABLE_RES_ID, MENU_STRING}, new int[]{R.id.iv_menu, R.id.tv_menu}));
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

    private List<Map<String, Object>> getMenuData(String[] items, int[] imageId) {
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < items.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put(MENU_DRAWABLE_RES_ID, imageId[i]);
            map.put(MENU_STRING, items[i]);
            data.add(map);
        }
        return data;
    }
}
