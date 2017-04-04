package com.neil.dailyzhihu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.model.bean.orignal.TopicListBean;
import com.neil.dailyzhihu.utils.load.LoaderFactory;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：Neil on 2017/2/16 22:29.
 * 邮箱：cn.neillee@gmail.com
 */

public class TopicGridBaseAdapter extends BaseAdapter {
    private Context mContext;
    private List<TopicListBean.TopicBean> mDatas;

    public TopicGridBaseAdapter(Context context, TopicListBean topicListBean) {
        if (topicListBean != null) this.mDatas = topicListBean.getOthers();
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mDatas != null ? mDatas.size() : 0;
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
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gv_block, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.ivTitle.setText(mDatas.get(position).getTitle());
        vh.ivDescription.setText(mDatas.get(position).getDescription());
        LoaderFactory.getImageLoader().displayImage(vh.ivImg, mDatas.get(position).getImages().get(0), null);
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.iv_img)
        ImageView ivImg;
        @Bind(R.id.iv_title)
        TextView ivTitle;
        @Bind(R.id.tv_description)
        TextView ivDescription;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
