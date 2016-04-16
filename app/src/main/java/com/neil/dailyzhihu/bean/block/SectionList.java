package com.neil.dailyzhihu.bean.block;

import com.neil.dailyzhihu.bean.UniversalBlockBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neil on 2016/3/24.
 */
public class SectionList {
    /**
     * description : 看别人的经历，理解自己的生活
     * id : 1
     * name : 深夜惊奇
     * thumbnail : http://pic2.zhimg.com/312c4c0fdf80b81ab54a322cdb3beff9.jpg
     */
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements UniversalBlockBean {
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
