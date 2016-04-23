package com.neil.dailyzhihu.ui.aty;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.utils.db.FavoriteStory;
import com.neil.dailyzhihu.utils.db.FavoriteStoryDBdao;

import java.util.List;

/**
 * Created by Neil on 2016/4/22.
 */
public class TestActivityDB extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FavoriteStoryDBdao dao = new FavoriteStoryDBdao(this);
        FavoriteStory story = makingData();
        dao.addStory(story);
        List<FavoriteStory> storyres = dao.queryStoryById(123456);
        if (storyres != null && storyres.size() > 0) {
            FavoriteStory story1 = storyres.get(0);
            Log.e("LOG_TAG", story1.toString());
        }
    }

    private FavoriteStory makingData() {
        FavoriteStory story = new FavoriteStory(123456 + "", "地生产，直到最近几年才前些年有 75% 的", "12345678945", "title标题", "http://daily.zhihu.com/story/3892357", "http://p4.zhimg.com/30/59/30594279d368534c6c2f91b2c00c7806.jpg", "imgsrc", " 面料：这和优衣库的历史有关系，当年火遍全日本并且至今仍然每年秋冬都会推出新款的抓绒外套让优衣库尝到了因为面料而带来的甜头，自此优衣库在面料的使用和研发上不断创新，比如内蒙古的一个羊毛牧场专门饲养给优衣库提供面料", "123456789455", "1234567899955", "imgpath", "01234545", null);
        return story;
    }

}
