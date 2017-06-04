package cn.neillee.dailyzhijiu.ui.aty;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.neil.dailyzhijiu.R;
import cn.neillee.dailyzhijiu.base.BaseSimpleActivity;
import cn.neillee.dailyzhijiu.model.http.api.AtyExtraKeyConstant;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：Neil on 2017/2/20 19:24.
 * 邮箱：cn.neillee@gmail.com
 */

public class WebViewActivity extends BaseSimpleActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.webview)
    WebView mWebView;

    private String mUrl;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);

        setupToolbar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.abc_ic_clear_mtrl_alpha);

        getExtras();
        setContent();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setContent() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setSupportMultipleWindows(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.loadUrl(mUrl);
    }

    public void getExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        mUrl = bundle.getString(AtyExtraKeyConstant.WEB_URL);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
