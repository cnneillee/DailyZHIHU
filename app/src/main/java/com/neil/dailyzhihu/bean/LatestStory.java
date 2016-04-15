package com.neil.dailyzhihu.bean;

import java.util.List;

/**
 * Created by Neil on 2016/3/23.
 */
public class LatestStory {
    /**
     * date : 20160322
     * stories : [{"images":["http://pic3.zhimg.com/8716a2a27e1e20562698a873c0672c9a.jpg"],"type":0,"id":8038222,"ga_prefix":"032222","title":"深夜惊奇 · 捡了个 iPhone"},{"images":["http://pic3.zhimg.com/6a7d21c62a61e1175ec81a068c9a6476.jpg"],"type":0,"id":8033546,"ga_prefix":"032221","title":"先不谈情节，《荒野猎人》的镜头实在太美了"},{"title":"灵魂画手带你变身蚁人，混入《骨头大战》片场","ga_prefix":"032220","images":["http://pic3.zhimg.com/1e56af96cc34b6fb446177675f3f842e.jpg"],"multipic":true,"type":0,"id":8032340},{"images":["http://pic3.zhimg.com/d0f9344f7dfa3e4a69046326c331e2a6.jpg"],"type":0,"id":8037777,"ga_prefix":"032219","title":"能被成功改编成电影的科幻小说都好在哪里？"},{"images":["http://pic2.zhimg.com/aa1bfd51128117f5f495034dddc2008d.jpg"],"type":0,"id":8037704,"ga_prefix":"032218","title":"看了《疫苗之殇》，关于疫苗你担心的所有问题，这里都有解答"},{"title":"收得此酱，你将拥有意大利的半壁江山","ga_prefix":"032217","images":["http://pic1.zhimg.com/c7154d90f465d1aa06e0aea40ccaac18.jpg"],"multipic":true,"type":0,"id":8036413},{"images":["http://pic1.zhimg.com/60a7cb7b451fa7ce88afb447df6c00a4.jpg"],"type":0,"id":8036196,"ga_prefix":"032216","title":"日本朋友说「Surplise」的时候，你可别 Surprise"},{"images":["http://pic1.zhimg.com/8e2e36d67fcf9dd21da7b915e04454dc.jpg"],"type":0,"id":8037120,"ga_prefix":"032215","title":"关于性别，给孩子成长为「自己」的机会"},{"images":["http://pic2.zhimg.com/5af5c1e5b7c23126cd019c038d1f1809.jpg"],"type":0,"id":8033677,"ga_prefix":"032214","title":"《名侦探柯南》剧场版的制作内幕"},{"images":["http://pic4.zhimg.com/2736f3dce99dd7af678746261b048773.jpg"],"type":0,"id":8036346,"ga_prefix":"032213","title":"理清「山东疫苗案」中的重要信息，回答三个问题"},{"title":"这里有个让人少女心爆棚的彩蛋","ga_prefix":"032211","images":["http://pic3.zhimg.com/6f6d6369f0e5aa584f09a4139c811f7e.jpg"],"multipic":true,"type":0,"id":8036018},{"images":["http://pic3.zhimg.com/2c41192240fdaa27ec6c7aabab4cd572.jpg"],"type":0,"id":8035901,"ga_prefix":"032210","title":"随意放生牛蛙，最高被罚款 50 万元"},{"images":["http://pic3.zhimg.com/bb4c2b769bd2c12fd8e07b1214e80906.jpg"],"type":0,"id":8032770,"ga_prefix":"032209","title":"要克制大笑、别吞灯泡，谨代表下巴和急诊大夫感谢您"},{"images":["http://pic2.zhimg.com/842ccee13b90c3d1e16378be0e4f5d9d.jpg"],"type":0,"id":8010252,"ga_prefix":"032208","title":"机智的人类：加一片小翼，省了几十米的跑道"},{"images":["http://pic3.zhimg.com/621f8e8afa3070d5bc2d55faaae3eb0e.jpg"],"type":0,"id":8028374,"ga_prefix":"032207","title":"如何看待 papi 酱获 1200 万元投资？"},{"images":["http://pic4.zhimg.com/c2312e356516d683c7a00dcbbf562c77.jpg"],"type":0,"id":8032023,"ga_prefix":"032207","title":"这一天，美国绿了"},{"title":"不穿的牛仔裤这样利用，好看好用又省钱","ga_prefix":"032207","images":["http://pic4.zhimg.com/a03102ae46fc56a3f133dd4be8e02d4f.jpg"],"multipic":true,"type":0,"id":8032699},{"images":["http://pic4.zhimg.com/98dfcf1f56b20e7e2f94777d209d4d4b.jpg"],"type":0,"id":8034476,"ga_prefix":"032207","title":"读读日报 24 小时热门 TOP 5 · 上海人在他笔下，有烟火气，且体面"},{"images":["http://pic1.zhimg.com/b6de5f3627c7e222a59f2f0c54bd4b00.jpg"],"type":0,"id":8021097,"ga_prefix":"032206","title":"瞎扯 · 跟老师作对的奇葩方法"}]
     * top_stories : [{"image":"http://pic4.zhimg.com/1511d543c8296da460737c7e61dd89b3.jpg","type":0,"id":8033546,"ga_prefix":"032221","title":"先不谈情节，《荒野猎人》的镜头实在太美了"},{"image":"http://pic1.zhimg.com/41c808741bc49910f0a126716a336854.jpg","type":0,"id":8037704,"ga_prefix":"032218","title":"看了《疫苗之殇》，关于疫苗你担心的所有问题，这里都有解答"},{"image":"http://pic1.zhimg.com/6253f49adffd754cd8adc2ed6f7d5d9c.jpg","type":0,"id":8036346,"ga_prefix":"032213","title":"理清「山东疫苗案」中的重要信息，回答三个问题"},{"image":"http://pic1.zhimg.com/a7abd45b1fa3b11c4edc9ecffe4e6e60.jpg","type":0,"id":8036018,"ga_prefix":"032211","title":"这里有个让人少女心爆棚的彩蛋"},{"image":"http://pic4.zhimg.com/0958e81844ea4c93eb373499790a4d63.jpg","type":0,"id":8028374,"ga_prefix":"032207","title":"如何看待 papi 酱获 1200 万元投资？"}]
     */

    private String date;
    /**
     * images : ["http://pic3.zhimg.com/8716a2a27e1e20562698a873c0672c9a.jpg"]
     * type : 0
     * id : 8038222
     * ga_prefix : 032222
     * title : 深夜惊奇 · 捡了个 iPhone
     */

    private List<StoriesBean> stories;
    /**
     * image : http://pic4.zhimg.com/1511d543c8296da460737c7e61dd89b3.jpg
     * type : 0
     * id : 8033546
     * ga_prefix : 032221
     * title : 先不谈情节，《荒野猎人》的镜头实在太美了
     */

    private List<TopStoriesBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }

    public class StoriesBean extends StoryBean {
        private int type;
        private int id;
        private String ga_prefix;
        private String title;
        private List<String> images;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }

    public class TopStoriesBean {
        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
