package cn.neillee.dailyzhijiu.utils;

import android.content.Context;

/**
 * 作者：Neil on 2016/5/8 13:52.
 * 邮箱：cn.neillee@gmail.com
 */
public class DisplayUtil {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
