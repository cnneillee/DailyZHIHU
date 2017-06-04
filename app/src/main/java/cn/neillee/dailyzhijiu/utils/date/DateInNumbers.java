package cn.neillee.dailyzhijiu.utils.date;

public class DateInNumbers{
    int dayOfMonth;
    int year;
    int monthOfYear;

    public DateInNumbers(int year, int month, int day) {
        this.dayOfMonth = day;
        this.year = year;
        this.monthOfYear = month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public int getMonthOfYear() {
        return monthOfYear;
    }

    public int getYear() {
        return year;
    }
}