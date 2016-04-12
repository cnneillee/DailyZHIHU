package com.neil.dailyzhihu.fragment;

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
import com.neil.dailyzhihu.MyApplication;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.LatestNewsAdapter;
import com.neil.dailyzhihu.bean.LatestNews;
import com.neil.dailyzhihu.ui.StoryActivity;
import com.neil.dailyzhihu.utils.ContentLoader;
import com.neil.dailyzhihu.utils.ImageLoader;
import com.neil.dailyzhihu.utils.UniversalLoader;

import java.util.ArrayList;
import java.util.List;

import github.chenupt.springindicator.SpringIndicator;

/**
 * Created by Neil on 2016/3/23.
 */
public class LatestFragment extends Fragment {
    private Context mContext;
    private ListView lv;
    private List<LatestNews.StoriesBean> mDatas;
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
        ContentLoader.loadString(Constant.URL_LATEST_NEWS, new ImageLoader.OnFinishListener() {
            @Override
            public void onFinish(Object s) {
                Gson gson = new Gson();
                System.out.println("---" + s);
                LatestNews latestNews = gson.fromJson((String) s, LatestNews.class);
                if (latestNews != null) {
                    mDatas = latestNews.getStories();

                    lv.setAdapter(new LatestNewsAdapter(mDatas, mContext));
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            LatestNews.StoriesBean storiesBean = (LatestNews.StoriesBean) parent.getAdapter().getItem(position);

                            Intent intent = new Intent(mContext, StoryActivity.class);
                            intent.putExtra("STORY_ID", storiesBean.getId());
                            mContext.startActivity(intent);
                        }
                    });

                    viewPager.setAdapter(new MyPagerAdapter(getListViews(latestNews.getTop_stories())));
                    //springIndicator.setViewPager(viewPager);
                }
            }
        });

    }


    private List<View> getListViews(List<LatestNews.TopStoriesBean> topStories) {
        List<View> listViews = new ArrayList<>();
        for (int i = 0; i < topStories.size(); i++) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_top_pager, null, false);
            ImageView iv = (ImageView) v.findViewById(R.id.iv_img);
            TextView tv = (TextView) v.findViewById(R.id.tv_title);
            tv.setText(topStories.get(i).getTitle());

            //TODO 图片加载
            MyApplication myApplication = (MyApplication) mContext.getApplicationContext();
            UniversalLoader loader = myApplication.getUniversalLoader();
            loader.loadImage(mContext, iv, topStories.get(i).getImage(), null);
            listViews.add(v);
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
