package cn.neillee.dailyzhijiu.ui.topic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neil.dailyzhijiu.R;
import cn.neillee.dailyzhijiu.base.BaseSimpleActivity;
import cn.neillee.dailyzhijiu.model.bean.orignal.TopicStoryListBean;
import cn.neillee.dailyzhijiu.model.http.api.AtyExtraKeyConstant;
import cn.neillee.dailyzhijiu.ui.adapter.TopicEditorListAdapter;
import cn.neillee.dailyzhijiu.ui.aty.WebViewActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：Neil on 2017/3/27 13:57.
 * 邮箱：cn.neillee@gmail.com
 */

public class TopicEditorsActivity extends BaseSimpleActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.lv_editors)
    ListView mLvEditors;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_topic_editors;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        setupToolbar(mToolbar);
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
        setTitle(getResources().getString(R.string.activity_editor) + " · " + topicName);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TopicStoryListBean.EditorsBean bean = (TopicStoryListBean.EditorsBean) parent.getAdapter().getItem(position);
        String url = "http://news-at.zhihu.com/api/4/editor/" + bean.getId() + "/profile-page/android";
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
