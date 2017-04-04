package com.neil.dailyzhihu.model.bean;

/**
 * 根据共性，得出所有的Story都有ID，Title，Img（数组）属性
 * Created by Neil on 2016/4/17.
 */
public interface IStoryBean {
    public int getStoryId();

    public String getImage();

    public String getTitle();
}
