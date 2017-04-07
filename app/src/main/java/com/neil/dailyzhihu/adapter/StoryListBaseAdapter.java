package com.neil.dailyzhihu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.mvp.model.bean.IListBean;
import com.neil.dailyzhihu.mvp.model.bean.IStoryBean;
import com.neil.dailyzhihu.utils.load.LoaderFactory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：Neil on 2017/3/21 17:01.
 * 邮箱：cn.neillee@gmail.com
 */

public class StoryListBaseAdapter<T extends IListBean> extends BaseAdapter {
    private List<IStoryBean> mItems;
    private String defaultImgUrl;
    private LayoutInflater mInflater;

    public StoryListBaseAdapter(Context context, IListBean listItem) {
        this.mItems = listItem.getStories();
        mInflater = LayoutInflater.from(context);
    }

    public void setDefaultImgUrl(String defaultImgUrl) {
        this.defaultImgUrl = defaultImgUrl;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems != null ? mItems.get(position) : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_lv_story_universal, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        IStoryBean item = mItems.get(position);
        viewHolder.tvTitle.setText(item.getTitle());
        defaultImgUrl = (item.getImage() == null) ? defaultImgUrl : item.getImage();
        LoaderFactory.getImageLoader().displayImage(viewHolder.ivImg, defaultImgUrl, null);

        return convertView;
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
