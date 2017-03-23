package com.neil.dailyzhihu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.bean.orignal.PastStoryListBean;
import com.neil.dailyzhihu.utils.load.LoaderFactory;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：Neil on 2017/2/16 21:59.
 * 邮箱：cn.neillee@gmail.com
 */

public class PastStoryListBaseAdapter extends BaseAdapter {

    private List<PastStoryListBean.PastStory> mPastStoryList;
    private Context mContext;

    public PastStoryListBaseAdapter(Context context, PastStoryListBean pastStoryListBean) {
        if (pastStoryListBean != null) this.mPastStoryList = pastStoryListBean.getStories();
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mPastStoryList != null ? mPastStoryList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mPastStoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mPastStoryList == null) return null;
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_story_universal, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PastStoryListBean.PastStory pastStory = mPastStoryList.get(position);
        viewHolder.tvTitle.setText(pastStory.getTitle());
        LoaderFactory.getImageLoader().displayImage(viewHolder.ivImg, pastStory.getImage(), null);

        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.iv_img)
        ImageView ivImg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}