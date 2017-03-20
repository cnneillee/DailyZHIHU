package com.neil.dailyzhihu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.bean.orignallayer.LongComment;
import com.neil.dailyzhihu.utils.Formater;
import com.neil.dailyzhihu.utils.load.LoaderFactory;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：Neil on 2016/3/23 03:50.
 * 邮箱：cn.neillee@gmail.com
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
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_long_comment, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LoaderFactory.getImageLoader().displayImage(viewHolder.ivAvatar, mDatas.get(position).getAvatar(), null);
        viewHolder.tvAuthor.setText(mDatas.get(position).getAuthor());
        viewHolder.tvContent.setText(mDatas.get(position).getContent());
        viewHolder.tvLikes.setText(String.valueOf(mDatas.get(position).getLikes()));
        long timestamp = mDatas.get(position).getTime();
        String result = Formater.formatData("yyyy/MM/dd HH:mm", timestamp);
        viewHolder.tvTime.setText(result);
        // replay to
        LongComment.CommentsBean.ReplyToBean replyToBean = mDatas.get(position).getReply_to();
        if(replyToBean!=null){
            viewHolder.llReplyTo.setVisibility(View.VISIBLE);
            viewHolder.tvAuthorReplyTo.setText(replyToBean.getAuthor());
            int status = replyToBean.getStatus();
            if (status==0){// 正常
                viewHolder.tvContentReplyTo.setText(replyToBean.getContent());
                viewHolder.tvStatusReplyTo.setVisibility(View.GONE);
            }else{
                viewHolder.tvStatusReplyTo.setVisibility(View.VISIBLE);
                viewHolder.tvStatusReplyTo.setText("[已删除]");
                viewHolder.tvContentReplyTo.setText(replyToBean.getErr_msg());
            }
        }else {
            viewHolder.llReplyTo.setVisibility(View.GONE);
        }
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
        @Bind(R.id.ll_reply_to)
        LinearLayout llReplyTo;
        @Bind(R.id.tv_status)
        TextView tvStatusReplyTo;
        @Bind(R.id.tv_author_reply_to)
        TextView tvAuthorReplyTo;
        @Bind(R.id.tv_content_reply_to)
        TextView tvContentReplyTo;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
