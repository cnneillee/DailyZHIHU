package cn.neillee.dailyzhijiu.model.bean.orignal;

import java.util.List;

/**
 * 作者：Neil on 2017/3/31 11:29.
 * 邮箱：cn.neillee@gmail.com
 */

public class ZhihuSplashBean {

    private List<LaunchAdsBean> launch_ads;

    public List<LaunchAdsBean> getLaunch_ads() {
        return launch_ads;
    }

    public void setLaunch_ads(List<LaunchAdsBean> launch_ads) {
        this.launch_ads = launch_ads;
    }

    public static class LaunchAdsBean {
        /**
         * external_click_url :
         * description :
         * view_interval : 7200
         * start_time : 1490270465
         * impression_tracks : ["https://sugar.zhihu.com/track?vs=1&ai=3686&ut=&cg=2&ts=1490270465.89&si=7de6d8cfc9ac49d4930da344ccd470d2&lu=0&hn=ad-engine.ad-engine.e9b87096&at=impression&pf=Android&az=11&sg=692b57e1df8a85e9dadbc06d889e3437"]
         * close_tracks : ["https://sugar.zhihu.com/track?vs=1&ai=3686&ut=&cg=2&ts=1490270465.89&si=7de6d8cfc9ac49d4930da344ccd470d2&lu=0&hn=ad-engine.ad-engine.e9b87096&at=close&pf=Android&az=11&sg=692b57e1df8a85e9dadbc06d889e3437"]
         * id : 3686_0323_0328
         * category : housing
         * za_ad_info : COYcEAsiATI6IDY5MmI1N2UxZGY4YTg1ZTlkYWRiYzA2ZDg4OWUzNDM3UgExXXKnsU4=
         * name : 170323 Henry Be 日报开屏
         * landing_url :
         * max_impression_times : 5
         * click_tracks : []
         * end_time : 1490702465
         * template : image_app_launch_new
         * image : https://pic3.zhimg.com/v2-54de17fbbdd784f3d4bdb6893c4df012_xxdpi.jpg
         */

        private String external_click_url;
        private String description;
        private int view_interval;
        private int start_time;
        private String id;
        private String category;
        private String za_ad_info;
        private String name;
        private String landing_url;
        private int max_impression_times;
        private int end_time;
        private String template;
        private String image;
        private List<String> impression_tracks;
        private List<String> close_tracks;
        private List<?> click_tracks;

        public String getExternal_click_url() {
            return external_click_url;
        }

        public void setExternal_click_url(String external_click_url) {
            this.external_click_url = external_click_url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getView_interval() {
            return view_interval;
        }

        public void setView_interval(int view_interval) {
            this.view_interval = view_interval;
        }

        public int getStart_time() {
            return start_time;
        }

        public void setStart_time(int start_time) {
            this.start_time = start_time;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getZa_ad_info() {
            return za_ad_info;
        }

        public void setZa_ad_info(String za_ad_info) {
            this.za_ad_info = za_ad_info;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLanding_url() {
            return landing_url;
        }

        public void setLanding_url(String landing_url) {
            this.landing_url = landing_url;
        }

        public int getMax_impression_times() {
            return max_impression_times;
        }

        public void setMax_impression_times(int max_impression_times) {
            this.max_impression_times = max_impression_times;
        }

        public int getEnd_time() {
            return end_time;
        }

        public void setEnd_time(int end_time) {
            this.end_time = end_time;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public List<String> getImpression_tracks() {
            return impression_tracks;
        }

        public void setImpression_tracks(List<String> impression_tracks) {
            this.impression_tracks = impression_tracks;
        }

        public List<String> getClose_tracks() {
            return close_tracks;
        }

        public void setClose_tracks(List<String> close_tracks) {
            this.close_tracks = close_tracks;
        }

        public List<?> getClick_tracks() {
            return click_tracks;
        }

        public void setClick_tracks(List<?> click_tracks) {
            this.click_tracks = click_tracks;
        }
    }
}
