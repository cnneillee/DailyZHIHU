package com.neil.dailyzhihu.listener;

import android.graphics.Bitmap;
import android.widget.ImageView;

public interface BitmapLoadCallback {
    public void bitmapLoaded(Bitmap bm);
    public void bitmapDisplay(ImageView iv);
}