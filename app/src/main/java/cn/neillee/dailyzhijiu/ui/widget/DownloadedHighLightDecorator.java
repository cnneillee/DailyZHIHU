package cn.neillee.dailyzhijiu.ui.widget;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.neillee.dailyzhijiu.R;
import cn.neillee.dailyzhijiu.utils.date.DateUtil;

/**
 * Created by Neil on 2016/5/6.
 */
public class DownloadedHighLightDecorator implements DayViewDecorator {
    private int color;
    private List<String> mDates;

    public DownloadedHighLightDecorator() {
        mDates = new ArrayList<>();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(8, R.color.pink));
    }

    public void addDatesAndUpdate(Calendar calendar) {
        mDates.add(DateUtil.calendar2yyyyMMDD(calendar));
    }

    public void addDatesAndUpdate(String calendar) {
        mDates.add(calendar);
    }

    public boolean contains(CalendarDay calendarDay) {
        for (int i = 0; i < mDates.size(); i++) {
            if (mDates.get(i).equals(DateUtil.calendar2yyyyMMDD(calendarDay.getCalendar())))
                return true;
        }
        return false;
    }
}
