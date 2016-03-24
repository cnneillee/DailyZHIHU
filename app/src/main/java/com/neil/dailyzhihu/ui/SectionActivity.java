package com.neil.dailyzhihu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.SectionStoriesAdapter;
import com.neil.dailyzhihu.bean.SectionStoriesList;
import com.neil.dailyzhihu.utils.ContentLoader;
import com.neil.dailyzhihu.utils.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Neil on 2016/3/24.
 */
public class SectionActivity extends AppCompatActivity {
    @Bind(R.id.tv_sectionName)
    TextView tvSectionName;
    @Bind(R.id.tv_timestamp)
    TextView tvTimestamp;
    @Bind(R.id.lv_stories)
    ListView lvStories;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);
        ButterKnife.bind(this);
        int sectionId = getExtras();
        if (sectionId > 0)
            fillContent(sectionId);
    }

    private void fillContent(int sectionId) {
        ContentLoader.loadString("http://news-at.zhihu.com/api/3/section/" + sectionId, new ImageLoader.OnFinishListener() {
            @Override
            public void onFinish(Object s) {
                Gson gson = new Gson();
                SectionStoriesList sectionStoriesList = gson.fromJson((String) s, SectionStoriesList.class);
                tvSectionName.setText(sectionStoriesList.getName());
                tvTimestamp.setText(sectionStoriesList.getTimestamp() + "");
                lvStories.setAdapter(new SectionStoriesAdapter(SectionActivity.this, sectionStoriesList.getStories()));
                lvStories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        SectionStoriesList.StoriesBean bean = (SectionStoriesList.StoriesBean) parent.getAdapter().getItem(position);
                        int storyId = bean.getId();
                        Intent intent = new Intent(SectionActivity.this, StoryActivity.class);
                        intent.putExtra("STORY_ID", storyId);
                        SectionActivity.this.startActivity(intent);
                    }
                });
            }
        });

    }

    private int getExtras() {
        int sectionId = -1;
        if (getIntent().getExtras() != null) {
            sectionId = getIntent().getIntExtra("SECTION_ID", 0);
        }
        return sectionId;
    }


}
