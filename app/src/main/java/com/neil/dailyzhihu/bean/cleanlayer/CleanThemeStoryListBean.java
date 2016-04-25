package com.neil.dailyzhihu.bean.cleanlayer;

import java.util.List;

/**
 * Created by Neil on 2016/4/25.
 */
public class CleanThemeStoryListBean extends CleanStoryListBean {
    private String name;
    private String description;
    private String background;
    private String image;
    private String imageSource;
    private int color;

    private List<SimpleStory> simpleStoryList;
    private List<EditorBean> editorBeanList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public List<SimpleStory> getSimpleStoryList() {
        return simpleStoryList;
    }

    public void setSimpleStoryList(List<SimpleStory> simpleStoryList) {
        this.simpleStoryList = simpleStoryList;
    }

    public List<EditorBean> getEditorBeanList() {
        return editorBeanList;
    }

    public void setEditorBeanList(List<EditorBean> editorBeanList) {
        this.editorBeanList = editorBeanList;
    }
}
