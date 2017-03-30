package com.neil.dailyzhihu.ui.topic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.TopicEditorListAdapter;
import com.neil.dailyzhihu.api.AtyExtraKeyConstant;
import com.neil.dailyzhihu.bean.orignal.TopicStoryListBean;
import com.neil.dailyzhihu.ui.NightModeBaseActivity;
import com.neil.dailyzhihu.ui.aty.WebViewActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：Neil on 2017/3/27 13:57.
 * 邮箱：cn.neillee@gmail.com
 */

public class TopicEditorsActivity extends NightModeBaseActivity implements AdapterView.OnItemClickListener {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.lv_editors)
    ListView mLvEditors;

    private View.OnClickListener upBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_topic_editors);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
        mToolbar.setNavigationOnClickListener(upBtnListener);
        getExtras();
    }

    public void getExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        String topicName = bundle.getString(AtyExtraKeyConstant.TOPIC_NAME);
        String editorsStr = bundle.getString(AtyExtraKeyConstant.EDITORS_LIST);
        Gson gson = new Gson();
        List<TopicStoryListBean.EditorsBean> editorsBeanList = gson.fromJson(editorsStr,
                new TypeToken<List<TopicStoryListBean.EditorsBean>>() {
                }.getType());
        mLvEditors.setAdapter(new TopicEditorListAdapter(this, editorsBeanList));
        mLvEditors.setOnItemClickListener(this);
        setTitle("主编 · " + topicName);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TopicStoryListBean.EditorsBean bean = (TopicStoryListBean.EditorsBean) parent.getAdapter().getItem(position);
        String url = bean.getUrl();
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(AtyExtraKeyConstant.WEB_URL, url);
        startActivity(intent);
    }

    @Override
    public void setTitle(CharSequence title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setTitle(title);
    }
}
