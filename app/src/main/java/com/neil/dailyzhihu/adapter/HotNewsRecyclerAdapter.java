package com.neil.dailyzhihu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.bean.cleanlayer.SimpleStory;
import com.neil.dailyzhihu.utils.LoaderFactory;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：Neil on 2016/7/25 14:52.
 * 邮箱：cn.neillee@gmail.com
 */

public class HotNewsRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {
    List<? extends SimpleStory> datas;
    private Context context;

    public HotNewsRecyclerAdapter(List<? extends SimpleStory> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_rv_hot_news, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTvTitle.setText(datas.get(position).getTitle());
        LoaderFactory.getImageLoader().displayImage(holder.mIvImage, datas.get(position).getImageUrl(), null);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


}

class MyViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.iv_image)
    ImageView mIvImage;
    @Bind(R.id.tv_title)
    TextView mTvTitle;

    MyViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}

