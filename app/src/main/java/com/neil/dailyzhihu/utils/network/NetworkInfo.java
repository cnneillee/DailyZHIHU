package com.neil.dailyzhihu.utils.network;

public class NetworkInfo {
    private String msg;
    private boolean status;

    public NetworkInfo(String msg, boolean status) {
        this.msg = msg;
        this.status = status;
    }

    public NetworkInfo() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}