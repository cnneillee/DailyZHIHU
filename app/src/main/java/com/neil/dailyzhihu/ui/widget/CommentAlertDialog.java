package com.neil.dailyzhihu.ui.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.CommentTypesPagerAdapter;

/**
 * 作者：Neil on 2016/4/22 22:49.
 * 邮箱：cn.neillee@gmail.com
 */
public class CommentAlertDialog extends android.app.AlertDialog {
    private ViewPager vp;

    public CommentAlertDialog(Context context, String storyId) {
        super(context, true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
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
        viewPager.setAdapter(new CommentTypesPagerAdapter(context, storyId));
        vp = viewPager;
        this.setTitle("查看评论");
    }
}
