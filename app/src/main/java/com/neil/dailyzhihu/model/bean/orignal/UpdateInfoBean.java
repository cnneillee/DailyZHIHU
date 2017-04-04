package com.neil.dailyzhihu.model.bean.orignal;

import java.util.List;

/**
 * 作者：Neil on 2017/3/24 11:51.
 * 邮箱：cn.neillee@gmail.com
 */

public class UpdateInfoBean extends OriginalStory{

    /**
     * version_name : 0.2.1
     * version_code : 2
     * description : 这是一个最新的版本，主要功能有：1.blablabla
     2.blablabla
     3.blablabla
     4.dudududud
     * released_at : 20170324
     * size : 4923038
     * debug : 0
     * url : https://lilinmao.github.io/projects/dailyzhihu/apks/app-debug-v0.2.1-2.apk
     * history_versions : [{"version_name":"0.2.1","version_code":2,"released_at":"20170324","description":"这是一个最新的版本，主要功能有：1.blablabla\n2.blablabla\n3.blablabla\n4.dudududud","size":2164180,"debug":0,"url":"https://lilinmao.github.io/projects/dailyzhihu/apks/app-debug-v0.1.1-1.apk"}]
     */

    private String version_name;
    private int version_code;
    private String description;
    private String released_at;
    private int size;
    private int debug;
    private String url;
    private List<HistoryVersionsBean> history_versions;

    public String getVersionName() {
        return version_name;
    }

    public void setVersionName(String version_name) {
        this.version_name = version_name;
    }

    public int getVersionCode() {
        return version_code;
    }

    public void setVersionCode(int version_code) {
        this.version_code = version_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleasedAt() {
        return released_at;
    }

    public void setReleasedAt(String released_at) {
        this.released_at = released_at;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDebug() {
        return debug;
    }

    public void setDebug(int debug) {
        this.debug = debug;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<HistoryVersionsBean> getHistoryVersions() {
        return history_versions;
    }

    public void setHistoryVersions(List<HistoryVersionsBean> history_versions) {
        this.history_versions = history_versions;
    }

    public static class HistoryVersionsBean {
        /**
         * version_name : 0.2.1
         * version_code : 2
         * released_at : 20170324
         * description : 这是一个最新的版本，主要功能有：1.blablabla
         2.blablabla
         3.blablabla
         4.dudududud
         * size : 2164180
         * debug : 0
         * url : https://lilinmao.github.io/projects/dailyzhihu/apks/app-debug-v0.1.1-1.apk
         */

        private String version_name;
        private int version_code;
        private String released_at;
        private String description;
        private int size;
        private int debug;
        private String url;

        public String getVersionName() {
            return version_name;
        }

        public void setVersionName(String version_name) {
            this.version_name = version_name;
        }

        public int getVersionCode() {
            return version_code;
        }

        public void setVersionCode(int version_code) {
            this.version_code = version_code;
        }

        public String getReleasedAt() {
            return released_at;
        }

        public void setReleasedAt(String released_at) {
            this.released_at = released_at;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getDebug() {
            return debug;
        }

        public void setDebug(int debug) {
            this.debug = debug;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
