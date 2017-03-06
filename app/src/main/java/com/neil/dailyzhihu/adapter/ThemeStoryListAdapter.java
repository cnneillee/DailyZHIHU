package com.neil.dailyzhihu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.bean.orignallayer.ThemeStoryList;
import com.neil.dailyzhihu.utils.load.LoaderFactory;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：Neil on 2017/2/16 23:00.
 * 邮箱：cn.neillee@gmail.com
 */

public class ThemeStoryListAdapter extends BaseAdapter {

    private List<ThemeStoryList.StoriesBean> mThemeStoryList;
    private Context mContext;

    public ThemeStoryListAdapter(Context context, ThemeStoryList themeStoryList) {
        this.mThemeStoryList = themeStoryList.getStories();
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mThemeStoryList != null ? mThemeStoryList.size():0;
    }

    @Override
    public Object getItem(int position) {
        return mThemeStoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mThemeStoryList == null) return null;
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_story_universal, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ThemeStoryList.StoriesBean themeStory = mThemeStoryList.get(position);
        viewHolder.tvTitle.setText(themeStory.getTitle());
        if (themeStory.getImages() != null) {
            String imageUrl = themeStory.getImages().get(0);
            LoaderFactory.getImageLoader().displayImage(viewHolder.ivImg, imageUrl, null);
        }

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
