package com.neil.dailyzhihu.widget;

import android.support.v7.widget.RecyclerView;

import junit.framework.Assert;

/**
 * 作者：Neil on 2017/5/30 16:56.
 * 邮箱：cn.neillee@gmail.com
 */
public class DividerGridItemDecorationTest {
    @org.junit.Test
    public void testisLastRaw() {
        Assert.assertEquals(true, this.isLastRaw(null, 4, 2, 5));
        Assert.assertEquals(true, this.isLastRaw(null, 4, 2, 6));
        Assert.assertEquals(true, this.isLastRaw(null, 5, 2, 6));
    }

    @org.junit.Test
    public void testisLastColumn() {
        Assert.assertEquals(true, this.isLastColumn(null, 1, 2, 5));
        Assert.assertEquals(true, this.isLastColumn(null, 3, 2, 6));
        Assert.assertEquals(true, this.isLastColumn(null, 5, 2, 6));
    }

    private boolean isLastColumn(RecyclerView parent, int pos, int spanCount,
                                 int childCount) {
        return (pos + 1) % spanCount == 0;
    }

    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount,
                              int childCount) {
        int rowCount = childCount / spanCount + (childCount % spanCount) == 0 ? 0 : 1;
        return (pos + 1) / spanCount >= rowCount;
    }
}