package com.neil.dailyzhihu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.bean.StoryExtra;
import com.neil.dailyzhihu.bean.UniversalStoryBean;
import com.neil.dailyzhihu.utils.Formater;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.LoaderFactory;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Neil on 2016/4/16.
 */
public class UniversalStoryListAdapter<T extends UniversalStoryBean> extends BaseAdapter {
    private List<T> mDatas;
    private Context mContext;

    public UniversalStoryListAdapter(List<T> datas, Context context) {
        this.mDatas = datas;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (mDatas == null) {
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
        if (mDatas == null)
            return null;
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_story_universal, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        cleanViewHolder(viewHolder);
        fillConvertViewContent(viewHolder, position);
        return convertView;
    }

    private void cleanViewHolder(ViewHolder viewHolder) {
        String initExtra = Formater.formatStoryExtra(null);
        viewHolder.tvExtra.setText(initExtra);
        viewHolder.tvTitle.setText("");
        viewHolder.ivImg.setImageResource(R.mipmap.img_default);
    }

    private void fillConvertViewContent(ViewHolder viewHolder, int position) {
        viewHolder.tvTitle.setText(mDatas.get(position).getTitle());
        if (mDatas.get(position).getImages() == null)
            return;
        LoaderFactory.getImageLoader().displayImage(viewHolder.ivImg, mDatas.get(position).getImages().get(0), null);
        loadExtra(viewHolder, position);
    }

    private void loadExtra(ViewHolder viewHolder, int position) {
        String extraUrl = Formater.formatUrl(Constant.EXTRA_HEAD, mDatas.get(position).getStoryId());
        final TextView tvExtra = viewHolder.tvExtra;
        LoaderFactory.getContentLoader().loadContent(extraUrl, new OnContentLoadingFinishedListener() {
            @Override
            public void onFinish(String content) {
                StoryExtra extra = (StoryExtra) GsonDecoder.getDecoder().decoding(content, StoryExtra.class);
                tvExtra.setText(Formater.formatStoryExtra(extra));
            }
        });
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
