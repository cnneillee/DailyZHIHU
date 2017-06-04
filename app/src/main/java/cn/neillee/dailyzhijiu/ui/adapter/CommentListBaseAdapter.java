package cn.neillee.dailyzhijiu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neil.dailyzhijiu.R;
import cn.neillee.dailyzhijiu.model.bean.orignal.CommentListBean;
import cn.neillee.dailyzhijiu.utils.Formater;
import cn.neillee.dailyzhijiu.utils.load.LoaderFactory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：Neil on 2016/3/23 03:50.
 * 邮箱：cn.neillee@gmail.com
 */
public class CommentListBaseAdapter extends BaseAdapter {
    private Context context;
    private List<CommentListBean.CommentsBean> mDatas;

    public CommentListBaseAdapter(Context context, List<CommentListBean.CommentsBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_comment, parent, false);
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
        CommentListBean.CommentsBean.ReplyToBean replyToBean = mDatas.get(position).getReplyTo();
        if (replyToBean != null) {
            viewHolder.llReplyTo.setVisibility(View.VISIBLE);
            viewHolder.tvAuthorReplyTo.setText(replyToBean.getAuthor());
            int status = replyToBean.getStatus();
            if (status == 0) {// 正常
                viewHolder.tvContentReplyTo.setText(replyToBean.getContent());
                viewHolder.tvStatusReplyTo.setVisibility(View.GONE);
            } else {
                viewHolder.tvStatusReplyTo.setVisibility(View.VISIBLE);
                viewHolder.tvStatusReplyTo.setText(R.string.comment_state_deleted);
                viewHolder.tvContentReplyTo.setText(replyToBean.getErrMsg());
            }
        } else {
            viewHolder.llReplyTo.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.iv_avatar)
        ImageView ivAvatar;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_likes)
        TextView tvLikes;
        @BindView(R.id.ll_reply_to)
        LinearLayout llReplyTo;
        @BindView(R.id.tv_status)
        TextView tvStatusReplyTo;
        @BindView(R.id.tv_author_reply_to)
        TextView tvAuthorReplyTo;
        @BindView(R.id.tv_content_reply_to)
        TextView tvContentReplyTo;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
