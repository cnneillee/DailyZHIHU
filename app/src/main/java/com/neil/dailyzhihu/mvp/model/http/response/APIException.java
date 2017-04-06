package com.neil.dailyzhihu.mvp.model.http.response;

/**
 * 作者：Neil on 2017/4/4 23:17.
 * 邮箱：cn.neillee@gmail.com
 */

public class APIException extends Exception {
    public APIException(String detailMessage) {
        super(detailMessage);
    }
}
