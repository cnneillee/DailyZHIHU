package com.neil.dailyzhihu.bean.orignal;

import com.neil.dailyzhihu.bean.IBlockBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Neil on 2016/3/24 14:12.
 * 邮箱：cn.neillee@gmail.com
 */
public class ColumnListBean extends OriginalStory {

    private List<ColumnBean> data;

    public List<ColumnBean> getData() {
        return data;
    }

    public void setData(List<ColumnBean> data) {
        this.data = data;
    }

    public static class ColumnBean implements IBlockBean {
        /**
         * description : 看别人的经历，理解自己的生活
         * id : 1
         * name : 深夜惊奇
         * thumbnail : http://pic2.zhimg.com/312c4c0fdf80b81ab54a322cdb3beff9.jpg
         */

        private String description;
        private int id;
        private String name;
        private String thumbnail;

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public int getStoryId() {
            return id;
        }

        @Override
        public List<String> getImages() {
            List<String> images = new ArrayList<>();
            images.add(thumbnail);
            return images;
        }

        @Override
        public String getTitle() {
            return name;
        }
    }
}
