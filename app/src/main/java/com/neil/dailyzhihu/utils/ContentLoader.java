package com.neil.dailyzhihu.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Neil on 2016/3/23.
 */
public class ContentLoader {
    public static void loadString(final String imgUrl, final ImageLoader.OnFinishListener onFinishListener) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String result = null;
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(imgUrl).build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        result = response.body().string();
                    }
                    Log.e("ContentLoader", response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                onFinishListener.onFinish(s);
            }
        }.execute();
    }

}
