package com.neil.dailyzhihu.presenter;

import android.util.ArrayMap;

import com.neil.dailyzhihu.app.DailyApp;
import com.neil.dailyzhihu.base.RxPresenter;
import com.neil.dailyzhihu.model.bean.orignal.GankSplashBean;
import com.neil.dailyzhihu.model.bean.orignal.HuaBanSplashBean;
import com.neil.dailyzhihu.model.bean.orignal.ZhihuSplashBean;
import com.neil.dailyzhihu.model.http.RetrofitHelper;
import com.neil.dailyzhihu.model.http.api.API;
import com.neil.dailyzhihu.presenter.constract.ImageSplashContract;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：Neil on 2017/4/11 21:12.
 * 邮箱：cn.neillee@gmail.com
 */

public class ImageSplashPresenter extends RxPresenter<ImageSplashContract.View>
        implements ImageSplashContract.Presenter {
    public static final int PUSH_SPLASH = 1;
    public static final int ERCIYUAN_SPLASH = 2;
    public static final int ZHIHU_SPLASH = 3;
    public static final int GANK_SPLASH = 4;

    private RetrofitHelper mRetrofitHelper;

    @Inject
    ImageSplashPresenter(RetrofitHelper retrofitHelper) {
        mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getSplash(int splashType) {
        ArrayMap<String, String> params = new ArrayMap<>();
        ArrayMap<String, String> headers;
        switch (splashType) {
            // "https://api.huaban.com/boards/35593275/pins?limit=1";
            case ERCIYUAN_SPLASH:
                params.clear();
                params.put("limit", "1");
                fetchHuaBanImageSplash(35593275, params);
                break;
            // https://api.zhihu.com/launch?app=daily&size=1080x1920
            case ZHIHU_SPLASH:
                params.clear();
                params.put("app", "daily");
                params.put("size", DailyApp.SCREEN_WIDTH + "x" + DailyApp.SCREEN_HEIGHT);

                headers = new ArrayMap<>();
                headers.put("Accept-Encoding", "gzip");
                // must
                headers.put("Authorization", "oauth c105bf312bb54e6f972a892d16b29fac");
                headers.put("User-Agent", "Mozilla/5.0 (Linux; Android 6.0.1; ONEPLUS A3000 Build/MMB29M; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/56.0.2924.87 Mobile Safari/537.36 Google-HTTP-Java-Client/1.22.0 (gzip)");
                headers.put("x-api-version", "4");
                headers.put("x-app-version", "2.6.2");
                // must
                headers.put("x-app-za", "OS=Android");
                headers.put("Host", "api.zhihu.com");
                headers.put("Connection", "Keep-Alive");
                fetchZhihuImageSplash(headers, params);
                break;
            //"http://gank.io/api/data/%E7%A6%8F%E5%88%A9/1/1"
            case GANK_SPLASH:
                fetchGankImageSplash("福利", 1, 1);
                break;
            //"https://api.huaban.com/boards/35593400/pins?limit=1"
            case PUSH_SPLASH:
                params.clear();
                params.put("limit", "1");
                fetchHuaBanImageSplash(35593400, params);
                break;
        }
    }

    private void fetchGankImageSplash(String s, int i, int i1) {
        mRetrofitHelper.fetchGankImageSplash(s, i, i1).enqueue(new Callback<GankSplashBean>() {
            @Override
            public void onResponse(Call<GankSplashBean> call, Response<GankSplashBean> response) {
                if (response.isSuccessful()) {
                    GankSplashBean splashBean = response.body();
                    String imgUrl = splashBean.isError() || splashBean.getResults().size() == 0 ?
                            "" : splashBean.getResults().get(0).getUrl();
                    String imgSign = splashBean.isError() || splashBean.getResults().size() == 0 ?
                            "" : splashBean.getResults().get(0).getSource();
                    mView.showImage(imgUrl, imgSign);
                } else Logger.e("Error[%d] in request TOPIC BlockData", response.code());
            }

            @Override
            public void onFailure(Call<GankSplashBean> call, Throwable t) {
                mView.showError(t.getMessage());
            }
        });
    }

    private void fetchZhihuImageSplash(ArrayMap<String, String> headers, ArrayMap<String, String> params) {
        mRetrofitHelper.fetchZhihuImageSplash(headers, params).enqueue(new Callback<ZhihuSplashBean>() {
            @Override
            public void onResponse(Call<ZhihuSplashBean> call, Response<ZhihuSplashBean> response) {
                if (response.isSuccessful()) {
                    ZhihuSplashBean splashBean = response.body();
                    String imgUrl = splashBean.getLaunch_ads() == null || splashBean.getLaunch_ads().size() == 0 ?
                            "" : splashBean.getLaunch_ads().get(0).getImage();
                    String imgSign = "知乎开屏";
                    mView.showImage(imgUrl, imgSign);
                } else Logger.e("Error[%d] in request ZhihuImageSplash", response.code());
            }

            @Override
            public void onFailure(Call<ZhihuSplashBean> call, Throwable t) {
                mView.showError(t.getMessage());
            }
        });
    }

    private void fetchHuaBanImageSplash(int i, ArrayMap<String, String> params) {
        mRetrofitHelper.fetchHuaBanImageSplash(i, params).enqueue(new Callback<HuaBanSplashBean>() {
            @Override
            public void onResponse(Call<HuaBanSplashBean> call, Response<HuaBanSplashBean> response) {
                if (response.isSuccessful()) {
                    HuaBanSplashBean splashBean = response.body();
                    if (splashBean.getPins() != null || splashBean.getPins().size() > 0) {
                        String imgUrl = API.HUABAN_IMG_HEADER + splashBean.getPins().get(0).getFile().getKey();
                        String imgSign = splashBean.getPins().get(0).getRaw_text();
                        mView.showImage(imgUrl, imgSign);
                    } else Logger.e("Error[%d] in request HuaBanImageSplash", response.code());
                } else {
                    mView.showError(response.code() + ":" + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<HuaBanSplashBean> call, Throwable t) {
                mView.showError(t.getMessage());
            }
        });
    }
}
