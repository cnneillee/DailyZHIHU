package com.neil.dailyzhihu.ui.column;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.base.BaseActivity;
import com.neil.dailyzhihu.model.bean.orignal.ColumnListBean;
import com.neil.dailyzhihu.model.http.api.API;
import com.neil.dailyzhihu.model.http.api.AtyExtraKeyConstant;
import com.neil.dailyzhihu.presenter.BlockGridPresenter;
import com.neil.dailyzhihu.presenter.constract.BlockGridContract;
import com.neil.dailyzhihu.ui.adapter.BlockBaseAdapter;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：Neil on 2017/1/19 18:18.
 * 邮箱：cn.neillee@gmail.com
 */

public class NavColumnsActivity extends BaseActivity<BlockGridPresenter>
        implements BlockGridContract.View, BlockBaseAdapter.OnItemClickListener {

    private static final String LOG_TAG = NavColumnsActivity.class.getSimpleName();

    @BindView(R.id.rv_sections)
    RecyclerView mGridSections;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private List<ColumnListBean.ColumnBean> mColumnBeanList;
    private BlockBaseAdapter<ColumnListBean.ColumnBean> mColumnGridBaseAdapter;

    @Override
    protected void initEventAndData() {
        setToolbar(mToolbar, getResources().getString(R.string.activity_columns));

//        View header = LayoutInflater.from(mContext).inflate(R.layout.header_gap8dp, null, false);
//        mGridSections.addHeaderView(header);
        mGridSections.setLayoutManager(new GridLayoutManager(mContext, 2));

        mColumnBeanList = new ArrayList<>();
        mColumnGridBaseAdapter = new BlockBaseAdapter<>(this, mColumnBeanList);
        mColumnGridBaseAdapter.setOnItemClickListener(this);
        mGridSections.setAdapter(mColumnGridBaseAdapter);
        
        mPresenter.getBlockData(API.SECTIONS);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_columns;
    }

    @Override
    public void showContent(String content) {
        Logger.json(content);
        ColumnListBean columnListBean = GsonDecoder.getDecoder().decoding(content, ColumnListBean.class);
        List<ColumnListBean.ColumnBean> columnList = columnListBean.getData();

        mColumnBeanList.clear();
        for (int i = 0; i < columnList.size(); i++) {
            mColumnBeanList.add(columnList.get(i));
        }
        mColumnGridBaseAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String errMsg) {

    }

    @Override
    public void OnItemClick(int position, BlockBaseAdapter.ViewHolder shareView) {
        ColumnListBean.ColumnBean bean = mColumnBeanList.get(position);
        int sectionId = bean.getStoryId();
        String sectionName = bean.getTitle();
        String sectionImg = bean.getImages() == null ? null : bean.getImages().get(0);
        Intent intent = new Intent(this, CertainColumnActivity.class);
        intent.putExtra(AtyExtraKeyConstant.SECTION_ID, sectionId);
        intent.putExtra(AtyExtraKeyConstant.SECTION_NAME, sectionName);
        intent.putExtra(AtyExtraKeyConstant.DEFAULT_IMG_URL, sectionImg);
        startActivity(intent);
    }
}
