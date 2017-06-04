package cn.neillee.dailyzhijiu.model.bean.orignal;


import java.util.List;

/**
 * 作者：Neil on 2016/3/23 14:11.
 * 邮箱：cn.neillee@gmail.com
 */
public class CommentListBean extends OriginalStory {
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
        /**
         * author : 楚亦锦
         * content : 第三个，那本书叫《数字图像处理》（估计是讲类似PS 毁图秀秀等一众软件的底层技术），然后作者在扉页写到： 致我无需修图的妻子，Shelly。 嗯……当着众读者的面讨好媳妇，秀一波恩爱 (╯°□°）╯︵ ┻━┻
         * avatar : http://pic4.zhimg.com/c98bc5e4f_im.jpg
         * time : 1488589198
         * id : 28293001
         * likes : 76
         * reply_to : {"content":"翻译咋说的？","status":0,"id":28292612,"author":"李春骋"}
         */

        private String author;
        private String content;
        private String avatar;
        private int time;
        private int id;
        private int likes;
        private ReplyToBean reply_to;

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

        public ReplyToBean getReplyTo() {
            return reply_to;
        }

        public void setReplyTo(ReplyToBean reply_to) {
            this.reply_to = reply_to;
        }

        public class ReplyToBean {
            /**
             * content : 翻译咋说的？
             * status : 0
             * id : 28292612
             * author : 李春骋
             */

            private String content;
            private int status;
            private int id;
            private String author;
            private String err_msg;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getErrMsg() {
                return err_msg;
            }

            public void setErrMsg(String err_msg) {
                this.err_msg = err_msg;
            }
        }
    }
}
