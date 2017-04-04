package com.neil.dailyzhihu.model.bean.orignal;

/**
 * 作者：Neil on 2016/3/23 14:17.
 * 邮箱：cn.neillee@gmail.com
 */
public class StoryExtraInfoBean extends OriginalStory {
    /**
     * post_reasons : 0
     * long_comments : 14
     * popularity : 357
     * normal_comments : 95
     * comments : 95
     * short_comments : 81
     */

    private int post_reasons;
    private int long_comments;
    private int popularity;
    private int normal_comments;
    private int comments;
    private int short_comments;

    public int getPost_reasons() {
        return post_reasons;
    }

    public int getLongComments() {
        return long_comments;
    }

    public int getPopularity() {
        return popularity;
    }

    public int getNormalComments() {
        return normal_comments;
    }

    public int getComments() {
        return comments;
    }

    public int getShortComments() {
        return short_comments;
    }
}
