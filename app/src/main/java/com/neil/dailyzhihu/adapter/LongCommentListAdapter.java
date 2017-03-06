package com.neil.dailyzhihu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.bean.orignallayer.LongComment;
import com.neil.dailyzhihu.utils.Formater;
import com.neil.dailyzhihu.utils.load.LoaderFactory;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Neil on 2016/3/23.
 */
public class LongCommentListAdapter extends BaseAdapter {
    private Context context;
    private List<LongComment.CommentsBean> mDatas;

    public LongCommentListAdapter(Context context, List<LongComment.CommentsBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas != null ? mDatas.size():0;
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
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.item_long_comment, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        LoaderFactory.getImageLoader().displayImage(vh.ivAvatar, mDatas.get(position).getAvatar(), null);
        vh.tvAuthor.setText(mDatas.get(position).getAuthor());
        vh.tvContent.setText(mDatas.get(position).getContent());
        //vh.tvId.setText(mDatas.get(position).getSectionId() + "");
        vh.tvLikes.setText(mDatas.get(position).getLikes() + " 个赞");
        long timestamp = mDatas.get(position).getTime();
        String result = Formater.formatData("yyyy/MM/dd HH:mm", timestamp);
//        String result = (String) DateUtils.getRelativeTimeSpanString(context, timestamp);
        vh.tvTime.setText(result);
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.iv_avatar)
        ImageView ivAvatar;
        @Bind(R.id.tv_author)
        TextView tvAuthor;
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_likes)
        TextView tvLikes;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
