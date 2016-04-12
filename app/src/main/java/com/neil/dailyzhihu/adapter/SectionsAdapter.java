package com.neil.dailyzhihu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.bean.SectionsList;
import com.neil.dailyzhihu.ui.SectionActivity;
import com.neil.dailyzhihu.utils.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Neil on 2016/3/23.
 */
public class SectionsAdapter extends BaseAdapter {
    private Context mContext;
    private List<SectionsList.DataBean> mDatas;

    public SectionsAdapter(Context mContext, List<SectionsList.DataBean> mDatas) {
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
                    inflate(R.layout.item_gv_themes, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.ivTitle.setText(mDatas.get(position).getName());
        vh.ivDescribsion.setText(mDatas.get(position).getDescription());
        ImageLoader.loadImage(mContext,vh.ivImg, mDatas.get(position).getThumbnail(), null);

        vh.ivImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sectionId = mDatas.get(position).getId();
                Intent intent = new Intent(mContext, SectionActivity.class);
                intent.putExtra("SECTION_ID", sectionId);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.iv_img)
        ImageView ivImg;
        @Bind(R.id.iv_title)
        TextView ivTitle;
        @Bind(R.id.iv_describsion)
        TextView ivDescribsion;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
