package com.neil.dailyzhihu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.neil.dailyzhihu.MyApplication;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.EditorsListAdapter;
import com.neil.dailyzhihu.adapter.LatestNewsAdapter;
import com.neil.dailyzhihu.adapter.ThemeStoriesAdapter;
import com.neil.dailyzhihu.bean.ThemeStoriesList;
import com.neil.dailyzhihu.utils.ContentLoader;
import com.neil.dailyzhihu.utils.ImageLoader;
import com.neil.dailyzhihu.utils.UniversalLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Neil on 2016/3/23.
 */
public class ThemeActivity extends AppCompatActivity {


    @Bind(R.id.lv_stories)
    ListView lvStories;
    @Bind(R.id.lv_editor)
    ListView lvEditor;
    @Bind(R.id.img_bg)
    ImageView imgBg;
    @Bind(R.id.tv_descripsion)
    TextView tvDescripsion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        ButterKnife.bind(this);
        int themeId = getExtras();
        if (themeId > 0)
            fillingContent(themeId);
    }

    private int getExtras() {
        int themeId = -1;
        if (getIntent().getExtras() != null) {
            themeId = getIntent().getIntExtra("THEME_ID", 0);
        }
        Log.e("THEME_ACTIVITY", "---" + themeId);
        return themeId;
    }


    private void fillingContent(final int themeId) {
        ContentLoader.loadString("http://news-at.zhihu.com/api/4/theme/" + themeId, new ImageLoader.OnFinishListener() {
            @Override
            public void onFinish(Object s) {
                Log.e("THEME_ACTIVITY", "------" + (String) s);
                Gson gson = new Gson();
                ThemeStoriesList themeStoriesList = gson.fromJson((String) s, ThemeStoriesList.class);
                List<ThemeStoriesList.StoriesBean> storiesBeanList = themeStoriesList.getStories();

                lvStories.setAdapter(new ThemeStoriesAdapter(storiesBeanList, ThemeActivity.this));
                lvStories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ThemeStoriesList.StoriesBean story = (ThemeStoriesList.StoriesBean) parent.getAdapter().getItem(position);
                        int storyId = story.getId();
                        Intent intent = new Intent(ThemeActivity.this, StoryActivity.class);
                        intent.putExtra("STORY_ID", storyId);
                        ThemeActivity.this.startActivity(intent);
                    }
                });
                List<ThemeStoriesList.EditorsBean> editorsBeanList = themeStoriesList.getEditors();
                lvEditor.setAdapter(new EditorsListAdapter(ThemeActivity.this, editorsBeanList));
                Log.e("THEME_ACTIVITY", "新闻数目------" + storiesBeanList.size() + "，编辑数目------" + editorsBeanList.size());
                //TODO 图片加载
                MyApplication myApplication = (MyApplication) ThemeActivity.this.getApplicationContext();
                UniversalLoader loader = myApplication.getUniversalLoader();
                loader.loadImage(ThemeActivity.this,imgBg, themeStoriesList.getBackground(), null);
               // ImageLoader.loadImage(imgBg, themeStoriesList.getBackground(), null);
            }
        });


    }


}
