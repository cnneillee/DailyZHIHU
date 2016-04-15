package com.neil.dailyzhihu.ui.fm;

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
import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.LatestStoryListAdapter;
import com.neil.dailyzhihu.bean.BeforeStory;
import com.neil.dailyzhihu.ui.aty.StoryActivity;
import com.neil.dailyzhihu.utils.LoaderFactory;

import java.util.List;

/**
 * Created by Neil on 2016/3/23.
 */
public class PastFragment extends Fragment {
    private Context mContext;
    private ListView lv;
    private List<BeforeStory.StoriesBean> mDatas;
    //    private SpringIndicator springIndicator;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past, container, false);
        lv = (ListView) view.findViewById(R.id.lv_before);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        //加载数据
        LoaderFactory.getContentLoader().loadContent(Constant.BEFORE_NEWS_HEADER + "20131119",
                new OnContentLoadingFinishedListener() {
                    @Override
                    public void onFinish(String content) {
                        Gson gson = new Gson();
                        BeforeStory beforeStory = gson.fromJson(content, BeforeStory.class);
                        if (beforeStory != null) {
                            mDatas = beforeStory.getStories();
                            lv.setAdapter(new LatestStoryListAdapter(mDatas, mContext));
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    BeforeStory.StoriesBean storiesBean = (BeforeStory.StoriesBean) parent.getAdapter().getItem(position);
                                    Intent intent = new Intent(mContext, StoryActivity.class);
                                    intent.putExtra(Constant.STORY_ID, storiesBean.getId());
                                    mContext.startActivity(intent);
                                }
                            });
                        }
                    }
                }
        );
    }
}
