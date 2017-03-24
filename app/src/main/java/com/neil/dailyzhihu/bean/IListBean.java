package com.neil.dailyzhihu.bean;

import java.util.List;

/**
 * 作者：Neil on 2017/3/21 17:23.
 * 邮箱：cn.neillee@gmail.com
 */

public interface IListBean<T extends IStoryBean> {
    public abstract List<IStoryBean> getStories();
}
