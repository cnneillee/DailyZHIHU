package cn.neillee.dailyzhijiu.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.neillee.dailyzhijiu.R;
import cn.neillee.dailyzhijiu.model.bean.orignal.LatestStoryListBean;
import cn.neillee.dailyzhijiu.ui.story.StoryDetailActivity;
import cn.neillee.dailyzhijiu.model.http.api.AtyExtraKeyConstant;
import cn.neillee.dailyzhijiu.utils.load.LoaderFactory;

import java.util.List;

/**
 * top viewpager（顶部轮播新闻）
 * 作者：Neil on 2017/2/16 21:35.
 * 邮箱：cn.neillee@gmail.com
 */
public class LatestTopStoryPagerAdapter extends PagerAdapter {
    private List<LatestStoryListBean.TopStoriesBean> mTopStoryList;
    private Context mContext;
    private int currentStoryId;
    private String currentImg;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, StoryDetailActivity.class);
            intent.putExtra(AtyExtraKeyConstant.STORY_ID, currentStoryId);
            intent.putExtra(AtyExtraKeyConstant.DEFAULT_IMG_URL, currentImg);
            mContext.startActivity(intent);
        }
    };

    public LatestTopStoryPagerAdapter(Context context, List<LatestStoryListBean.TopStoriesBean> topStoriesBeanList) {
        this.mTopStoryList = topStoriesBeanList;
        mContext = context;
    }

    @Override
    public int getCount() {
        if (mTopStoryList != null)
            return mTopStoryList.size();
        return 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = getPagerView(mTopStoryList.get(position));
        view.setTag(mTopStoryList.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 获得顶部轮播单页页卡
     *
     * @param topStory 顶部单页内容
     * @return 单页页卡
     */
    private View getPagerView(LatestStoryListBean.TopStoriesBean topStory) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_vp_top_story, null, false);
        ImageView iv = (ImageView) v.findViewById(R.id.iv_img);
        TextView tv = (TextView) v.findViewById(R.id.tv_title);
        tv.setText(topStory.getTitle());
        LoaderFactory.getImageLoader().displayImage(iv, topStory.getImage(), null);
        currentStoryId = topStory.getStoryId();
        currentImg = topStory.getImage();
        v.setOnClickListener(mOnClickListener);
        return v;
    }
}