package com.neil.dailyzhihu.ui.aty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.UniversalStoryListAdapter;
import com.neil.dailyzhihu.bean.story.SectionStoryList;
import com.neil.dailyzhihu.utils.Formater;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.LoaderFactory;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Neil on 2016/3/24.
 */
public class SectionActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    @Bind(R.id.tv_sectionName)
    TextView tvSectionName;
    @Bind(R.id.tv_timestamp)
    TextView tvTimestamp;
    @Bind(R.id.lv_stories)
    ListView lvStories;
    private int sectionId = -1;
    private String sectionName = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);
        ButterKnife.bind(this);
        int sectionId = getExtras();
        if (sectionId > 0)
            fillContent(sectionId);
        lvStories.setOnItemClickListener(this);
    }

    private void fillContent(int sectionId) {
        LoaderFactory.getContentLoader().loadContent(Formater.formatUrl(Constant.SECTIONS_HEAD, sectionId), new OnContentLoadingFinishedListener() {
            @Override
            public void onFinish(String content) {
                SectionStoryList sectionStoryList = (SectionStoryList) GsonDecoder.getDecoder().decoding(content, SectionStoryList.class);
                tvSectionName.setText(sectionStoryList.getName());
                tvTimestamp.setText(sectionStoryList.getTimestamp() + "");
                lvStories.setAdapter(new UniversalStoryListAdapter(sectionStoryList.getStories(), SectionActivity.this));
            }
        });
        setActionBarText(sectionId);
    }

    private void setActionBarText(int sectionId) {
        ActionBar ab = getSupportActionBar();
        if (ab == null || sectionId < 0 || sectionName == null || sectionName.equals(""))
            return;
        ab.setTitle("栏目纵览 " + sectionId + "-" + sectionName);
    }

    private int getExtras() {
        int sectionId = -1;
        if (getIntent().getExtras() != null) {
            sectionId = getIntent().getIntExtra(Constant.SECTION_ID, -2);
        }
        return sectionId;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SectionStoryList.StoriesBean bean = (SectionStoryList.StoriesBean) parent.getAdapter().getItem(position);
        int storyId = bean.getStoryId();
        Intent intent = new Intent(SectionActivity.this, StoryActivity.class);
        intent.putExtra(Constant.STORY_ID, storyId);
        SectionActivity.this.startActivity(intent);
    }
}
