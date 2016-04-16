package com.neil.dailyzhihu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.bean.UniversalBlockBean;
import com.neil.dailyzhihu.ui.aty.ThemeActivity;
import com.neil.dailyzhihu.utils.LoaderFactory;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Neil on 2016/4/17.
 */
public class UniversalBlockGridView<T extends UniversalBlockBean> extends BaseAdapter {
    private Context mContext;
    private List<T> mDatas;

    public UniversalBlockGridView(Context context, List<T> datas) {
        this.mContext = context;
        this.mDatas = datas;
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
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gv_universal_block, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.ivTitle.setText(mDatas.get(position).getTitle());
        vh.ivDescribsion.setText(mDatas.get(position).getDescription());
        LoaderFactory.getImageLoader().displayImage(vh.ivImg, mDatas.get(position).getImages().get(0), null);
        vh.ivImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int themeId = mDatas.get(position).getStoryId();
                Intent intent = new Intent(mContext, ThemeActivity.class);
                intent.putExtra(Constant.THEME_ID, themeId);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
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
