package cn.neillee.dailyzhijiu.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 作者：Neil on 2017/6/1 16:51.
 * 邮箱：cn.neillee@gmail.com
 */

public abstract class BaseReecyclerViewAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {
    protected OnItemClickListener<VH> mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<VH> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener<VH> {
        void onItemClick(VH holder, int position, long id);
    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder, position, holder.getItemId());
                }
            });
            this.onBindViewHolder(holder, position, mOnItemClickListener != null);
        }
    }

    public abstract void onBindViewHolder(VH holder, int position, boolean bindItemListener);
}
