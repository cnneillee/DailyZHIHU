package com.neil.dailyzhihu.ui.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.neil.dailyzhihu.R;

/**
 * Created by Neil on 2016/4/22.
 */
public class CommentAlertDialog extends android.app.AlertDialog {
    private ViewPager vp;

    public CommentAlertDialog(Context context, boolean cancelable, OnCancelListener cancelListener, PagerAdapter pagerAdapter, DialogInterface.OnClickListener onClickListener) {
        super(context, cancelable, cancelListener);
        View contentView = LayoutInflater.from(context).inflate(R.layout.view_comments, null, false);
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高
        float screenHeight = d.heightPixels;
        int dialogHeight = (int) (screenHeight * 0.75);
        this.show();
        this.setContentView(contentView);
        Window window = this.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.height = dialogHeight;
        window.setAttributes(params);
        ViewPager viewPager = (ViewPager) contentView.findViewById(R.id.vp_comment);
        viewPager.setAdapter(pagerAdapter);
        vp = viewPager;
        this.setTitle("查看评论");
        this.setButton(DialogInterface.BUTTON_NEGATIVE, "确定", onClickListener);
    }

    public ViewPager getVp() {
        return vp;
    }
}
