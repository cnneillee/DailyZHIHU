package com.neil.dailyzhihu.ui.aty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.EditorListAdapter;
import com.neil.dailyzhihu.adapter.UniversalStoryListAdapter;
import com.neil.dailyzhihu.bean.CleanDataHelper;
import com.neil.dailyzhihu.bean.cleanlayer.CleanThemeStoryListBean;
import com.neil.dailyzhihu.bean.cleanlayer.SimpleStory;
import com.neil.dailyzhihu.bean.orignallayer.ThemeStoryList;
import com.neil.dailyzhihu.ui.widget.BaseActivity;
import com.neil.dailyzhihu.utils.Formater;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.LoaderFactory;
import com.nineoldandroids.view.ViewHelper;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Neil on 2016/3/23.
 */
public class ThemeActivity extends BaseActivity implements ObservableScrollViewCallbacks, AdapterView.OnItemClickListener, View.OnClickListener {
    @Bind(R.id.image)
    ImageView mImageView;
    // mListBackgroundView makes ListView's background except header view.
    @Bind(R.id.list_background)
    View mListBackgroundView;
    @Bind(R.id.list)
    ObservableListView mListView;
    @Bind(R.id.toolbar)
    Toolbar mToolbarView;

    private int mParallaxImageHeight;

    private int themeId = -1;
    private TextView tvIntro;
    private ListView lvEditor;
    private TextView tvLoadingEditor;
    private LinearLayout lleditorLVWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_pre);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbarView);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.primary)));

        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);

        mListView = (ObservableListView) findViewById(R.id.list);
        mListView.setScrollViewCallbacks(this);
        // Set padding view for ListView. This is the flexible space.
        View paddingView = new View(this);
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                mParallaxImageHeight);
        paddingView.setLayoutParams(lp);

        // This is required to disable header's list selector effect
        paddingView.setClickable(true);

        View introHeaderview = getLayoutInflater().inflate(R.layout.lv_header_theme_acty, null);
        tvIntro = (TextView) introHeaderview.findViewById(R.id.tv_intro);
        mListView.addHeaderView(paddingView);
        mListView.addHeaderView(introHeaderview, null, false);
        setDummyData(mListView);

        View editorFooter = getLayoutInflater().inflate(R.layout.lv_footer_theme_acty, null);
        lvEditor = (ListView) editorFooter.findViewById(R.id.lv_editor);
        lleditorLVWrapper = (LinearLayout) editorFooter.findViewById(R.id.ll_editorLVWrapper);
        tvLoadingEditor = (TextView) editorFooter.findViewById(R.id.tv_loading_editor);
        tvLoadingEditor.setOnClickListener(this);
        mListView.addFooterView(editorFooter, null, false);
        lleditorLVWrapper.setVisibility(View.GONE);

        getExtras();
        if (themeId > 0)
            fillContent();
        mListView.setOnItemClickListener(this);
    }

    private int getExtras() {
        if (getIntent().getExtras() != null) {
            themeId = getIntent().getIntExtra(Constant.THEME_ID, -2);
        }
        return themeId;
    }

    private void fillContent() {
        LoaderFactory.getContentLoader().loadContent(Formater.formatUrl(Constant.THEME_HEAD, themeId), new OnContentLoadingFinishedListener() {
            @Override
            public void onFinish(String content) {
                ThemeStoryList themeStoryList = (ThemeStoryList) GsonDecoder.getDecoder().decoding(content, ThemeStoryList.class);
                CleanThemeStoryListBean cleanThemeStoryListBean = CleanDataHelper.cleanThemeStoryList(themeStoryList);
                String themeBGImgUrl = cleanThemeStoryListBean.getBackground();
                String introDes = cleanThemeStoryListBean.getDescription();
                String actionbarTitle = cleanThemeStoryListBean.getName();
                tvIntro.setText(introDes);
                setActionBarText(actionbarTitle);
                LoaderFactory.getImageLoader().displayImage(mImageView, themeBGImgUrl, null);
                mListView.setAdapter(new UniversalStoryListAdapter(cleanThemeStoryListBean.getSimpleStoryList(), ThemeActivity.this));
                List<ThemeStoryList.EditorsBean> editorsBeanList = themeStoryList.getEditors();
                lvEditor.setAdapter(new EditorListAdapter(ThemeActivity.this, editorsBeanList));
            }
        });
    }

    private void setActionBarText(String themeName) {
        ActionBar ab = getSupportActionBar();
        if (ab == null || themeId < 0)
            return;
        ab.setTitle("日报·主题" + "\t" + themeName);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SimpleStory bean = (SimpleStory) parent.getAdapter().getItem(position);
        int storyId = bean.getStoryId();
        Intent intent = new Intent(this, StoryActivity.class);
        intent.putExtra(Constant.STORY_ID, storyId);
        startActivity(intent);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(mListView.getCurrentScrollY(), false, false);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.primary);
        float alpha = Math.min(1, (float) scrollY / mParallaxImageHeight);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        ViewHelper.setTranslationY(mImageView, -scrollY / 2);

        // Translate list background
        ViewHelper.setTranslationY(mListBackgroundView, Math.max(0, -scrollY + mParallaxImageHeight));
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    @Override
    public void onClick(View v) {
        lleditorLVWrapper.setVisibility(View.VISIBLE);
        tvLoadingEditor.setText("当当当当！下面是我们哒编辑~~~");
    }
}
