package cn.neillee.dailyzhijiu.ui.topic;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.google.gson.Gson;
import cn.neillee.dailyzhijiu.R;
import cn.neillee.dailyzhijiu.base.BaseActivity;
import cn.neillee.dailyzhijiu.model.bean.orignal.TopicStoryListBean;
import cn.neillee.dailyzhijiu.model.http.api.AtyExtraKeyConstant;
import cn.neillee.dailyzhijiu.presenter.TopicDetailPresenter;
import cn.neillee.dailyzhijiu.presenter.constract.TopicDetailContract;
import cn.neillee.dailyzhijiu.ui.adapter.TopicStoryListAdapter;
import cn.neillee.dailyzhijiu.ui.story.StoryDetailActivity;
import cn.neillee.dailyzhijiu.utils.DisplayUtil;
import cn.neillee.dailyzhijiu.utils.load.LoaderFactory;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者：Neil on 2016/3/23 11:05.
 * 邮箱：cn.neillee@gmail.com
 */
public class TopicDetailActivity extends BaseActivity<TopicDetailPresenter> implements ObservableScrollViewCallbacks,
        AdapterView.OnItemClickListener, TopicDetailContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbarView;
    // mListBackgroundView makes ListView's background except header view.
    @BindView(R.id.list_background)
    View mListBackgroundView;
    @BindView(R.id.lv_theme_story_list)
    ObservableListView mListView;
    @BindView(R.id.image)
    ImageView mImageView;
    @BindView(R.id.tv_summary)
    TextView tvIntro;

    private int mParallaxImageHeight;
    private LinearLayout mLLEditors;
    private List<TopicStoryListBean.EditorsBean> mEditors;

    private int mThemeId = -1;
    private String mDefaultImgUrl;
    private String mTopicName;

    private TopicStoryListAdapter mTopicStoryListAdapter;
    private List<TopicStoryListBean.TopicStory> mThemeStoryList;

    @Override
    protected void initEventAndData() {
        // 对ActionBar进行设置
        setToolbar(mToolbarView, getResources().getString(R.string.activity_topic));

        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);

        mListView.setScrollViewCallbacks(this);
        // Set padding view for ListView. This is the flexible space.
        View paddingView = new View(this);
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                mParallaxImageHeight);
        paddingView.setLayoutParams(lp);

        // This is required to disable header's list selector effect
        paddingView.setClickable(true);

        mListView.addHeaderView(paddingView);

        // 设置HeaderView
        View introHeaderView = getLayoutInflater().inflate(R.layout.header_lv_theme_acty, null);
        mLLEditors = (LinearLayout) introHeaderView.findViewById(R.id.ll_editors);
        mListView.addHeaderView(introHeaderView, null, false);

        introHeaderView.setOnClickListener(editorsListener);

        mListView.setOnItemClickListener(this);

        mThemeStoryList = new ArrayList<>();
        mTopicStoryListAdapter = new TopicStoryListAdapter(mContext, mThemeStoryList);
        mListView.setAdapter(mTopicStoryListAdapter);

        // 获取ThemeId，填充内容
        if (getIntent().getExtras() != null) {
            mThemeId = getIntent().getIntExtra(AtyExtraKeyConstant.THEME_ID, -2);
            mDefaultImgUrl = getIntent().getStringExtra(AtyExtraKeyConstant.DEFAULT_IMG_URL);
        }
        if (mThemeId > 0) {
            mPresenter.getTopicDetailData(mThemeId);
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_topic_detail;
    }

    @Override
    public void showContent(TopicStoryListBean topicStoryListBean) {
        List<TopicStoryListBean.TopicStory> topicStoryList = topicStoryListBean.getStories();

        mThemeStoryList.clear();

        for (int i = 0; i < topicStoryList.size(); i++) {
            mThemeStoryList.add(topicStoryList.get(i));
        }
        mTopicStoryListAdapter.notifyDataSetChanged();

        String bgUrl = topicStoryListBean.getBackground();
        LoaderFactory.getImageLoader().displayImage(mImageView, bgUrl, null);
        String introDes = topicStoryListBean.getDescription();
        mTopicName = topicStoryListBean.getName();
        tvIntro.setText(introDes);
        setActionBarText(mTopicName);
        mEditors = topicStoryListBean.getEditors();
        for (int i = 0; i < mEditors.size(); i++) {
            TopicStoryListBean.EditorsBean bean = mEditors.get(i);
            CircleImageView imageView = new CircleImageView(TopicDetailActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DisplayUtil.dip2px(mContext, 30), DisplayUtil.dip2px(mContext, 30));
            imageView.setImageResource(R.drawable.ic_editor_profile_avatar);
            mLLEditors.addView(imageView, params);
            LoaderFactory.getImageLoader().displayImage(imageView, bean.getAvatar(), null);
        }
    }

    @Override
    public void showError(String errMsg) {

    }

    private View.OnClickListener editorsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mEditors == null) return;
            Gson gson = new Gson();
            String editorsInfo = gson.toJson(mEditors);
            Intent intent = new Intent(TopicDetailActivity.this, TopicEditorsActivity.class);
            intent.putExtra(AtyExtraKeyConstant.EDITORS_LIST, editorsInfo);
            intent.putExtra(AtyExtraKeyConstant.TOPIC_NAME, mTopicName);
            startActivity(intent);
        }
    };

    /*设置标题*/
    private void setActionBarText(String themeName) {
        ActionBar ab = getSupportActionBar();
        if (ab == null || mThemeId < 0) return;
        ab.setTitle(getResources().getString(R.string.activity_topic) + " · " + themeName);
    }

    /*某一主题日报中的某个新闻项被点击*/
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TopicStoryListBean.TopicStory bean = (TopicStoryListBean.TopicStory) parent.getAdapter().getItem(position);
        int storyId = bean.getStoryId();
        mDefaultImgUrl = (bean.getImage() == null) ? mDefaultImgUrl : bean.getImage();
        Intent intent = new Intent(this, StoryDetailActivity.class);
        intent.putExtra(AtyExtraKeyConstant.STORY_ID, storyId);
        intent.putExtra(AtyExtraKeyConstant.DEFAULT_IMG_URL, mDefaultImgUrl);
        startActivity(intent);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        TypedValue typedValue = new TypedValue();
        mContext.getTheme().resolveAttribute(R.attr.barBgColor, typedValue, true);
        int baseColor = typedValue.data;
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
}
