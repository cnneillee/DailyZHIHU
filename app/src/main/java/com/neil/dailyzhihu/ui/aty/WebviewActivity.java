package com.neil.dailyzhihu.ui.aty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebView;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.bean.orignallayer.StoryContent;
import com.neil.dailyzhihu.listener.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.load.LoaderFactory;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：Neil on 2017/2/20 19:24.
 * 邮箱：cn.neillee@gmail.com
 */

public class WebviewActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.webview)
    WebView mWebview;

    private final String URL = "https://news-at.zhihu.com/api/4/news/9236860";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("Webview Activity");
        setContent();
    }

    private OnContentLoadingFinishedListener mListener = new OnContentLoadingFinishedListener() {
        @Override
        public void onFinish(String content, String url) {
            StoryContent story = GsonDecoder.getDecoder().decoding(content, StoryContent.class);
            List<String> cssArr = story.getCss();
            List<String> jsArr = story.getJs();
            String head = "<head><style type=\"text/css\">" + cssArr.get(0) + "</style></head>";
            String html = "<html>" + head + "<body>" + story.getBody() + " </body></html>";
            mWebview.loadData(html, "text/html; charset=UTF-8", null);
            Log.e("HTML", html);
//            mWebview.loadData(content,"text/html","utf-8");
        }
    };

    private void setContent() {
        LoaderFactory.getContentLoader().loadContent(URL, mListener);
    }
}
