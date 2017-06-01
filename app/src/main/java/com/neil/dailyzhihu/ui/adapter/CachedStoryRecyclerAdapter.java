package com.neil.dailyzhihu.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.base.BaseReecyclerViewAdapter;
import com.neil.dailyzhihu.model.db.CachedStory;
import com.neil.dailyzhihu.utils.load.LoaderFactory;

import java.util.List;

/**
 * 作者：Neil on 2017/6/1 15:43.
 * 邮箱：cn.neillee@gmail.com
 */

public class CachedStoryRecyclerAdapter extends BaseReecyclerViewAdapter<CachedStoryRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private List<CachedStory> mData;

    public CachedStoryRecyclerAdapter(Context context, List<CachedStory> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_lv_story_universal, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, boolean bindItemListener) {
        CachedStory story = mData.get(position);
        holder.mTvTitle.setText(story.getTitle());
        LoaderFactory.getImageLoader().displayImage(holder.mIvImg, story.getImage(), null);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvTitle;
        ImageView mIvImg;

        public ViewHolder(View itemView) {
            super(itemView);
            mIvImg = (ImageView) itemView.findViewById(R.id.iv_img);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }
}
