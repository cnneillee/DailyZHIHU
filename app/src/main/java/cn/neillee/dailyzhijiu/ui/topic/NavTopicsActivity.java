package cn.neillee.dailyzhijiu.ui.topic;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import cn.neillee.dailyzhijiu.R;
import cn.neillee.dailyzhijiu.base.BaseActivity;
import cn.neillee.dailyzhijiu.model.bean.orignal.OriginalStory;
import cn.neillee.dailyzhijiu.model.bean.orignal.TopicListBean;
import cn.neillee.dailyzhijiu.model.http.api.AtyExtraKeyConstant;
import cn.neillee.dailyzhijiu.presenter.BlockGridPresenter;
import cn.neillee.dailyzhijiu.presenter.constract.BlockGridContract;
import cn.neillee.dailyzhijiu.ui.adapter.BlockBaseAdapter;
import cn.neillee.dailyzhijiu.ui.widget.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：Neil on 2017/1/19 17:53.
 * 邮箱：cn.neillee@gmail.com
 * 主题日报窗口
 */
public class NavTopicsActivity extends BaseActivity<BlockGridPresenter> implements
        BlockGridContract.View, BlockBaseAdapter.OnItemClickListener {
    @BindView(R.id.rv_themes)
    RecyclerView mGidThemes;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private List<TopicListBean.TopicBean> mTopicBeanList;
    private BlockBaseAdapter<TopicListBean.TopicBean> mTopicAdapter;

    @Override
    protected void initEventAndData() {
        setToolbar(mToolbar, getResources().getString(R.string.activity_topics));

//        View header = LayoutInflater.from(mContext).inflate(R.layout.header_gap8dp, null, false);
//        mGidThemes.addHeaderView(header);

        mTopicBeanList = new ArrayList<>();
        mTopicAdapter = new BlockBaseAdapter<>(mContext, mTopicBeanList);
        mGidThemes.setLayoutManager(new GridLayoutManager(mContext, 2));
        mGidThemes.addItemDecoration(new DividerGridItemDecoration(mContext));
        mGidThemes.setAdapter(mTopicAdapter);
        mTopicAdapter.setOnItemClickListener(this);

        mPresenter.getBlockData(BlockGridContract.TOPIC);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_topics;
    }

    @Override
    public void showContent(OriginalStory bean) {
        List<TopicListBean.TopicBean> topicBeanList = ((TopicListBean) bean).getOthers();
        mTopicBeanList.clear();
        for (int i = 0; i < topicBeanList.size(); i++) {
            mTopicBeanList.add(topicBeanList.get(i));
        }
        mTopicAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String errMsg) {

    }

    @Override
    public void OnItemClick(int position, BlockBaseAdapter.ViewHolder shareView) {
        TopicListBean.TopicBean bean = mTopicBeanList.get(position);
        int sectionId = bean.getStoryId();
        String topicImg = bean.getImage();
        Intent intent = new Intent(this, TopicDetailActivity.class);
        intent.putExtra(AtyExtraKeyConstant.THEME_ID, sectionId);
        intent.putExtra(AtyExtraKeyConstant.DEFAULT_IMG_URL, topicImg);
        startActivity(intent);
    }
}
