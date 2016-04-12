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
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.LatestNewsAdapter;
import com.neil.dailyzhihu.bean.BeforeNews;
import com.neil.dailyzhihu.bean.LatestNews;
import com.neil.dailyzhihu.bean.NewsBean;
import com.neil.dailyzhihu.ui.StoryActivity;
import com.neil.dailyzhihu.utils.ContentLoader;
import com.neil.dailyzhihu.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neil on 2016/3/23.
 */
public class BeforeFragment extends Fragment {
    private Context mContext;
    private ListView lv;
    private List<BeforeNews.StoriesBean> mDatas;
    //    private SpringIndicator springIndicator;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_before, container, false);
        lv = (ListView) view.findViewById(R.id.lv_before);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        //加载数据
        ContentLoader.loadString(Constant.BEFORE_NEWS_HEADER + "20131119", new ImageLoader.OnFinishListener() {
            @Override
            public void onFinish(Object s) {
                Gson gson = new Gson();
                System.out.println("---" + s);
                BeforeNews beforeNews = gson.fromJson((String) s, BeforeNews.class);
                if (beforeNews != null) {
                    mDatas = beforeNews.getStories();

                    lv.setAdapter(new LatestNewsAdapter(mDatas, mContext));
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            BeforeNews.StoriesBean storiesBean = (BeforeNews.StoriesBean) parent.getAdapter().getItem(position);

                            Intent intent = new Intent(mContext, StoryActivity.class);
                            intent.putExtra("STORY_ID", storiesBean.getId());
                            mContext.startActivity(intent);
                        }
                    });


                }
            }
        });

    }
}
