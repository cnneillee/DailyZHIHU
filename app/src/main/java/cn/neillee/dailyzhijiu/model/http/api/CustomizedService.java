package cn.neillee.dailyzhijiu.model.http.api;

import cn.neillee.dailyzhijiu.model.bean.orignal.UpdateInfoBean;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * 作者：Neil on 2017/4/11 11:05.
 * 邮箱：cn.neillee@gmail.com
 */

public interface CustomizedService {
    String SUPPORT_HOST = "https://lilinmao.github.io/projects/dailyzhihu/api/1/";

    @GET("updates/index.html")
    Call<UpdateInfoBean> getUpdateInfo();
}
