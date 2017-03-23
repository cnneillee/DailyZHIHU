package com.neil.dailyzhihu.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.bean.orignal.HotStoryListBean;
import com.neil.dailyzhihu.utils.load.LoaderFactory;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：Neil on 2017/2/16 21:51.
 * 邮箱：cn.neillee@gmail.com
 */

public class HotStoryListBaseAdapter extends BaseAdapter {
    private static final String LOG_TAG = HotStoryListBaseAdapter.class.getSimpleName();
    private List<HotStoryListBean.HotStory> mHotStoryList;
    private Context mContext;

    public HotStoryListBaseAdapter(Context context, HotStoryListBean hotStoryListBean) {
        if (hotStoryListBean != null) this.mHotStoryList = hotStoryListBean.getStories();
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mHotStoryList != null ? mHotStoryList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mHotStoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_story_universal, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HotStoryListBean.HotStory recentStory = mHotStoryList.get(position);
        viewHolder.tvTitle.setText(recentStory.getTitle());
        Log.e(LOG_TAG, "设置title");
        LoaderFactory.getImageLoader().displayImage(viewHolder.ivImg, recentStory.getImage(), null);

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
