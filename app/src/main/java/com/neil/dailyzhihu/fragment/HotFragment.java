package com.neil.dailyzhihu.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.HotStoriesAdapter;
import com.neil.dailyzhihu.bean.HotStories;
import com.neil.dailyzhihu.bean.Themes;
import com.neil.dailyzhihu.ui.StoryActivity;
import com.neil.dailyzhihu.utils.ContentLoader;
import com.neil.dailyzhihu.utils.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Neil on 2016/3/23.
 */
public class HotFragment extends Fragment {


    @Bind(R.id.lv_hot)
    ListView lvHot;

    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getContext();
        ContentLoader.loadString("http://news-at.zhihu.com/api/3/news/hot", new ImageLoader.OnFinishListener() {
            @Override
            public void onFinish(Object s) {
                Gson gson = new Gson();
                HotStories hotStories = gson.fromJson((String) s, HotStories.class);
                List<HotStories.RecentBean> recentBean = hotStories.getRecent();
                lvHot.setAdapter(new HotStoriesAdapter(context, recentBean));
            }
        });
        lvHot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HotStories.RecentBean bean = (HotStories.RecentBean) parent.getAdapter().getItem(position);
                Intent intent = new Intent(context, StoryActivity.class);
                intent.putExtra("STORY_ID", bean.getNews_id());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
