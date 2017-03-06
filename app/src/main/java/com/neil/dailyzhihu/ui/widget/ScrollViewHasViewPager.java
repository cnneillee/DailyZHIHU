package com.neil.dailyzhihu.ui.widget;

///android---在ScrollView中嵌套ViewPager
//        在ScrollView 中如果有嵌套了ViewPager 那么 ViewPager的横向滑动将被ScrollView拦截掉，所以我们需要重写ScrollView来判断用户的手势，当用户手势在X方向上的距离大于在Y方向上的距离的时候判断是向左滑动，此时屏蔽ScrollView的滑动操作。反过来，就Y》X，就判定是ScrollView的滑动：重写的ScrollView代码如下：
//
///**
// * xu
// */

import android.content.Context;
import android.renderscript.Sampler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;

public class ScrollViewHasViewPager extends ObservableScrollView {

    //决定scrollview是否可以滑动
    private boolean canScroll;
    //手势
    private GestureDetector gestureDetector;

    public ScrollViewHasViewPager(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public ScrollViewHasViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        gestureDetector = new GestureDetector(new YScrollView());
        canScroll = true;
    }

    public ScrollViewHasViewPager(Context context, AttributeSet attrs,
                                  int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            canScroll = true;
        }
        return super.onInterceptTouchEvent(ev) && gestureDetector.onTouchEvent(ev);
    }


    class YScrollView extends SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            // TODO Auto-generated method stub
            if (canScroll) {
                if (Math.abs(distanceY) >= Math.abs(distanceX)) {
                    canScroll = true;
                } else {
                    canScroll = false;
                }
            }
            return canScroll;
        }
    }
}

