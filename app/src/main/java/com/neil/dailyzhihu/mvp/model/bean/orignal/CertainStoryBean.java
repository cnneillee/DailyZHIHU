package com.neil.dailyzhihu.mvp.model.bean.orignal;

import java.util.List;

/**
 * 作者：Neil on 2016/3/23 14:22.
 * 邮箱：cn.neillee@gmail.com
 */
public class CertainStoryBean extends OriginalStory {
    /**
     * body : <div class="main-wrap content-wrap">...</div>
     * image_source : Angel Abril Ruiz / CC BY
     * title : 卖衣服的新手段：把耐用品变成「不停买新的」
     * image : http://p4.zhimg.com/30/59/30594279d368534c6c2f91b2c00c7806.jpg
     * share_url : http://daily.zhihu.com/story/3892357
     * js : []
     * ga_prefix : 050615
     * type : 0
     * id : 3892357
     * css : ["http://news-at.zhihu.com/css/news_qa.auto.css?v=77778"]
     */

    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private List<String> js;
    private String ga_prefix;
    /**
     * 在较为特殊的情况下，知乎日报可能将某个主题日报的站外文章推送至知乎日报首页。
     * type=0正常，type特殊情况
     */
    private int type;
    private int id;
    private List<String> css;

    public CertainStoryBean() {
    }

    public CertainStoryBean(int id, int type, String ga_prefix, String share_url, String image, String image_source, String title, String body) {
        this.id = id;
        this.type = type;
        this.ga_prefix = ga_prefix;
        this.share_url = share_url;
        this.image = image;
        this.image_source = image_source;
        this.title = title;
        this.body = body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setImageSource(String image_source) {
        this.image_source = image_source;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setShareUrl(String share_url) {
        this.share_url = share_url;
    }

    public void setGaPrefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setJs(List<String> js) {
        this.js = js;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }

    public String getBody() {
        return body;
    }

    public String getImageSource() {
        return image_source;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getShareUrl() {
        return share_url;
    }

    public String getGaPrefix() {
        return ga_prefix;
    }

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public List<String> getJs() {
        return js;
    }

    public List<String> getCss() {
        return css;
    }

    @Override
    public String toString() {
        return body + image_source + title + image + share_url + ga_prefix + type + id;
    }
}
