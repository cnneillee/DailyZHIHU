package com.neil.dailyzhihu.utils.cnt;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.neil.dailyzhihu.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.utils.cnt.ContentLoaderWrapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Neil on 2016/4/15.
 */
public class UniversalContentLoader implements ContentLoaderWrapper {
    private OnContentLoadingFinishedListener mListener;
    private String mContentUrl;

    @Override
    public void loadContent(String contentUrl, OnContentLoadingFinishedListener listener) {
        if (listener == null)
            return;
        mListener = listener;
        if (contentUrl == null || contentUrl.equals("")) {
            return;
        }
        mContentUrl = contentUrl;
        loadContent(contentUrl);
    }

    @Override
    public void loadContent(Context context, String contentUrl, OnContentLoadingFinishedListener listener) {
        loadContent(contentUrl, listener);
    }

    @Override
    public void loadContent(String contentUrl) {
        ContentLoadingTask loadingTask = new ContentLoadingTask();
        loadingTask.execute();
    }

    class ContentLoadingTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String contentResult;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(mContentUrl).build();
            contentResult = doLoad(request, client);
            return contentResult;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (mListener != null)
                mListener.onFinish(s);
        }

        private String doLoad(Request request, OkHttpClient client) {
            String contentResult = null;
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    contentResult = response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return contentResult;
        }
    }
}
