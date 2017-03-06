package com.neil.dailyzhihu.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * 作者：Neil on 2017/2/24 10:39.
 * 邮箱：cn.neillee@gmail.com
 */

public class ObservableWebView1 extends WebView {
    private OnScrollChangedCallback mOnScrollChangedCallback;

    public ObservableWebView1(final Context context) {
        super(context);
    }

    public ObservableWebView1(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableWebView1(final Context context, final AttributeSet attrs,
                             final int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(final int l, final int t, final int oldl,
                                   final int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (mOnScrollChangedCallback != null) {
            mOnScrollChangedCallback.onScroll(l - oldl, t - oldt);
        }
    }

    public OnScrollChangedCallback getOnScrollChangedCallback() {
        return mOnScrollChangedCallback;
    }

    public void setOnScrollChangedCallback(
            final OnScrollChangedCallback onScrollChangedCallback) {
        mOnScrollChangedCallback = onScrollChangedCallback;
    }

    public static interface OnScrollChangedCallback {
        public void onScroll(int dx, int dy);
    }
}