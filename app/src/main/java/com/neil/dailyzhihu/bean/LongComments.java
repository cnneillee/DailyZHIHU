package com.neil.dailyzhihu.bean;

import java.util.List;

/**
 * Created by Neil on 2016/3/23.
 */
public class LongComments {
    /**
     * author : 思霖Candy
     * content : 估计是我喜欢清淡吧，舌头很敏感，一直觉得飞机餐挺好吃的。感觉到的问题，一个是味道不纯粹，可能饭菜一起加热的缘故，然后味觉下降应该是感觉的到的，以前不知道，还以为是环境影响的～
     * <p/>
     * 最后，如果在南航厦航海航上看到一个问空姐要第二份的蓝荒人，还吃的一点不剩，很可能是我～
     * avatar : http://pic1.zhimg.com/4624d6db4_im.jpg
     * time : 1413721757
     * id : 549001
     * likes : 1
     */

    private List<CommentsBean> comments;

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public class CommentsBean {
        private String author;
        private String content;
        private String avatar;
        private int time;
        private int id;
        private int likes;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }
    }
}
