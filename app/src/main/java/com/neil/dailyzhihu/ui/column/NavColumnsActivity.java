package com.neil.dailyzhihu.ui.column;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableGridView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.neil.dailyzhihu.adapter.ColumnGridBaseAdapter;
import com.neil.dailyzhihu.mvp.model.http.api.API;
import com.neil.dailyzhihu.mvp.model.bean.orignal.ColumnListBean;
import com.neil.dailyzhihu.listener.OnContentLoadedListener;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.mvp.presenter.BlockGridPresenter;
import com.neil.dailyzhihu.mvp.presenter.constract.BlockGridContract;
import com.neil.dailyzhihu.ui.widget.BaseActivity;
import com.neil.dailyzhihu.mvp.model.http.api.AtyExtraKeyConstant;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.load.LoaderFactory;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：Neil on 2017/1/19 18:18.
 * 邮箱：cn.neillee@gmail.com
 */

public class NavColumnsActivity extends BaseActivity implements ObservableScrollViewCallbacks,
        AdapterView.OnItemClickListener, View.OnClickListener, BlockGridContract.View {

    private static final String LOG_TAG = NavColumnsActivity.class.getSimpleName();

    @Bind(R.id.gv_sections)
    ObservableGridView gvSections;
    @Bind(R.id.tv_header)
    TextView tvHeader;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private List<ColumnListBean.ColumnBean> mDatas;
    private View.OnClickListener upBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            NavColumnsActivity.this.finish();
        }
    };

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_columns);
        ButterKnife.bind(this);

        // 将ToolBar设置为ActionBar
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
        mToolbar.setNavigationOnClickListener(upBtnListener);
//        getSupportActionBar().setNavigationMode();
//        View header = LayoutInflater.from(mContext).inflate(R.layout.section_listview_header, null, false);
//        header.setOnClickListener(this);
//        gvSections.addHeaderView(header);
//        gvSections.setScrollViewCallbacks(this);
        gvSections.setOnItemClickListener(this);
        tvHeader.setOnClickListener(this);
//        Utility.setGridViewHeightBasedOnChildren(gvSections);
        BlockGridPresenter presenter = new BlockGridPresenter(this);
        presenter.getBlockData(API.SECTIONS);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
    }

    @Override
    public void onDownMotionEvent() {
    }

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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ColumnListBean.ColumnBean bean = mDatas.get((int) id);
        int sectionId = bean.getStoryId();
        String sectionName = bean.getTitle();
        String sectionImg = bean.getImages() == null ? null : bean.getImages().get(0);
        Intent intent = new Intent(this, CertainColumnActivity.class);
        intent.putExtra(AtyExtraKeyConstant.SECTION_ID, sectionId);
        intent.putExtra(AtyExtraKeyConstant.SECTION_NAME, sectionName);
        intent.putExtra(AtyExtraKeyConstant.DEFAULT_IMG_URL, sectionImg);
        startActivity(intent);
    }

    // header被点击
    @Override
    public void onClick(View v) {
//        Intent intent = new Intent(this, SectionSettingActivity.class);
//        this.startActivity(intent);
        Toast.makeText(this, getResources().getString(R.string.notify_columns_subscribing_clicked), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(BlockGridContract.Presenter presenter) {

    }

    @Override
    public void showContent(String content) {
        Logger.json(content);
        ColumnListBean columnListBean = GsonDecoder.getDecoder().decoding(content, ColumnListBean.class);
        mDatas = columnListBean.getData();
        ColumnGridBaseAdapter adapter = new ColumnGridBaseAdapter(NavColumnsActivity.this, columnListBean);
        gvSections.setAdapter(adapter);
    }
}
