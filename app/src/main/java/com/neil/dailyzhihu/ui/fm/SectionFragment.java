package com.neil.dailyzhihu.ui.fm;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.gson.Gson;
import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.UniversalBlockGridView;
import com.neil.dailyzhihu.bean.block.SectionList;
import com.neil.dailyzhihu.utils.cnt.ContentLoader;
import com.neil.dailyzhihu.utils.img.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Neil on 2016/3/24.
 */
public class SectionFragment extends Fragment {
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
        View view = inflater.inflate(R.layout.fragment_section, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getContext();
        ContentLoader.loadString(Constant.SECTIONS, new ImageLoader.OnFinishListener() {
            @Override
            public void onFinish(Object s) {
                Gson gson = new Gson();
                SectionList sectionList = gson.fromJson((String) s, SectionList.class);
                List<SectionList.DataBean> mDatas = sectionList.getData();
                gvSections.setAdapter(new UniversalBlockGridView(context, mDatas));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
