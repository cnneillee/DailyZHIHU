package com.neil.dailyzhihu.model.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * 作者：Neil on 2016/4/27 15:11.
 * 邮箱：cn.neillee@gmail.com
 */
@Entity(nameInDb = "star_record", generateGettersSetters = false)
public class StarRecord {
    @Id(autoincrement = true)
    private Long _id;
    @Property(nameInDb = "story_id")
    private int storyId;
    @Property(nameInDb = "timestamp")
    private long timestamp;

    public StarRecord(int storyId, long timestamp) {
        this.storyId = storyId;
        this.timestamp = timestamp;
    }

    @Generated(hash = 1746535335)
    public StarRecord(Long _id, int storyId, long timestamp) {
        this._id = _id;
        this.storyId = storyId;
        this.timestamp = timestamp;
    }

    @Generated(hash = 1872581448)
    public StarRecord() {
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
