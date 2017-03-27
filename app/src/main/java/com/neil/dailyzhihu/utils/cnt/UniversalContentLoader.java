package com.neil.dailyzhihu.utils.cnt;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.neil.dailyzhihu.listener.OnContentLoadedListener;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * 内容加载器包装的实现
 */
public class UniversalContentLoader implements ContentLoaderWrapper {
    private static final int REQUEST_SUCCESS = 0;
    private static final int REQUEST_FAIL = 1;
    private static final String LOG_TAG = UniversalContentLoader.class.getSimpleName();

    private OkHttpClient mOkHttpClient;

    public UniversalContentLoader() {
        this.mOkHttpClient = new OkHttpClient();
    }

    private final static String KEY_NETWORK_DATA = "KEY_NETWORK_DATA";
    private final static String KEY_FAILURE_INFO = "KEY_FAILURE_INFO";
    private final static String KEY_URL = "KEY_URL";

    // handle msg, refresh ui here
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_SUCCESS:
                    OnContentLoadedListener listener = (OnContentLoadedListener) msg.obj;
                    String networkData = msg.getData().getString(KEY_NETWORK_DATA);
                    String url = msg.getData().getString(KEY_URL);
                    listener.onSuccess(networkData, url);
                    Log.e(LOG_TAG, "REQUEST_SUCCESS: " + url + "\n" + networkData);
                    break;
                case REQUEST_FAIL:
                    break;
            }
        }
    };

    @Override
    public void loadContent(final String contentUrl, final OnContentLoadedListener listener) {
        Runnable requestTask = new Runnable() {
            @Override
            public void run() {
                final Message msg = mHandler.obtainMessage();
                final Bundle deliverBundle = new Bundle();
                msg.obj = listener;
                deliverBundle.putString(KEY_URL, contentUrl);
                final Request request = new Request.Builder().url(contentUrl).build();
                mOkHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        String failureInfo = e.getMessage();
                        deliverBundle.putString(KEY_FAILURE_INFO, failureInfo);
                        msg.what = REQUEST_FAIL;
                        msg.setData(deliverBundle);
                        msg.sendToTarget();
                        Log.e(LOG_TAG, "onFailure: " + contentUrl + "\n" + failureInfo);
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String networkData = response.body().string();
                            msg.what = REQUEST_SUCCESS;
                            deliverBundle.putString(KEY_NETWORK_DATA, networkData);
                            Log.e(LOG_TAG, response.code() + "SUCCESS —— onResponse: " + contentUrl + "\n" + networkData);
                        } else {
                            String failureInfo = response.toString();
                            deliverBundle.putString(KEY_FAILURE_INFO, failureInfo);
                            msg.what = REQUEST_FAIL;
                            Log.e(LOG_TAG, response.code() + "FAILURE —— onResponse: " + contentUrl + "\n" + failureInfo);
                        }
                        msg.setData(deliverBundle);
                        msg.sendToTarget();
                    }
                });
            }
        };
        Thread requestThread = new Thread(requestTask);
        requestThread.start();
    }

    @Override
    public void loadContent(final String contentUrl) {
        Runnable requestTask = new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder().url(contentUrl).build();
                mOkHttpClient.newCall(request).enqueue(null);
            }
        };
        Thread requestThread = new Thread(requestTask);
        requestThread.start();
    }
}
