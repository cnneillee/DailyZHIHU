package com.neil.dailyzhihu.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.api.API;
import com.neil.dailyzhihu.bean.orignallayer.LongComment;
import com.neil.dailyzhihu.listener.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.load.LoaderFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Neil on 2016/4/21 22:35.
 * 邮箱：cn.neillee@gmail.com
 */
public class CommentPagerAdapter extends PagerAdapter {
    private List<View> mViews = new ArrayList<>();
    private String[] mTitles = new String[]{"长评论", "短评论"};
    private String[] mCommentTails = new String[]{"/long-comments", "/short-comments"};
    private Context mContext;
    private String mStoryId;

    public CommentPagerAdapter(Context context, String storyId) {
        this.mContext = context;
        this.mStoryId = storyId;
        View view1 = LayoutInflater.from(mContext).inflate(R.layout.vp_item_comment, null, false);
        View view2 = LayoutInflater.from(mContext).inflate(R.layout.vp_item_comment, null, false);
        mViews.add(view1);
        mViews.add(view2);
    }

    //viewpager中的组件数量
    @Override
    public int getCount() {
        return mViews.size();
    }

    // 滑动切换的时候销毁当前的组件
    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        container.removeView(mViews.get(position));
    }


    // 每次滑动的时候生成的组件
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final View view = mViews.get(position);
//        final TextView tv = (TextView) view.findViewById(R.id.commentType);
        final ListView lv = (ListView) view.findViewById(R.id.lv_comment);

//        tv.setText(mTitles[position]);
        LoaderFactory.getContentLoader().loadContent(API.STORY_COMMENT_PREFIX + mStoryId + mCommentTails[position],
                new OnContentLoadingFinishedListener() {
                    @Override
                    public void onFinish(String content) {
                        Log.e("COMMENTACTIVITY", position + "URL:" + API.STORY_COMMENT_PREFIX + mStoryId + mCommentTails[position] + "\nContent:" + content);
                        LongComment longComment = GsonDecoder.getDecoder().decoding(content, LongComment.class);
                        if (longComment == null) return;
                        List<LongComment.CommentsBean> data = longComment.getComments();
                        Log.e("longcomment", data.size() + "个");
                        for (int i = 0; i < data.size(); i++) {
                            Log.e("longcomment", data.get(i).getContent());
                        }
                        lv.setAdapter(new LongCommentListAdapter(mContext, data));
                        Log.e("longcomment", lv.getCount() + "个");
                    }
                });
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "标题" + position;
    }
}
