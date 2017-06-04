package cn.neillee.dailyzhijiu.model.db;

import cn.neillee.dailyzhijiu.model.bean.IBlockBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Neil on 2017/6/1 13:06.
 * 邮箱：cn.neillee@gmail.com
 */
@Entity(generateGettersSetters = false, nameInDb = "cached_story")
public class CachedStory implements IBlockBean {
    @Id(autoincrement = true)
    private Long _id;
    @Unique
    @Property(nameInDb = "story_id")
    private int storyId;
    @Property(nameInDb = "title")
    private String title;
    @Property(nameInDb = "body")
    private String body;
    @Property(nameInDb = "image")
    private String image;
    @Property(nameInDb = "image_source")
    private String imageSource;

    public CachedStory(int storyId, String title, String body, String image, String imageSource) {
        this.storyId = storyId;
        this.title = title;
        this.body = body;
        this.image = image;
        this.imageSource = imageSource;
    }

    @Generated(hash = 2001526795)
    public CachedStory(Long _id, int storyId, String title, String body, String image,
                       String imageSource) {
        this._id = _id;
        this.storyId = storyId;
        this.title = title;
        this.body = body;
        this.image = image;
        this.imageSource = imageSource;
    }

    @Generated(hash = 1718393559)
    public CachedStory() {
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

    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return title;
    }

    @Override
    public List<String> getImages() {
        List<String> images = new ArrayList<>();
        images.add(image);
        return images;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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
}
