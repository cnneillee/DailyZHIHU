package com.neil.dailyzhihu.ui.aty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.EditorListAdapter;
import com.neil.dailyzhihu.adapter.UniversalStoryListAdapter;
import com.neil.dailyzhihu.bean.story.ThemeStoryList;
import com.neil.dailyzhihu.utils.Formater;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.LoaderFactory;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Neil on 2016/3/23.
 */
public class ThemeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
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
        lvStories.setOnItemClickListener(this);
    }

    private int getExtras() {
        int themeId = -2;
        if (getIntent().getExtras() != null) {
            themeId = getIntent().getIntExtra(Constant.THEME_ID, -1);
        }
        return themeId;
    }

    private void fillingContent(final int themeId) {
        LoaderFactory.getContentLoader().loadContent(Formater.formatUrl(Constant.THEME_HEAD, themeId), new OnContentLoadingFinishedListener() {
            @Override
            public void onFinish(String content) {
                ThemeStoryList themeStoryList = (ThemeStoryList) GsonDecoder.getDecoder().decoding(content, ThemeStoryList.class);
                if (themeStoryList == null) return;
                loadBackground(themeStoryList.getBackground(), imgBg);
                tvDescripsion.setText(themeStoryList.getDescription());
                List<ThemeStoryList.StoriesBean> storiesBeanList = themeStoryList.getStories();
                lvStories.setAdapter(new UniversalStoryListAdapter(storiesBeanList, ThemeActivity.this));
                List<ThemeStoryList.EditorsBean> editorsBeanList = themeStoryList.getEditors();
                lvEditor.setAdapter(new EditorListAdapter(ThemeActivity.this, editorsBeanList));
            }

            private void loadBackground(String background, ImageView imgBg) {
                LoaderFactory.getImageLoader().displayImage(imgBg, background, null);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ThemeStoryList.StoriesBean story = (ThemeStoryList.StoriesBean) parent.getAdapter().getItem(position);
        int storyId = story.getStoryId();
        Intent intent = new Intent(ThemeActivity.this, StoryActivity.class);
        intent.putExtra(Constant.STORY_ID, storyId);
        ThemeActivity.this.startActivity(intent);
    }
}
