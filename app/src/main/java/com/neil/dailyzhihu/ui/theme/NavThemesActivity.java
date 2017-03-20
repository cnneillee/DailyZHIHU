package com.neil.dailyzhihu.ui.theme;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.github.ksoichiro.android.observablescrollview.ObservableGridView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.neil.dailyzhihu.api.API;
import com.neil.dailyzhihu.listener.OnContentLoadedListener;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.ThemeGridAdapter;
import com.neil.dailyzhihu.bean.orignallayer.ThemeList;
import com.neil.dailyzhihu.api.AtyExtraKeyConstant;
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
public class NavThemesActivity extends NightModeBaseActivity implements AdapterView.OnItemClickListener, ObservableScrollViewCallbacks {
    @Bind(R.id.gv_themes)
    ObservableGridView gvThemes;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private List<ThemeList.OthersBean> mDatas;
    private View.OnClickListener upBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            NavThemesActivity.this.finish();
        }
    };

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_themes);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
        mToolbar.setNavigationOnClickListener(upBtnListener);
        mToolbar.setNavigationOnClickListener(upBtnListener);

        gvThemes.setOnItemClickListener(this);
        gvThemes.setScrollViewCallbacks(this);
        LoaderFactory.getContentLoader().loadContent(API.THEMES,
                new OnContentLoadedListener() {
                    @Override
                    public void onSuccess(String content, String url) {
                        Logger.json(content);
                        ThemeList themes = GsonDecoder.getDecoder().decoding(content, ThemeList.class);
                        ThemeGridAdapter adapter = new ThemeGridAdapter(NavThemesActivity.this, themes);
                        mDatas = themes.getOthers();
                        gvThemes.setAdapter(adapter);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /**
     * 列表项子项的点击事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ThemeList.OthersBean bean = mDatas.get(position);
        int sectionId = bean.getStoryId();
        Intent intent = new Intent(this, CertainThemeActivity.class);
        intent.putExtra(AtyExtraKeyConstant.THEME_ID, sectionId);
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
}
