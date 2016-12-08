package com.neil.dailyzhihu.bean.cleanlayer;

/**
 * Created by Neil on 2016/5/8.
 */
public class CleanStoryExtra {
    private String longComment;
    private String shortComment;
    private String popu;

    public CleanStoryExtra(String longComment, String shortComment, String popu) {

        this.longComment = longComment;
        this.shortComment = shortComment;
        this.popu = popu;
    }

    public CleanStoryExtra() {
    }

    public String getLongComment() {
        return longComment;
    }

    public String getShortComment() {
        return shortComment;
    }

    public String getPopu() {
        return popu;
    }

    public void setLongComment(String longComment) {
        this.longComment = longComment;
    }

    public void setShortComment(String shortComment) {
        this.shortComment = shortComment;
    }

    public void setPopu(String popu) {
        this.popu = popu;
    }
}
