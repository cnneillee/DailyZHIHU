package com.neil.dailyzhihu.utils.cnt;

import android.os.AsyncTask;
import android.util.Log;

import com.neil.dailyzhihu.listener.OnContentLoadingFinishedListener;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import static android.os.AsyncTask.THREAD_POOL_EXECUTOR;

/**
 * 内容加载器包装的实现
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
    public void loadContent(String contentUrl) {
        ContentLoadingTask loadingTask = new ContentLoadingTask();
        loadingTask.executeOnExecutor(THREAD_POOL_EXECUTOR);
//        loadingTask.execute();
    }

    private class ContentLoadingTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String contentResult;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(mContentUrl).build();
            contentResult = doLoad(request, client);
            Log.e("doInBackground", contentResult == null ? "contentResult = null" : contentResult);
            return contentResult;
        }

        @Override
        protected void onPostExecute(String s) {
            if (mListener != null) mListener.onFinish(s);
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
