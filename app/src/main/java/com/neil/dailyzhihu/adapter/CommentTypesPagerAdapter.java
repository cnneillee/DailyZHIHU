package com.neil.dailyzhihu.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.mvp.model.http.api.API;
import com.neil.dailyzhihu.mvp.model.bean.orignal.CommentListBean;
import com.neil.dailyzhihu.listener.OnContentLoadedListener;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.cnt.ContentLoaderWrapper;
import com.neil.dailyzhihu.utils.load.LoaderFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Neil on 2016/4/21 22:35.
 * 邮箱：cn.neillee@gmail.com
 */
public class CommentTypesPagerAdapter extends PagerAdapter {

    public enum CommentType {
        LONG(R.string.long_comment, "/long-comments", 0), SHORT(R.string.short_comment, "/short-comments", 1);
        public int nameRes;
        public String urlSuffix;
        public int index;

        CommentType(int nameRes, String urlSuffix, int index) {
            this.nameRes = nameRes;
            this.urlSuffix = urlSuffix;
            this.index = index;
        }

        public static CommentType getType(int index) {
            return index == CommentType.LONG.index ? CommentType.LONG : CommentType.SHORT;
        }
    }

    private List<View> mViews = new ArrayList<>();
    private Context mContext;

    public CommentTypesPagerAdapter(Context context, String storyId) {
        this.mContext = context;
        View view1 = LayoutInflater.from(mContext).inflate(R.layout.vp_item_comment, null, false);
        View view2 = LayoutInflater.from(mContext).inflate(R.layout.vp_item_comment, null, false);
        mViews.add(view1);
        mViews.add(view2);
        loadComments(storyId);
    }

    private void loadComments(String storyId) {
        ContentLoaderWrapper loaderWrapper = LoaderFactory.getContentLoader();
        loaderWrapper.loadContent(API.STORY_COMMENT_PREFIX + storyId + CommentType.LONG.urlSuffix, mListenerLong);
        loaderWrapper.loadContent(API.STORY_COMMENT_PREFIX + storyId + CommentType.SHORT.urlSuffix, mListenerShort);
    }

    //viewpager中的组件数量
    @Override
    public int getCount() {
        return mViews.size();
    }

    // 滑动切换的时候销毁当前的组件
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }


    // 每次滑动的时候生成的组件
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = mViews.get(position);
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
        return mContext != null ? mContext.getResources().getString(CommentType.getType(position).nameRes) : "";
    }

    private OnContentLoadedListener mListenerLong = new OnContentLoadedListener() {
        @Override
        public void onSuccess(String content, String url) {
            View view = mViews.get(CommentType.LONG.index);
            ListView listView = (ListView) view.findViewById(R.id.lv_comment);
            TextView addonView = (TextView) view.findViewById(R.id.tv_addon_info);
            CommentListBean commentListBean = GsonDecoder.getDecoder().decoding(content, CommentListBean.class);
            if (commentListBean != null) {
                List<CommentListBean.CommentsBean> data = commentListBean.getComments();
                listView.setAdapter(new CommentListBaseAdapter(mContext, data));
                addonView.setVisibility((data == null || data.size() <= 0) ? View.VISIBLE : View.INVISIBLE);
            } else {
                addonView.setVisibility(View.VISIBLE);
            }
        }
    };

    private OnContentLoadedListener mListenerShort = new OnContentLoadedListener() {
        @Override
        public void onSuccess(String content, String url) {
            View view = mViews.get(CommentType.SHORT.index);
            ListView listView = (ListView) view.findViewById(R.id.lv_comment);
            TextView addonView = (TextView) view.findViewById(R.id.tv_addon_info);
            CommentListBean commentListBean = GsonDecoder.getDecoder().decoding(content, CommentListBean.class);
            if (commentListBean != null) {
                List<CommentListBean.CommentsBean> data = commentListBean.getComments();
                listView.setAdapter(new CommentListBaseAdapter(mContext, data));
                addonView.setVisibility((data == null || data.size() <= 0) ? View.VISIBLE : View.INVISIBLE);
            } else {
                addonView.setVisibility(View.VISIBLE);
            }
        }
    };
}
