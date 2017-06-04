package cn.neillee.dailyzhijiu.utils.date;

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
        return calendar2yyyyMMDD(calendar);
    }

    public static String calendar2yyyyMMDD(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + (month >= 10 ? "" : "0") + month + (day >= 10 ? "" : "0") + day + "";
    }

    /**
     * 将字符串日期解析成DateInNumbers
     *
     * @param yyyyMMDD 日期字符串
     * @return dateInNumbers
     */
    public static DateInNumbers yyyyMMDD2DateInNumbers(String yyyyMMDD) {
        DateInNumbers dateInNumbers =  null;
        try {
            dateInNumbers = new DateInNumbers(
                    Integer.valueOf(yyyyMMDD.substring(0, 4)),
                    Integer.valueOf(yyyyMMDD.substring(4, 6)),
                    Integer.valueOf(yyyyMMDD.substring(6, 8)));
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return dateInNumbers;
    }

    /**
     * 将DateInNumbers格式化为yyyyMMDD
     * @param dateInNumbers DateInNumbers
     * @return yyyyMMDD
     */
    public static String dateInNumbers2yyyyMMDD(DateInNumbers dateInNumbers) {
        return dateInNumbers2yyyyMMDD(dateInNumbers.year, dateInNumbers.monthOfYear, dateInNumbers.dayOfMonth);
    }

    /**
     * 将year，monthOfYear, dayOfMonth转变为yyyyMMDD
     * @param year 年
     * @param monthOfYear 月
     * @param dayOfMonth 日
     * @return yyyyMMDD
     */
    public static String dateInNumbers2yyyyMMDD(int year,int monthOfYear, int dayOfMonth) {
        return year + ((monthOfYear + 1 >= 10 ? "" : "0") + monthOfYear)
                + ((dayOfMonth >= 10 ? "" : "0") + dayOfMonth);
    }
}
