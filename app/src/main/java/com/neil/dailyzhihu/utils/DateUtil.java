package com.neil.dailyzhihu.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Neil on 2016/4/28.
 * neillee
 */
public class DateUtil {
    //y-year,M-month,d-day,h-hour,m-minute,s-second
    public static String millies2yyyyMMDD(long millies) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millies);
        return calendar2yyyyMMDD(calendar);
    }

    public static String calendar2yyyyMMDD(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return year + (month >= 10 ? "" : "0") + month + (day >= 10 ? "" : "0") + day + "";
    }
}
