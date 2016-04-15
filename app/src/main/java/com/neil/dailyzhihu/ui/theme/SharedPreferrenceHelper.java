package com.neil.dailyzhihu.ui.theme;

import android.content.Context;
import android.content.SharedPreferences;

import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.R;

public class SharedPreferrenceHelper {

    public static void settheme(Context context, String theme) {
        SharedPreferences sp = context.getSharedPreferences(Constant.SHARED_PREFERANCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Constant.DAY_MODE_THEME, theme);
        editor.commit();
    }

    public static String gettheme(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constant.SHARED_PREFERANCE_NAME, Context.MODE_PRIVATE);
        return sp.getString(Constant.DAY_MODE_THEME, Constant.DEFAULT_TIME_THEME);
    }

    public static void switchAppTheme(Context context) {
        String value = SharedPreferrenceHelper.gettheme(context);
        switch (value) {
            case Constant.NIGHT_TIME_THEME:
                SharedPreferrenceHelper.settheme(context, Constant.DAY_TIME_THEME);
                break;
            case Constant.DAY_TIME_THEME:
                SharedPreferrenceHelper.settheme(context, Constant.NIGHT_TIME_THEME);
                break;
        }
    }

    public static int getAppTheme(Context context) {
        String value = SharedPreferrenceHelper.gettheme(context);
        switch (value) {
            case Constant.NIGHT_TIME_THEME:
                return R.style.DayTimeModeTheme;
            case Constant.DAY_TIME_THEME:
                return R.style.NightTimeModeTheme;
        }
        return R.style.DayTimeModeTheme;
    }
}