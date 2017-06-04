package cn.neillee.dailyzhijiu.model.bean.orignal;


import java.util.List;

public class HuaBanSplashBean {

    private List<PinsBean> pins;

    public List<PinsBean> getPins() {
        return pins;
    }

    public void setPins(List<PinsBean> pins) {
        this.pins = pins;
    }

    public static class PinsBean {
        /**
         * pin_id : 756114086
         * user_id : 14363515
         * board_id : 30319486
         * file_id : 106221289
         * file : {"id":106221289,"farm":"farm1","bucket":"hbimg","key":"704e3c565a5db25ec6ac46d74affec8005db4e6ad249-YBhFNW","type":"image/png","height":"380","width":"722","frames":"1"}
         * media_type : 0
         * source : zybang.com
         * link : http://www.zybang.com/question/f9af7bd25bf09e1447f2e7da7574c396.html
         * raw_text : #数据库#SQL语句有哪几种类型,各类型的主要作用是什么?_百度作业帮
         * text_meta : {"tags":[{"start":0,"offset":5}]}
         * via : 7
         * via_user_id : 0
         * original : null
         * created_at : 1466324986
         * like_count : 0
         * comment_count : 0
         * repin_count : 0
         * is_private : 0
         * orig_source : null
         * hide_origin : false
         */

        private int pin_id;
        private int user_id;
        private int board_id;
        private int file_id;
        private FileBean file;
        private int media_type;
        private String source;
        private String link;
        private String raw_text;
        private TextMetaBean text_meta;
        private int via;
        private int via_user_id;
        private Object original;
        private int created_at;
        private int like_count;
        private int comment_count;
        private int repin_count;
        private int is_private;
        private Object orig_source;
        private boolean hide_origin;

        public int getPin_id() {
            return pin_id;
        }

        public void setPin_id(int pin_id) {
            this.pin_id = pin_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getBoard_id() {
            return board_id;
        }

        public void setBoard_id(int board_id) {
            this.board_id = board_id;
        }

        public int getFile_id() {
            return file_id;
        }

        public void setFile_id(int file_id) {
            this.file_id = file_id;
        }

        public FileBean getFile() {
            return file;
        }

        public void setFile(FileBean file) {
            this.file = file;
        }

        public int getMedia_type() {
            return media_type;
        }

        public void setMedia_type(int media_type) {
            this.media_type = media_type;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getRaw_text() {
            return raw_text;
        }

        public void setRaw_text(String raw_text) {
            this.raw_text = raw_text;
        }

        public TextMetaBean getText_meta() {
            return text_meta;
        }

        public void setText_meta(TextMetaBean text_meta) {
            this.text_meta = text_meta;
        }

        public int getVia() {
            return via;
        }

        public void setVia(int via) {
            this.via = via;
        }

        public int getVia_user_id() {
            return via_user_id;
        }

        public void setVia_user_id(int via_user_id) {
            this.via_user_id = via_user_id;
        }

        public Object getOriginal() {
            return original;
        }

        public void setOriginal(Object original) {
            this.original = original;
        }

        public int getCreated_at() {
            return created_at;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        public int getLike_count() {
            return like_count;
        }

        public void setLike_count(int like_count) {
            this.like_count = like_count;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public int getRepin_count() {
            return repin_count;
        }

        public void setRepin_count(int repin_count) {
            this.repin_count = repin_count;
        }

        public int getIs_private() {
            return is_private;
        }

        public void setIs_private(int is_private) {
            this.is_private = is_private;
        }

        public Object getOrig_source() {
            return orig_source;
        }

        public void setOrig_source(Object orig_source) {
            this.orig_source = orig_source;
        }

        public boolean isHide_origin() {
            return hide_origin;
        }

        public void setHide_origin(boolean hide_origin) {
            this.hide_origin = hide_origin;
        }

        public static class FileBean {
            /**
             * id : 106221289
             * farm : farm1
             * bucket : hbimg
             * key : 704e3c565a5db25ec6ac46d74affec8005db4e6ad249-YBhFNW
             * type : image/png
             * height : 380
             * width : 722
             * frames : 1
             */

            private int id;
            private String farm;
            private String bucket;
            private String key;
            private String type;
            private String height;
            private String width;
            private String frames;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getFarm() {
                return farm;
            }

            public void setFarm(String farm) {
                this.farm = farm;
            }

            public String getBucket() {
                return bucket;
            }

            public void setBucket(String bucket) {
                this.bucket = bucket;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }

            public String getWidth() {
                return width;
            }

            public void setWidth(String width) {
                this.width = width;
            }

            public String getFrames() {
                return frames;
            }

            public void setFrames(String frames) {
                this.frames = frames;
            }
        }

        public static class TextMetaBean {
            private List<TagsBean> tags;

            public List<TagsBean> getTags() {
                return tags;
            }

            public void setTags(List<TagsBean> tags) {
                this.tags = tags;
            }

            public static class TagsBean {
                /**
                 * start : 0
                 * offset : 5
                 */

                private int start;
                private int offset;

                public int getStart() {
                    return start;
                }

                public void setStart(int start) {
                    this.start = start;
                }

                public int getOffset() {
                    return offset;
                }

                public void setOffset(int offset) {
                    this.offset = offset;
                }
            }
        }
    }
}
