package com.neil.dailyzhihu.bean.orignal;

import java.util.List;

/**
 * 作者：Neil on 2017/3/31 11:37.
 * 邮箱：cn.neillee@gmail.com
 */

public class LofterSplashBean {

    /**
     * title : 图片
     * date : 2017-02-13 09:13:32
     * link : http://9mouth.lofter.com/post/11c344_e350819
     * list : ["http://imglf1.nosdn.127.net/img/bzdNQ3lVZUc4R1QzdWJ4c3BUTGpuUjk4OGRMZTZUcitqZEhEbi9mTDJpbWNGYTMvM0hMT3VRPT0.jpg"]
     */

    private String title;
    private String date;
    private String link;
    private List<String> list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
