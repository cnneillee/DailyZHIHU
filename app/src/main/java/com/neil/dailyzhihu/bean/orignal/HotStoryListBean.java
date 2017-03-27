package com.neil.dailyzhihu.bean.orignal;

import com.neil.dailyzhihu.bean.IStoryBean;

import java.util.List;

/**
 * 作者：Neil on 2016/3/23 14:11.
 * 邮箱：cn.neillee@gmail.com
 */
public class HotStoryListBean extends OriginalStory {

    private List<HotStory> recent;

    public List<HotStory> getStories() {
        return recent;
    }

    public class HotStory extends OriginalStory implements IStoryBean {
        /**
         * news_id : 8029459
         * url : http://news-at.zhihu.com/api/2/news/8029459
         * thumbnail : http://pic3.zhimg.com/3e0a30c21c318f0d4142d4631dde4f96.jpg
         * title : 供电公司会为了多收电费而故意调高电压吗？
         */

        private int news_id;
        private String url;
        private String thumbnail;
        private String title;

        public HotStory(int news_id, String url, String thumbnail, String title) {
            this.news_id = news_id;
            this.url = url;
            this.thumbnail = thumbnail;
            this.title = title;
        }

        @Override
        public int getStoryId() {
            return news_id;
        }

        @Override
        public String getImage() {
            return getThumbnail();
        }

        @Override
        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }

        public String getThumbnail() {
            return thumbnail;
        }
    }
}
