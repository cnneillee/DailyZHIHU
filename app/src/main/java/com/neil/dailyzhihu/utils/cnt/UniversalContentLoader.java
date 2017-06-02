package com.neil.dailyzhihu.utils.cnt;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.neil.dailyzhihu.listener.OnContentLoadListener;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
                    OnContentLoadListener listener = (OnContentLoadListener) msg.obj;
                    String networkData = msg.getData().getString(KEY_NETWORK_DATA);
                    String url = msg.getData().getString(KEY_URL);
                    listener.onSuccess(networkData, url);
                    Logger.t(LOG_TAG).i("REQUEST_SUCCESS: " + url + "\n" + networkData);
                    break;
                case REQUEST_FAIL:
                    break;
            }
        }
    };

    @Override
    public void loadContent(final String contentUrl, final OnContentLoadListener listener) {
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
                    public void onFailure(Call call, IOException e) {
                        String failureInfo = e.getMessage();
                        deliverBundle.putString(KEY_FAILURE_INFO, failureInfo);
                        msg.what = REQUEST_FAIL;
                        msg.setData(deliverBundle);
                        msg.sendToTarget();
                        Logger.t(LOG_TAG).e("onFailure: " + contentUrl + "\n" + failureInfo);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String networkData = response.body().string();
                            msg.what = REQUEST_SUCCESS;
                            deliverBundle.putString(KEY_NETWORK_DATA, networkData);
                            Logger.t(LOG_TAG).i(response.code() + "SUCCESS —— onResponse: " + contentUrl + "\n" + networkData);
                        } else {
                            throw new IOException("Unexpected code " + response);
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
