package com.neil.dailyzhihu.adapter;

import android.content.Context;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.bean.CleanDataHelper;
import com.neil.dailyzhihu.bean.StoryExtra;
import com.neil.dailyzhihu.bean.cleanlayer.CleanStoryExtra;
import com.neil.dailyzhihu.bean.cleanlayer.SimpleStory;
import com.neil.dailyzhihu.bean.listener.OnStoryExtraLoadingListener;
import com.neil.dailyzhihu.bean.listener.SimpleOnStoryExtraLoadingListener;
import com.neil.dailyzhihu.utils.Formater;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.LoaderFactory;
import com.neil.dailyzhihu.utils.cnt.UniversalContentLoaderTest;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UniversalStoryListAdapter extends BaseAdapter {
    private List<? extends SimpleStory> mDatas;
    private Context mContext;

    private LruCache<String, CleanStoryExtra> mStoryExtraLruCache = new LruCache<>(30);

    public UniversalStoryListAdapter(List<? extends SimpleStory> datas, Context context) {
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
        String initExtra = "热度：-，评论：-L + -S";
        viewHolder.tvExtra.setText(initExtra);
        viewHolder.tvTitle.setText("");
        viewHolder.ivImg.setImageResource(R.mipmap.img_default);
    }

    private void fillConvertViewContent(ViewHolder viewHolder, int position) {
        viewHolder.tvTitle.setText(mDatas.get(position).getTitle());
        if (mDatas.get(position).getImageUrl() == null)
            return;
        LoaderFactory.getImageLoader().displayImage(viewHolder.ivImg, mDatas.get(position).getImageUrl(), null);
        loadExtra(viewHolder, position);
    }

    private void loadExtra(ViewHolder viewHolder, int position) {
        final String extraUrl = Formater.formatUrl(Constant.EXTRA_HEAD, mDatas.get(position).getStoryId());
        final TextView tvExtra = viewHolder.tvExtra;
        if (getCachedStoryExtra(extraUrl) != null) {
            CleanStoryExtra extra = getCachedStoryExtra(extraUrl);
            tvExtra.setText(Formater.formatStoryExtra(extra));
            return;
        }
        UniversalContentLoaderTest.extraLoad(mDatas.get(position).getStoryId(), mContext, new SimpleOnStoryExtraLoadingListener() {
            @Override
            public void onFinish(CleanStoryExtra storyExtra) {
                mStoryExtraLruCache.put(extraUrl, storyExtra);
                tvExtra.setText(Formater.formatStoryExtra(storyExtra));
            }
        });

        LoaderFactory.getContentLoader().loadContent(extraUrl, new OnContentLoadingFinishedListener() {
            @Override
            public void onFinish(String content) {
                StoryExtra extra = GsonDecoder.getDecoder().decoding(content, StoryExtra.class);
                mStoryExtraLruCache.put(extraUrl, CleanDataHelper.convertStoryExtra2CleanStoryExtra(extra));
                tvExtra.setText(Formater.formatStoryExtra(extra));
            }
        });
    }

    CleanStoryExtra getCachedStoryExtra(String extraUrl) {
        return mStoryExtraLruCache.get(extraUrl);
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
