package com.neil.dailyzhihu.ui.aty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.api.AtyExtraKeyConstant;
import com.neil.dailyzhihu.ui.NightModeBaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：Neil on 2017/2/20 19:24.
 * 邮箱：cn.neillee@gmail.com
 */

public class WebViewActivity extends NightModeBaseActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.webview)
    WebView mWebView;

    private String mUrl;

    private View.OnClickListener upBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_action_cancel);
        mToolbar.setNavigationOnClickListener(upBtnListener);

        getExtras();
        setContent();
    }

    private void setContent() {
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
        super.onBackPressed();
    }
}
