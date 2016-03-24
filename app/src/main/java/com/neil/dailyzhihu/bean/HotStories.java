package com.neil.dailyzhihu.bean;

import java.util.List;

/**
 * Created by Neil on 2016/3/23.
 */
public class HotStories {

    /**
     * news_id : 8029459
     * url : http://news-at.zhihu.com/api/2/news/8029459
     * thumbnail : http://pic3.zhimg.com/3e0a30c21c318f0d4142d4631dde4f96.jpg
     * title : 供电公司会为了多收电费而故意调高电压吗？
     */

    private List<RecentBean> recent;

    public List<RecentBean> getRecent() {
        return recent;
    }

    public void setRecent(List<RecentBean> recent) {
        this.recent = recent;
    }

    public class RecentBean {
        private int news_id;
        private String url;
        private String thumbnail;
        private String title;

        public int getNews_id() {
            return news_id;
        }

        public void setNews_id(int news_id) {
            this.news_id = news_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
