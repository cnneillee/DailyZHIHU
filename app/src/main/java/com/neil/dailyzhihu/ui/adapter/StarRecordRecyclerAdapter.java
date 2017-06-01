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
import com.neil.dailyzhihu.model.db.StarRecord;
import com.neil.dailyzhihu.utils.load.LoaderFactory;

import java.util.List;

/**
 * 作者：Neil on 2017/6/1 15:43.
 * 邮箱：cn.neillee@gmail.com
 */

public class StarRecordRecyclerAdapter extends BaseReecyclerViewAdapter<StarRecordRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private List<StarRecord> mData;

    public StarRecordRecyclerAdapter(Context context, List<StarRecord> data) {
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
        StarRecord record = mData.get(position);
        holder.mTvTitle.setText(record.getTitle());
        LoaderFactory.getImageLoader().displayImage(holder.mIvImg, record.getImage(), null);
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
