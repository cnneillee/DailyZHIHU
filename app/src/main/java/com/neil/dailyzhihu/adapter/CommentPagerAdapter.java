package com.neil.dailyzhihu.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.api.API;
import com.neil.dailyzhihu.bean.orignallayer.LongComment;
import com.neil.dailyzhihu.listener.OnContentLoadedListener;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.cnt.ContentLoaderWrapper;
import com.neil.dailyzhihu.utils.load.LoaderFactory;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Neil on 2016/4/21 22:35.
 * 邮箱：cn.neillee@gmail.com
 */
public class CommentPagerAdapter extends PagerAdapter {

    private static final String LOG_TAG = CommentPagerAdapter.class.getSimpleName();
    private ContentLoaderWrapper mLoaderWrapper;

    public enum CommentType {
        LONG("长评论", "/long-comments", 0), SHORT("短评论", "/short-comments", 1);
        public String cn;
        public String en;
        public int index;

        CommentType(String cnName, String enName, int index) {
            this.cn = cnName;
            this.en = enName;
            this.index = index;
        }

        public static CommentType getType(int index) {
            return index == CommentType.LONG.index ? CommentType.LONG : CommentType.SHORT;
        }
    }

    private List<View> mViews = new ArrayList<>();
    private Context mContext;
    private String mStoryId;

    public CommentPagerAdapter(Context context, String storyId) {
        this.mContext = context;
        this.mStoryId = storyId;
        View view1 = LayoutInflater.from(mContext).inflate(R.layout.vp_item_comment, null, false);
        View view2 = LayoutInflater.from(mContext).inflate(R.layout.vp_item_comment, null, false);
        mViews.add(view1);
        mViews.add(view2);
        mLoaderWrapper = LoaderFactory.getContentLoader();
        loadData(view1, 0);
        loadData1(view2, 1);

//        loadData(view1, CommentType.LONG.index);
//        loadData(view2, CommentType.SHORT.index);
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
        View view = mViews.get(position);
//        loadData(mViews.get(0), 0);
//        loadData1(mViews.get(1), 1);
//        Logger.e(position + "");
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

    private OnContentLoadedListener mListener1 = new OnContentLoadedListener() {
        @Override
        public void onSuccess(String content, String url) {
            View view = mViews.get(0);
            ListView listView = (ListView) view.findViewById(R.id.lv_comment);
            Logger.e("onSuccess: " + 0 + "-" + url);
            LongComment longComment = GsonDecoder.getDecoder().decoding(content, LongComment.class);
            if (longComment == null) return;
            List<LongComment.CommentsBean> data = longComment.getComments();
            listView.setAdapter(new LongCommentListAdapter(mContext, data));
        }
    };
    private OnContentLoadedListener mListener2 = new OnContentLoadedListener() {
        @Override
        public void onSuccess(String content, String url) {
            View view = mViews.get(1);
            ListView listView = (ListView) view.findViewById(R.id.lv_comment);
            Logger.e("onSuccess: " + 1 + "-" + url);
            LongComment longComment = GsonDecoder.getDecoder().decoding(content, LongComment.class);
            if (longComment == null) return;
            List<LongComment.CommentsBean> data = longComment.getComments();
            listView.setAdapter(new LongCommentListAdapter(mContext, data));
        }
    };
    private void loadData(View view, final int index) {
        Logger.e(index + "-" + API.STORY_COMMENT_PREFIX + mStoryId + CommentType.getType(index).en);
        mLoaderWrapper.loadContent(API.STORY_COMMENT_PREFIX + mStoryId + CommentType.getType(index).en,mListener1);
    }

    private void loadData1(View view, final int index) {
        Logger.e(index + "-" + API.STORY_COMMENT_PREFIX + mStoryId + CommentType.getType(index).en);
        mLoaderWrapper.loadContent(API.STORY_COMMENT_PREFIX + mStoryId + CommentType.getType(index).en, mListener2);
    }
}
