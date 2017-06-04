package cn.neillee.dailyzhijiu.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.neillee.dailyzhijiu.R;

/**
 * 作者：赵晨璞 on 2016/5/1 12:48.
 */
public class SnackbarUtil {

    public static final int Info = 1;
    public static final int Confirm = 2;
    public static final int Warning = 3;
    public static final int Alert = 4;


    public static int red = 0xfff44336;
    public static int green = 0xff4caf50;
    public static int blue = 0xff2195f3;
    public static int orange = 0xffffc107;

    public static Snackbar ShortSnackbarWithTheme(Context context, View view, String message) {
        TypedValue barBgTypedValue = new TypedValue();
        TypedValue barTitleTypedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.barBgColor, barBgTypedValue, true);
        context.getTheme().resolveAttribute(R.attr.barTitleColor, barTitleTypedValue, true);
        int barBgColor = barBgTypedValue.data;
        int barTitleColor = barTitleTypedValue.data;
        return ShortSnackbar(view, message, barTitleColor, barBgColor);
    }

    /**
     * 短显示Snackbar，自定义颜色
     *
     * @param view            View
     * @param message         Message
     * @param messageColor    Color of message
     * @param backgroundColor Color of background
     * @return Snackbar created
     */
    public static Snackbar ShortSnackbar(View view, String message, int messageColor, int backgroundColor) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        setSnackbarColor(snackbar, messageColor, backgroundColor);
        return snackbar;
    }

    /**
     * 长显示Snackbar，自定义颜色
     *
     * @param view            View
     * @param message         Message
     * @param messageColor    Color of message
     * @param backgroundColor Color of background
     * @return Snackbar created
     */
    public static Snackbar LongSnackbar(View view, String message, int messageColor, int backgroundColor) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        setSnackbarColor(snackbar, messageColor, backgroundColor);
        return snackbar;
    }

    /**
     * 自定义时常显示Snackbar，自定义颜色
     *
     * @param view            View
     * @param message         Message
     * @param messageColor    Color of message
     * @param backgroundColor Color of background
     * @return Snackbar created
     */
    public static Snackbar IndefiniteSnackbar(View view, String message, int duration, int messageColor, int backgroundColor) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setDuration(duration);
        setSnackbarColor(snackbar, messageColor, backgroundColor);
        return snackbar;
    }

    /**
     * 短显示Snackbar，可选预设类型
     *
     * @param view    View
     * @param message Message
     * @param type    Message type
     * @return Snackbar created
     */
    public static Snackbar ShortSnackbar(View view, String message, int type) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        switchType(snackbar, type);
        return snackbar;
    }

    /**
     * 长显示Snackbar，可选预设类型
     *
     * @param view    View
     * @param message Message
     * @param type    Message type
     * @return Snackbar created
     */
    public static Snackbar LongSnackbar(View view, String message, int type) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        switchType(snackbar, type);
        return snackbar;
    }

    /**
     * 自定义时常显示Snackbar，可选预设类型
     *
     * @param view    View
     * @param message Message
     * @param type    Message type
     * @return Snackbar created
     */
    public static Snackbar IndefiniteSnackbar(View view, String message, int duration, int type) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setDuration(duration);
        switchType(snackbar, type);
        return snackbar;
    }

    //选择预设类型
    private static void switchType(Snackbar snackbar, int type) {
        switch (type) {
            case Info:
                setSnackbarColor(snackbar, blue);
                break;
            case Confirm:
                setSnackbarColor(snackbar, green);
                break;
            case Warning:
                setSnackbarColor(snackbar, orange);
                break;
            case Alert:
                setSnackbarColor(snackbar, Color.YELLOW, red);
                break;
        }
    }

    /**
     * 设置Snackbar背景颜色
     *
     * @param snackbar        Snackbar
     * @param backgroundColor Color of background
     */
    public static void setSnackbarColor(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        view.setBackgroundColor(backgroundColor);
    }

    /**
     * 设置Snackbar文字和背景颜色
     *
     * @param snackbar        Snackbar
     * @param messageColor    Color of message
     * @param backgroundColor Color of background
     */
    public static void setSnackbarColor(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();
        view.setBackgroundColor(backgroundColor);
        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
    }

    /**
     * 向Snackbar中添加view
     *
     * @param snackbar Snackbar
     * @param layoutId Layout id of new view
     * @param index    Position of new view in Snackbar
     */
    public static void SnackbarAddView(Snackbar snackbar, int layoutId, int index) {
        View snackbarView = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarView;

        View add_view = LayoutInflater.from(snackbarView.getContext()).inflate(layoutId, null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity = Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view, index, p);
    }

}