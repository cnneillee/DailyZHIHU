package com.neil.dailyzhihu.ui.story;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.listener.BitmapLoadCallback;
import com.neil.dailyzhihu.utils.SnackbarUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：Neil on 2017/3/4 15:11.
 * 邮箱：cn.neillee@gmail.com
 */

public class ShareImageFragment extends Fragment {
    @Bind(R.id.webview)
    WebView mWebView;
    private String mHtmlContent;
    private View mRootView;
    private BitmapLoadCallback mBitmapLoadCallback;

    public static ShareImageFragment newInstance() {
        Bundle args = new Bundle();

        ShareImageFragment fragment = new ShareImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_share_image, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_action_change_color:
                SnackbarUtil.ShortSnackbar(mRootView, getResources().getString(R.string.to_do), SnackbarUtil.Confirm).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_share_image, container, false);
        ButterKnife.bind(this, mRootView);
        mWebView.loadData(mHtmlContent, "text/html; charset=UTF-8", null);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Bitmap bitmap = makingView2Bitmap(mWebView);
                mBitmapLoadCallback.bitmapLoaded(bitmap);
            }
        });

        return mRootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mBitmapLoadCallback = (BitmapLoadCallback) context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void setHtmlContent(String htmlContent) {
        mHtmlContent = htmlContent;
    }

    /**
     * 截取webView快照(webView加载的整个内容的大小)
     *
     * @param webView
     * @return
     */
    private Bitmap captureWebView(WebView webView) {
        Picture snapShot = webView.capturePicture();

        Bitmap bmp = Bitmap.createBitmap(snapShot.getWidth(), snapShot.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        snapShot.draw(canvas);
        return bmp;
    }

    private Bitmap makingView2Bitmap(View view) {
        if (view == null) {
            return null;
        }
        Bitmap screenshot;
        screenshot = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(screenshot);
        view.draw(c);
        return screenshot;
    }
}
