package com.neil.dailyzhihu.ui.topic;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.neil.dailyzhihu.adapter.TopicStoryListAdapter;
import com.neil.dailyzhihu.api.API;
import com.neil.dailyzhihu.listener.OnContentLoadedListener;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.TopicEditorListAdapter;
import com.neil.dailyzhihu.bean.orignal.TopicStoryListBean;
import com.neil.dailyzhihu.ui.story.StoryDetailActivity;
import com.neil.dailyzhihu.ui.widget.BaseActivity;
import com.neil.dailyzhihu.api.AtyExtraKeyConstant;
import com.neil.dailyzhihu.utils.Formater;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.load.LoaderFactory;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：Neil on 2016/3/23 11:05.
 * 邮箱：cn.neillee@gmail.com
 */
public class CertainTopicActivity extends BaseActivity implements
        AdapterView.OnItemClickListener, View.OnClickListener {

    @Bind(R.id.toolbar)
    Toolbar mToolbarView;
    @Bind(R.id.lv_theme_story_list)
    ListView mlvStoryList;

    // 当前Theme的Id
    private int themeId = -1;
    private String defaultImgUrl;

    // 添加的Theme介绍、编辑介绍等
    private TextView tvIntro;
    private ListView lvEditor;
    private TextView tvLoadingEditor;
    private LinearLayout llEditorLVWrapper;
    private View.OnClickListener upBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CertainTopicActivity.this.finish();
        }
    };

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_topic_detail);
        ButterKnife.bind(this);

        // 对ActionBar进行设置
        setSupportActionBar(mToolbarView);
        mToolbarView.setNavigationIcon(R.drawable.ic_action_back);
        mToolbarView.setNavigationOnClickListener(upBtnListener);

        // 设置HeaderView
        View introHeaderView = getLayoutInflater().inflate(R.layout.lv_header_theme_acty, null);
        tvIntro = (TextView) introHeaderView.findViewById(R.id.tv_header);
        mlvStoryList.addHeaderView(introHeaderView, null, false);
        setDummyData(mlvStoryList);

        // 设置FooterView
        View editorFooter = getLayoutInflater().inflate(R.layout.lv_footer_theme_acty, null);
        lvEditor = (ListView) editorFooter.findViewById(R.id.lv_editor);
        llEditorLVWrapper = (LinearLayout) editorFooter.findViewById(R.id.ll_editorLVWrapper);
        tvLoadingEditor = (TextView) editorFooter.findViewById(R.id.tv_loading_editor);
        tvLoadingEditor.setOnClickListener(this);
        mlvStoryList.addFooterView(editorFooter, null, false);
        llEditorLVWrapper.setVisibility(View.GONE);

        // 获取ThemeId，填充内容
        getExtras();
        if (themeId > 0)
            fillContent();

        // 设置StoryList的Item点击事件
        mlvStoryList.setOnItemClickListener(this);
    }

    /*获取themeId*/
    private int getExtras() {
        if (getIntent().getExtras() != null) {
            themeId = getIntent().getIntExtra(AtyExtraKeyConstant.THEME_ID, -2);
            defaultImgUrl = getIntent().getStringExtra(AtyExtraKeyConstant.DEFAULT_IMG_URL);
        }
        return themeId;
    }

    /*填充内容*/
    private void fillContent() {
        LoaderFactory.getContentLoader().loadContent(Formater.formatUrl(API.THEME_PREFIX, themeId),
                new OnContentLoadedListener() {
                    @Override
                    public void onSuccess(String content, String url) {
                        Logger.json(content);
                        TopicStoryListBean topicStoryListBean = GsonDecoder.getDecoder().decoding(content, TopicStoryListBean.class);
                        TopicStoryListAdapter adapter = new TopicStoryListAdapter(CertainTopicActivity.this, topicStoryListBean);
                        adapter.setDefaultImgUrl(defaultImgUrl);
                        mlvStoryList.setAdapter(adapter);

//                String themeBGImgUrl = cleanThemeStoryListBean.getBackground();
                        String introDes = topicStoryListBean.getDescription();
                        String actionbarTitle = topicStoryListBean.getName();
                        tvIntro.setText(introDes);
                        setActionBarText(actionbarTitle);
//                LoaderFactory.getImageLoader().displayImage(mImageView, themeBGImgUrl, null);
                        List<TopicStoryListBean.EditorsBean> editorsBeanList = topicStoryListBean.getEditors();
                        lvEditor.setAdapter(new TopicEditorListAdapter(CertainTopicActivity.this, editorsBeanList));
                    }
                });
    }

    /*设置标题*/
    private void setActionBarText(String themeName) {
        ActionBar ab = getSupportActionBar();
        if (ab == null || themeId < 0) return;
        ab.setTitle("日报·主题\t" + themeName);
    }

    /*某一主题日报中的某个新闻项被点击*/
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TopicStoryListBean.TopicStory bean = (TopicStoryListBean.TopicStory) parent.getAdapter().getItem(position);
        int storyId = bean.getStoryId();
        defaultImgUrl = (bean.getImage() == null) ? defaultImgUrl : bean.getImage();
        Intent intent = new Intent(this, StoryDetailActivity.class);
        intent.putExtra(AtyExtraKeyConstant.STORY_ID, storyId);
        intent.putExtra(AtyExtraKeyConstant.DEFAULT_IMG_URL, defaultImgUrl);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        llEditorLVWrapper.setVisibility(View.VISIBLE);
        tvLoadingEditor.setText("当当当当！下面是我们哒编辑~~~");
    }
}
