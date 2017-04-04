package com.neil.dailyzhihu.model.bean.orignal;

import com.neil.dailyzhihu.model.bean.IStoryBean;

import java.util.List;

/**
 * 作者：Neil on 2016/3/24 14:38.
 * 邮箱：cn.neillee@gmail.com
 */
public class ColumnStoryListBean extends OriginalStory {
    /**
     * timestamp : 1457013601
     * stories : [{"images":["http://pic2.zhimg.com/312c4c0fdf80b81ab54a322cdb3beff9.jpg"],"date":"20160323","display_date":"3 月 23 日","id":8041772,"title":"深夜惊奇 · 糖丸救命"},{"images":["http://pic3.zhimg.com/8716a2a27e1e20562698a873c0672c9a.jpg"],"date":"20160322","display_date":"3 月 22 日","id":8038222,"title":"深夜惊奇 · 捡了个 iPhone"},{"images":["http://pic4.zhimg.com/6a2c23ef32142636ae8669587df7ba2b.jpg"],"date":"20160321","display_date":"3 月 21 日","id":8032239,"title":"深夜惊奇 · 一个人长大"},{"images":["http://pic1.zhimg.com/7b96b2b5e6a9f7e2321d27b9e6ca60cc.jpg"],"date":"20160320","display_date":"3 月 20 日","id":8021265,"title":"深夜惊奇 · 吸毒的父亲"},{"images":["http://pic1.zhimg.com/cc074e939470f3974ac14318070481f8.jpg"],"date":"20160319","display_date":"3 月 19 日","id":8024183,"title":"深夜惊奇 · 截肢"},{"images":["http://pic1.zhimg.com/18476c677861bf0c8b1da5641b749c64.jpg"],"date":"20160318","display_date":"3 月 18 日","id":8021305,"title":"深夜惊奇 · 最心酸的一句"},{"images":["http://pic3.zhimg.com/c99ca105bc54bc83bfda21b705f005aa.jpg"],"date":"20160317","display_date":"3 月 17 日","id":7993711,"title":"深夜惊奇 · 共患难，共祝愿"},{"images":["http://pic2.zhimg.com/d5b4708c01152bad68b1f50d3e2fd131.jpg"],"date":"20160316","display_date":"3 月 16 日","id":7919752,"title":"深夜惊奇 · 离婚难"},{"images":["http://pic2.zhimg.com/c61ccfeb96dd26c8c042004118bde325.jpg"],"date":"20160314","display_date":"3 月 14 日","id":7988857,"title":"深夜惊奇 · 好运气坏运气（一）"},{"images":["http://pic3.zhimg.com/13adc4b2722335f5f274a7a4027ddfd6.jpg"],"date":"20160313","display_date":"3 月 13 日","id":7993715,"title":"深夜惊奇 · 施暴无解"},{"images":["http://pic4.zhimg.com/93628cf8cf95f74b1d1bbc3801d29da7.jpg"],"date":"20160312","display_date":"3 月 12 日","id":7989029,"title":"深夜惊奇 · 绝交信"},{"images":["http://pic3.zhimg.com/ac2e5ef84ffe9cd7feeaebce059708b6.jpg"],"date":"20160311","display_date":"3 月 11 日","id":7985530,"title":"深夜惊奇 · 吃着吃着哭了"},{"images":["http://pic4.zhimg.com/39874179031943ceddbf61a97a21fceb.jpg"],"date":"20160310","display_date":"3 月 10 日","id":7919740,"title":"深夜惊奇 · 别人的痛苦"},{"images":["http://pic3.zhimg.com/7201733be8b90fcd77278b778f7e14c6.jpg"],"date":"20160309","display_date":"3 月 9 日","id":7980068,"title":"深夜惊奇 · 手术室的纸条"},{"images":["http://pic3.zhimg.com/520fd57c39a85848e82135468517096e.jpg"],"date":"20160308","display_date":"3 月 8 日","id":7975044,"title":"深夜惊奇 · 不会撩妹"},{"images":["http://pic4.zhimg.com/9b7c3cec6a2293d25f956bab308dd95b.jpg"],"date":"20160307","display_date":"3 月 7 日","id":7970186,"title":"深夜惊奇 · 带我妈离开家"},{"images":["http://pic1.zhimg.com/9cb92e2d5ccd79b210905d156cac9c58.jpg"],"date":"20160306","display_date":"3 月 6 日","id":7919621,"title":"深夜惊奇 · 20 年的运气换一回"},{"images":["http://pic2.zhimg.com/c1348ce8518a462022ae2141ba6c91f5.jpg"],"date":"20160305","display_date":"3 月 5 日","id":7947218,"title":"深夜惊奇 · 恶果"},{"images":["http://pic4.zhimg.com/a251f8c88223cc0148a49d0e794a4247.jpg"],"date":"20160304","display_date":"3 月 4 日","id":7941451,"title":"深夜惊奇 · 帮忙凑钱"},{"images":["http://pic3.zhimg.com/654a84b91773b3da89513ac73ff528a2.jpg"],"date":"20160303","display_date":"3 月 3 日","id":7947109,"title":"深夜惊奇 · 漫长的怀念"}]
     * name : 深夜惊奇
     */

    private int timestamp;
    private List<ColumnStory> stories;
    private String name;

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ColumnStory> getStories() {
        return stories;
    }

    public void setStories(List<ColumnStory> stories) {
        this.stories = stories;
    }

    public class ColumnStory implements IStoryBean {
        /**
         * images : ["http://pic2.zhimg.com/312c4c0fdf80b81ab54a322cdb3beff9.jpg"]
         * date : 20160323
         * display_date : 3 月 23 日
         * id : 8041772
         * title : 深夜惊奇 · 糖丸救命
         */

        private String date;
        private String display_date;
        private int id;
        private String title;
        private List<String> images;

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public int getStoryId() {
            return id;
        }

        @Override
        public String getImage() {
            return images != null ? images.get(0) : null;
        }

        public String getDate() {
            return date;
        }

        public String getDisplayDate() {
            return display_date;
        }

        public List<String> getImages() {
            return images;
        }
    }
}
