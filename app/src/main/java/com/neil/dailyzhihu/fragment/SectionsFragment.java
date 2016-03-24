package com.neil.dailyzhihu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.gson.Gson;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.SectionsAdapter;
import com.neil.dailyzhihu.adapter.ThemesListAdapter;
import com.neil.dailyzhihu.bean.SectionsList;
import com.neil.dailyzhihu.bean.Themes;
import com.neil.dailyzhihu.utils.ContentLoader;
import com.neil.dailyzhihu.utils.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Neil on 2016/3/24.
 */
public class SectionsFragment extends Fragment {


    @Bind(R.id.gv_sections)
    GridView gvSections;
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sections, container, false);


        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getContext();
        ContentLoader.loadString("http://news-at.zhihu.com/api/3/sections", new ImageLoader.OnFinishListener() {
            @Override
            public void onFinish(Object s) {
                Gson gson = new Gson();
                SectionsList sectionsList = gson.fromJson((String) s, SectionsList.class);
                List<SectionsList.DataBean> mDatas = sectionsList.getData();
                gvSections.setAdapter(new SectionsAdapter(context, mDatas));
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

    }
}
