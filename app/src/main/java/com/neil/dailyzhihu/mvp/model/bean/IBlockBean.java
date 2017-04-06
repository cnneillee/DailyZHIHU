package com.neil.dailyzhihu.mvp.model.bean;

import java.util.List;

/**
 * 根据共性，得出所有的Story都有ID，Title，Img（数组）属性
 * Created by Neil on 2016/4/17.
 */
public interface IBlockBean {
    public int getStoryId();

    public List<String> getImages();

    public String getTitle();

    public String getDescription();

}
