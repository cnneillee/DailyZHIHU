package com.neil.dailyzhihu.ui.topic;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.github.ksoichiro.android.observablescrollview.ObservableGridView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.neil.dailyzhihu.adapter.TopicGridBaseAdapter;
import com.neil.dailyzhihu.mvp.model.http.api.API;
import com.neil.dailyzhihu.listener.OnContentLoadedListener;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.mvp.model.bean.orignal.TopicListBean;
import com.neil.dailyzhihu.mvp.model.http.api.AtyExtraKeyConstant;
import com.neil.dailyzhihu.mvp.presenter.BlockGridPresenter;
import com.neil.dailyzhihu.mvp.presenter.constract.BlockGridContract;
import com.neil.dailyzhihu.ui.NightModeBaseActivity;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.load.LoaderFactory;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：Neil on 2017/1/19 17:53.
 * 邮箱：cn.neillee@gmail.com
 */

/**
 * 主题日报窗口
 */
public class NavTopicsActivity extends NightModeBaseActivity implements
        AdapterView.OnItemClickListener, ObservableScrollViewCallbacks, BlockGridContract.View {
    @Bind(R.id.gv_themes)
    ObservableGridView gvThemes;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private List<TopicListBean.TopicBean> mDatas;
    private View.OnClickListener upBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            NavTopicsActivity.this.finish();
        }
    };

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_topics);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
        mToolbar.setNavigationOnClickListener(upBtnListener);
        mToolbar.setNavigationOnClickListener(upBtnListener);

        gvThemes.setOnItemClickListener(this);
        gvThemes.setScrollViewCallbacks(this);
        BlockGridPresenter presenter = new BlockGridPresenter(this);
        presenter.getBlockData(API.THEMES);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TopicListBean.TopicBean bean = mDatas.get(position);
        int sectionId = bean.getStoryId();
        String topicImg = bean.getImage();
        Intent intent = new Intent(this, TopicDetailActivity.class);
        intent.putExtra(AtyExtraKeyConstant.THEME_ID, sectionId);
        intent.putExtra(AtyExtraKeyConstant.DEFAULT_IMG_URL, topicImg);
        startActivity(intent);
    }


    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    /**
     * 列表项滑动后对Actionbar施加影响
     *
     * @param scrollState 滑动状态
     */
    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        ActionBar ab = this.getSupportActionBar();
        if (ab == null) {
            return;
        }
        if (scrollState == ScrollState.UP) {
            if (ab.isShowing()) {
                ab.hide();
            }
        } else if (scrollState == ScrollState.DOWN) {
            if (!ab.isShowing()) {
                ab.show();
            }
        }
    }

    @Override
    public void setPresenter(BlockGridContract.Presenter presenter) {

    }

    @Override
    public void showContent(String content) {
        Logger.json(content);
        TopicListBean themes = GsonDecoder.getDecoder().decoding(content, TopicListBean.class);
        TopicGridBaseAdapter adapter = new TopicGridBaseAdapter(NavTopicsActivity.this, themes);
        mDatas = themes.getOthers();
        gvThemes.setAdapter(adapter);
    }
}
