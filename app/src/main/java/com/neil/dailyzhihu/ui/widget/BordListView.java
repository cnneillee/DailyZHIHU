//package com.neil.dailyzhihu.ui.widget;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.drawable.Drawable;
//import android.util.AttributeSet;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ListAdapter;
//import android.widget.ListView;
//
//import com.neil.dailyzhihu.R;
//
///**
// * 作者：Neil on 2017/3/4 14:01.
// * 邮箱：cn.neillee@gmail.com
// */
//
//public class BordListView extends AdapterView<ListAdapter> {
//    ListAdapter mAdapter;
//
//    public BordListView(Context context) {
//        this(context, null);
//    }
//
//    public BordListView(Context context, AttributeSet attrs) {
//        this(context, attrs, R.attr.listViewStyle);
//    }
//
//    public BordListView(Context context, AttributeSet attrs, int defStyleAttr) {
//        this(context, attrs, defStyleAttr, 0);
//    }
//
//    public BordListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//
//        final TypedArray a = context.obtainStyledAttributes(
//                attrs, R.styleable.ListView, defStyleAttr, defStyleRes);
//
//        final CharSequence[] entries = a.getTextArray(R.styleable.ListView_entries);
//        if (entries != null) {
//            setAdapter(new ArrayAdapter<>(context, R.layout.simple_list_item_1, entries));
//        }
//
//        final Drawable d = a.getDrawable(R.styleable.ListView_divider);
//        if (d != null) {
//            // Use an implicit divider height which may be explicitly
//            // overridden by android:dividerHeight further down.
//            setDivider(d);
//        }
//
//        final Drawable osHeader = a.getDrawable(R.styleable.ListView_overScrollHeader);
//        if (osHeader != null) {
//            setOverscrollHeader(osHeader);
//        }
//
//        final Drawable osFooter = a.getDrawable(R.styleable.ListView_overScrollFooter);
//        if (osFooter != null) {
//            setOverscrollFooter(osFooter);
//        }
//
//        // Use an explicit divider height, if specified.
//        if (a.hasValueOrEmpty(R.styleable.ListView_dividerHeight)) {
//            final int dividerHeight = a.getDimensionPixelSize(
//                    R.styleable.ListView_dividerHeight, 0);
//            if (dividerHeight != 0) {
//                setDividerHeight(dividerHeight);
//            }
//        }
//
//        mHeaderDividersEnabled = a.getBoolean(R.styleable.ListView_headerDividersEnabled, true);
//        mFooterDividersEnabled = a.getBoolean(R.styleable.ListView_footerDividersEnabled, true);
//
//        a.recycle();
//    }
//
//    @Override
//    public ListAdapter getAdapter() {
//        return mAdapter;
//    }
//
//    @Override
//    public void setAdapter(ListAdapter adapter) {
//        this.mAdapter = adapter;
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//
//    }
//
//    @Override
//    public View getSelectedView() {
//        return null;
//    }
//
//    @Override
//    public void setSelection(int position) {
//        if (mAdapter == null) {
//            return;
//        }
//
//        if (!isInTouchMode()) {
//            position = lookForSelectablePosition(position, true);
//            if (position >= 0) {
//                setNextSelectedPositionInt(position);
//            }
//        } else {
//            mResurrectToPosition = position;
//        }
//
//        if (position >= 0) {
//            mLayoutMode = LAYOUT_SPECIFIC;
//            mSpecificTop = mListPadding.top + y;
//
//            if (mNeedSync) {
//                mSyncPosition = position;
//                mSyncRowId = mAdapter.getItemId(position);
//            }
//
//            if (mPositionScroller != null) {
//                mPositionScroller.stop();
//            }
//            requestLayout();
//        }
//    }
//}
