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
import com.neil.dailyzhihu.utils.LoaderFactory;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Neil on 2016/3/23.
 */
public class EditorListAdapter extends BaseAdapter {
    private Context mContext;
    private List<ThemeStoryList.EditorsBean> mDatas;

    public EditorListAdapter(Context mContext, List<ThemeStoryList.EditorsBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        if (mDatas == null) return -1;
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
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).
                    inflate(R.layout.item_lv_editor_theme, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tvBio.setText(mDatas.get(position).getBio());
        vh.tvName.setText(mDatas.get(position).getName());
        vh.tvUrl.setText(mDatas.get(position).getUrl());
        LoaderFactory.getImageLoader().displayImage(vh.ivAvatar, mDatas.get(position).getAvatar(), null);
//        vh.ivImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int themeId = mDatas.get(position).getId();
//                Intent intent = new Intent(mContext, ThemeActivity.class);
//                intent.getIntExtra("THEME_ID", themeId);
//                mContext.startActivity(intent);
//            }
//        });
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.iv_avatar)
        ImageView ivAvatar;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_bio)
        TextView tvBio;
        @Bind(R.id.tv_url)
        TextView tvUrl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
