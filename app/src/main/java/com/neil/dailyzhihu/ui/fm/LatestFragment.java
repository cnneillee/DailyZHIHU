package com.neil.dailyzhihu.ui.fm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.LatestStoryListAdapter;
import com.neil.dailyzhihu.bean.LatestStory;
import com.neil.dailyzhihu.ui.aty.StoryActivity;
import com.neil.dailyzhihu.utils.LoaderFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neil on 2016/3/23.
 */
public class LatestFragment extends Fragment {
    private Context mContext;
    private ListView lv;
    private List<LatestStory.StoriesBean> mDatas;
    //    private SpringIndicator springIndicator;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_latest, container, false);
        lv = (ListView) view.findViewById(R.id.lv_latest);
        //springIndicator = (SpringIndicator) view.findViewById(R.id.indicator);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        //加载数据
        LoaderFactory.getContentLoader().loadContent(Constant.URL_LATEST_NEWS, new OnContentLoadingFinishedListener() {
            @Override
            public void onFinish(String content) {
                Gson gson = new Gson();
                LatestStory latestStory = gson.fromJson((String) content, LatestStory.class);
                if (latestStory != null) {
                    mDatas = latestStory.getStories();
                    lv.setAdapter(new LatestStoryListAdapter(mDatas, mContext));
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            LatestStory.StoriesBean storiesBean = (LatestStory.StoriesBean) parent.getAdapter().getItem(position);
                            Intent intent = new Intent(mContext, StoryActivity.class);
                            intent.putExtra(Constant.STORY_ID, storiesBean.getId());
                            mContext.startActivity(intent);
                        }
                    });
                    viewPager.setAdapter(new MyPagerAdapter(getListViews(latestStory.getTop_stories())));
                }
            }
        });
    }

    private List<View> getListViews(List<LatestStory.TopStoriesBean> topStories) {
        List<View> listViews = new ArrayList<>();
        for (int i = 0; i < topStories.size(); i++) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager, null, false);
            ImageView iv = (ImageView) v.findViewById(R.id.iv_img);
            TextView tv = (TextView) v.findViewById(R.id.tv_title);
            tv.setText(topStories.get(i).getTitle());
            LoaderFactory.getImageLoader().displayImage(iv, topStories.get(i).getImage(), null);
        }
        return listViews;
    }

    class MyPagerAdapter extends PagerAdapter {
        private List<View> listViews;

        public MyPagerAdapter(List<View> listViews) {
            this.listViews = listViews;
        }

        @Override
        public int getCount() {
            return listViews.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(listViews.get(position));
            return listViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            ((ViewPager) container).removeView((View) object);
//            container.removeView(listViews.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
