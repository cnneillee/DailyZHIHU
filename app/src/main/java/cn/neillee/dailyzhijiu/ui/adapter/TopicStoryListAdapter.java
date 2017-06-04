package cn.neillee.dailyzhijiu.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.neil.dailyzhijiu.R;
import cn.neillee.dailyzhijiu.model.bean.orignal.TopicStoryListBean;
import cn.neillee.dailyzhijiu.utils.load.LoaderFactory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：Neil on 2017/2/16 23:00.
 * 邮箱：cn.neillee@gmail.com
 */

public class TopicStoryListAdapter extends BaseAdapter {

    private List<TopicStoryListBean.TopicStory> mThemeStoryList;
    private Context mContext;
    private String defaultImgUrl;

    public TopicStoryListAdapter(Context context, List<TopicStoryListBean.TopicStory> topicStoryList) {
        this.mThemeStoryList = topicStoryList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mThemeStoryList != null ? mThemeStoryList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mThemeStoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mThemeStoryList == null) return null;
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_story_universal, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TopicStoryListBean.TopicStory themeStory = mThemeStoryList.get(position);
        viewHolder.tvTitle.setText(themeStory.getTitle());
        String imgUrl = themeStory.getImage();
        if (!TextUtils.isEmpty(imgUrl)) {
            LoaderFactory.getImageLoader().displayImage(viewHolder.ivImg, imgUrl, null);
        } else {
            viewHolder.ivImg.setVisibility(View.GONE);
        }
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
