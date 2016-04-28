package com.neil.dailyzhihu.utils;

import android.text.format.DateFormat;
import android.text.format.DateUtils;

import java.util.Calendar;

/**
 * Created by Neil on 2016/4/28.
 * neillee
 */
public class DateUtil {
    //y-year,M-month,d-day,h-hour,m-minute,s-second
    public static String millies2yyyyMMDD(long millies) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millies);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
    }
    class DateEntity{

    }
}
