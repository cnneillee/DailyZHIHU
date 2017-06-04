package cn.neillee.dailyzhijiu;

import android.os.Environment;

import java.io.File;

import cn.neillee.dailyzhijiu.app.DailyApp;

/**
 * 作者：Neil on 2016/3/23 01:09.
 * 邮箱：cn.neillee@gmail.com
 */
public class Constant {

    public static final int START_ACTY_LAST_MILLIES = 2000;

    public static final int MIN_YEAR_OF_PAST_STORY = 2013;
    public static final int MIN_MONTH_OF_YEAR_OF_PAST_STORY = 5;
    public static final int MIN_DAY_OF_MONTH_OF_PAST_STORY = 20;

    public static final int NIGHT_BRIGHTNESS = 40;
    public static final int DAY_BRIGHTNESS = 150;

    public static final int EXIT_CONFIRM_TIME = 2000;

    //================= PATH ====================

    public static final String PATH_DATA = DailyApp.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "codeest" + File.separator + "GeekNews";

    // buglyID
    public static final String BUGLY_ID_KEY = "BUGLY_APPID";
}
