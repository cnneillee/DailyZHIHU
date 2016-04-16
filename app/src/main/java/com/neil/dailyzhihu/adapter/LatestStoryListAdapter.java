package com.neil.dailyzhihu.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.bean.StoryBean;
import com.neil.dailyzhihu.bean.StoryExtra;
import com.neil.dailyzhihu.utils.LoaderFactory;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Neil on 2016/3/23.
 */
public class LatestStoryListAdapter<T extends StoryBean> extends BaseAdapter {
    private List<T> mDatas;
    private Context mContext;

    public LatestStoryListAdapter(List<T> datas, Context context) {
        this.mDatas = datas;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (mDatas==null){
            return 0;
        }
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_story_universal, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tvTitle.setText(mDatas.get(position).getTitle());
        LoaderFactory.getImageLoader().displayImage(vh.ivImg, mDatas.get(position).getImages().get(0), null);
        String extraUrl = Constant.EXTRA_HEAD + mDatas.get(position).getId();
        final TextView tvExtra = vh.tvExtra;
        LoaderFactory.getContentLoader().loadContent(extraUrl, new OnContentLoadingFinishedListener() {
            @Override
            public void onFinish(String content) {
                Gson gson = new Gson();
                StoryExtra extra = gson.fromJson(content, StoryExtra.class);
                tvExtra.setText(formatExtra(extra));
            }

            private String formatExtra(StoryExtra extra) {
                return String.format("热度：%d，评论：%dL + %dS", extra.getPopularity(),
                        extra.getLong_comments(), extra.getShort_comments());
            }
        });
        return convertView;
    }


    class ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_extra)
        TextView tvExtra;
        @Bind(R.id.iv_img)
        ImageView ivImg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
