package com.neil.dailyzhihu.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.neil.dailyzhihu.MyApplication;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.bean.SectionStoriesList;
import com.neil.dailyzhihu.ui.SectionActivity;
import com.neil.dailyzhihu.utils.ImageLoader;
import com.neil.dailyzhihu.utils.UniversalLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Neil on 2016/3/24.
 */
public class SectionStoriesAdapter extends BaseAdapter {
    private Context mContext;
    private List<SectionStoriesList.StoriesBean> mDatas;

    public SectionStoriesAdapter(Context mContext, List<SectionStoriesList.StoriesBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).
                    inflate(R.layout.item_lv_section_stories, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tvTitle.setText(mDatas.get(position).getTitle());

        //TODO 图片加载
        MyApplication myApplication = (MyApplication) mContext.getApplicationContext();
        UniversalLoader loader = myApplication.getUniversalLoader();
        loader.loadImage(mContext, vh.ivImg, mDatas.get(position).getImages().get(0), null);

        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.iv_img)
        ImageView ivImg;
        @Bind(R.id.cv_news)
        CardView cvNews;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}