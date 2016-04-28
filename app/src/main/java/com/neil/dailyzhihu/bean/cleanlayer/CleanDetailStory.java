package com.neil.dailyzhihu.bean.cleanlayer;

import java.util.List;

public class CleanDetailStory {
    private String body;
    private String imageSource;
    private String title;
    private String image;
    private String shareUrl;
    private String gaPrefix;

    private CleanSectionAndThemeBean mCleanSectionAndThemeBean;
    private int type;
    private int storyId;
    private List<?> js;
    private List<String> images;
    private List<String> css;

    public CleanDetailStory() {
    }

    public CleanDetailStory(String body, String imageSource, String title, String image, String shareUrl, String gaPrefix, CleanSectionAndThemeBean cleanSectionAndThemeBean, int type, int storyId, List<?> js, List<String> images, List<String> css) {
        this.body = body;
        this.imageSource = imageSource;
        this.title = title;
        this.image = image;
        this.shareUrl = shareUrl;
        this.gaPrefix = gaPrefix;
        this.mCleanSectionAndThemeBean = cleanSectionAndThemeBean;
        this.type = type;
        this.storyId = storyId;
        this.js = js;
        this.images = images;
        this.css = css;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getGaPrefix() {
        return gaPrefix;
    }

    public void setGaPrefix(String gaPrefix) {
        this.gaPrefix = gaPrefix;
    }

    public CleanSectionAndThemeBean getCleanSectionAndThemeBean() {
        return mCleanSectionAndThemeBean;
    }

    public void setCleanSectionAndThemeBean(CleanSectionAndThemeBean cleanSectionAndThemeBean) {
        this.mCleanSectionAndThemeBean = cleanSectionAndThemeBean;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public List<?> getJs() {
        return js;
    }

    public void setJs(List<?> js) {
        this.js = js;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getCss() {
        return css;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }
}
