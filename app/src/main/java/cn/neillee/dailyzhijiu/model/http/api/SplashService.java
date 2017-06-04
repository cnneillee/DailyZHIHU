package cn.neillee.dailyzhijiu.model.http.api;

import android.util.ArrayMap;

import cn.neillee.dailyzhijiu.model.bean.orignal.GankSplashBean;
import cn.neillee.dailyzhijiu.model.bean.orignal.HuaBanSplashBean;
import cn.neillee.dailyzhijiu.model.bean.orignal.ZhihuSplashBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * 作者：Neil on 2017/4/11 11:32.
 * 邮箱：cn.neillee@gmail.com
 */

public interface SplashService {
    String GANK_HOST = "http://gank.io/api/";
    String HUABAN_HOST = "https://api.huaban.com/";
    String ZHIHU_HOST = "https://api.zhihu.com/";

    @GET(GANK_HOST + "data/{category}/{count}/{no}")
    Call<GankSplashBean> getGankSplash(@Path("category") String category, @Path("count") int count, @Path("no") int no);

    @GET(HUABAN_HOST + "/boards/{boardsId}/pins")
    Call<HuaBanSplashBean> getHuabanSplash(@Path("boardsId") int boardId, @QueryMap ArrayMap<String, String> queryMap);

    @GET(ZHIHU_HOST + "launch")
    Call<ZhihuSplashBean> getZhihuSplash(@HeaderMap ArrayMap<String, String> headersMap, @QueryMap ArrayMap<String, String> paramsMap);
}
