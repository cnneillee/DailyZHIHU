package com.neil.dailyzhihu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.model.bean.orignal.ColumnStoryListBean;
import com.neil.dailyzhihu.utils.load.LoaderFactory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：Neil on 2017/2/16 22:12.
 * 邮箱：cn.neillee@gmail.com
 */

public class ColumnStoryListBaseAdapter extends BaseAdapter {

    private List<ColumnStoryListBean.ColumnStory> mColumnStoryList;
    private Context mContext;
    private String defaultImgUrl;

    public ColumnStoryListBaseAdapter(Context context, List<ColumnStoryListBean.ColumnStory> ColumnStoryList) {
        this.mColumnStoryList = ColumnStoryList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mColumnStoryList != null ? mColumnStoryList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mColumnStoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mColumnStoryList == null) return null;
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_story_universal, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ColumnStoryListBean.ColumnStory sectionStory = mColumnStoryList.get(position);
        viewHolder.tvTitle.setText(sectionStory.getTitle());
        String imgUrl = (sectionStory.getImage() == null) ? defaultImgUrl : sectionStory.getImage();
        LoaderFactory.getImageLoader().displayImage(viewHolder.ivImg, imgUrl, null);

        return convertView;
    }

    public String getDefaultImgUrl() {
        return defaultImgUrl;
    }

    public void setDefaultImgUrl(String defaultImgUrl) {
        this.defaultImgUrl = defaultImgUrl;
    }

    class ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.iv_img)
        ImageView ivImg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
