package com.neil.dailyzhihu.bean;

import android.util.Log;

import com.neil.dailyzhihu.bean.cleanlayer.CleanBeforeStoryListBean;
import com.neil.dailyzhihu.bean.cleanlayer.CleanHotStoryListBean;
import com.neil.dailyzhihu.bean.cleanlayer.CleanLatestStoryListBean;
import com.neil.dailyzhihu.bean.cleanlayer.CleanSectionStoryListBean;
import com.neil.dailyzhihu.bean.cleanlayer.CleanThemeStoryListBean;
import com.neil.dailyzhihu.bean.cleanlayer.EditorBean;
import com.neil.dailyzhihu.bean.cleanlayer.SectionStory;
import com.neil.dailyzhihu.bean.cleanlayer.SimpleStory;
import com.neil.dailyzhihu.bean.cleanlayer.TopStory;
import com.neil.dailyzhihu.bean.orignallayer.BeforeStoryListBean;
import com.neil.dailyzhihu.bean.orignallayer.HotStory;
import com.neil.dailyzhihu.bean.orignallayer.LatestStory;
import com.neil.dailyzhihu.bean.orignallayer.RecentBean;
import com.neil.dailyzhihu.bean.orignallayer.SectionStoryList;
import com.neil.dailyzhihu.bean.orignallayer.ThemeStoryList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neil on 2016/4/25.
 */
public class CleanDataHelper {
    private static final String LOG_TAG = CleanDataHelper.class.getSimpleName();

    public static CleanHotStoryListBean cleanHotStory(HotStory hotStory) {
        CleanHotStoryListBean CleanHotStoryListBean = new CleanHotStoryListBean();
        List<RecentBean> recentBeanList = hotStory.getRecent();
        if (recentBeanList == null)
            return null;
        List<SimpleStory> simpleStoryList = new ArrayList<>();
        for (int i = 0; i < recentBeanList.size(); i++) {
            SimpleStory simpleStory = convertRecentBean2SimpleStory(recentBeanList.get(i));
            simpleStoryList.add(simpleStory);
        }
        CleanHotStoryListBean.setSimpleStoryList(simpleStoryList);
        return CleanHotStoryListBean;
    }

    public static CleanBeforeStoryListBean cleanBeforeStory(BeforeStoryListBean beforeStoryListBean) {
        CleanBeforeStoryListBean cleanBeforeStoryListBean = new CleanBeforeStoryListBean();
        String date = beforeStoryListBean.getDate();
        List<BeforeStoryListBean.StoriesBean> storiesBeanList = beforeStoryListBean.getStories();
        List<SimpleStory> simpleStoryList = new ArrayList<>();
        if (storiesBeanList == null)
            return null;

        for (int i = 0; i < storiesBeanList.size(); i++) {
            SimpleStory simpleStory = convertRecentBean2SimpleStory(storiesBeanList.get(i));
            simpleStoryList.add(simpleStory);
        }
        cleanBeforeStoryListBean.setSimpleStoryList(simpleStoryList);
        cleanBeforeStoryListBean.setDate(date);
        return cleanBeforeStoryListBean;
    }

    public static CleanLatestStoryListBean cleanLatestStory(LatestStory latestStory) {
        CleanLatestStoryListBean cleanLatestStoryList = new CleanLatestStoryListBean();
        List<LatestStory.StoriesBean> storiesBeanList = latestStory.getStories();
        List<SimpleStory> simpleStoryList = new ArrayList<>();
        for (int i = 0; i < storiesBeanList.size(); i++) {
            SimpleStory simpleStory = convertStoriesBean2SimpleStory(storiesBeanList.get(i));
            simpleStoryList.add(simpleStory);
        }
        List<TopStory> topStoryList = new ArrayList<>();
        for (int i = 0; i < latestStory.getTop_stories().size(); i++) {
            TopStory topStory = convertTopStories2TopStory(latestStory.getTop_stories().get(i));
            topStoryList.add(topStory);
        }
        cleanLatestStoryList.setSimpleStoryList(simpleStoryList);
        cleanLatestStoryList.setDate(latestStory.getDate());
        cleanLatestStoryList.setTopStoryList(topStoryList);
        return cleanLatestStoryList;
    }

    public static CleanSectionStoryListBean cleanSectionStoryList(SectionStoryList sectionStoryList) {
        CleanSectionStoryListBean cleanSectionStoryListBean = new CleanSectionStoryListBean();
        cleanSectionStoryListBean.setName(sectionStoryList.getName());
        cleanSectionStoryListBean.setTimestamp(sectionStoryList.getTimestamp());
        List<SectionStoryList.StoriesBean> storiesBeanList = sectionStoryList.getStories();
        List<SectionStory> newSectionStoryList = new ArrayList<>();
        for (int i = 0; i < storiesBeanList.size(); i++) {
            SectionStory sectionStory = convertStoriesBean2SimpleStory(storiesBeanList.get(i));
            newSectionStoryList.add(sectionStory);
        }
        cleanSectionStoryListBean.setName(sectionStoryList.getName());
        cleanSectionStoryListBean.setTimestamp(sectionStoryList.getTimestamp());
        cleanSectionStoryListBean.setSectionStoryList(newSectionStoryList);
        return cleanSectionStoryListBean;
    }

    public static CleanThemeStoryListBean cleanThemeStoryList(ThemeStoryList themeStoryList) {
        CleanThemeStoryListBean cleanThemeStoryListBean = new CleanThemeStoryListBean();
        cleanThemeStoryListBean.setName(themeStoryList.getName());
        cleanThemeStoryListBean.setBackground(themeStoryList.getBackground());
        cleanThemeStoryListBean.setDescription(themeStoryList.getDescription());
        cleanThemeStoryListBean.setImage(themeStoryList.getImage());
        cleanThemeStoryListBean.setImageSource(themeStoryList.getImage_source());
        List<ThemeStoryList.EditorsBean> editorsBeanList = themeStoryList.getEditors();
        List<ThemeStoryList.StoriesBean> storiesBeen = themeStoryList.getStories();
        List<EditorBean> editorBeanList = new ArrayList<>();
        for (int i = 0; i < editorsBeanList.size(); i++) {
            EditorBean editorBean = convertEditorsBean2EditorBean(editorsBeanList.get(i));
            editorBeanList.add(editorBean);
        }
        List<SimpleStory> simpleStoryList = new ArrayList<>();
        for (int i = 0; i < storiesBeen.size(); i++) {
            SimpleStory simpleStory = convertStoriesBean2SimpleStory(storiesBeen.get(i));
            simpleStoryList.add(simpleStory);
        }
        cleanThemeStoryListBean.setEditorBeanList(editorBeanList);
        cleanThemeStoryListBean.setSimpleStoryList(simpleStoryList);
        return cleanThemeStoryListBean;
    }

    private static SimpleStory convertStoriesBean2SimpleStory(ThemeStoryList.StoriesBean storiesBean) {
        SimpleStory simpleStory = new SimpleStory();
        if (storiesBean.getImages() != null)
            simpleStory.setImageUrl(storiesBean.getImages().get(0));
        simpleStory.setTitle(storiesBean.getTitle());
        simpleStory.setStoryId(storiesBean.getStoryId());
        simpleStory.setType(storiesBean.getType());
        return simpleStory;
    }

    private static EditorBean convertEditorsBean2EditorBean(ThemeStoryList.EditorsBean editorsBean) {
        EditorBean editorBean = new EditorBean();
        editorBean.setAvatar(editorsBean.getAvatar());
        editorBean.setBio(editorsBean.getBio());
        editorBean.setId(editorsBean.getId());
        editorBean.setName(editorsBean.getName());
        editorBean.setUrl(editorsBean.getUrl());
        return editorBean;
    }

    private static TopStory convertTopStories2TopStory(LatestStory.TopStoriesBean topStoriesBean) {
        TopStory topStory = new TopStory();
        topStory.setStoryId(topStoriesBean.getStoryId());
        topStory.setGaPrefix(topStoriesBean.getGa_prefix());
        topStory.setType(topStoriesBean.getType());
        topStory.setTitle(topStoriesBean.getTitle());
        topStory.setImageUrl(topStoriesBean.getImages().get(0));
        return topStory;
    }

    public static SimpleStory convertRecentBean2SimpleStory(RecentBean recentBean) {
        SimpleStory simpleStory = new SimpleStory();
        simpleStory.setStoryId(recentBean.getStoryId());
        simpleStory.setImageUrl(recentBean.getThumbnail());
        simpleStory.setTitle(recentBean.getTitle());
        return simpleStory;
    }

    public static SimpleStory convertRecentBean2SimpleStory(BeforeStoryListBean.StoriesBean recentBean) {
        SimpleStory simpleStory = new SimpleStory();
        simpleStory.setStoryId(recentBean.getStoryId());
        simpleStory.setImageUrl(recentBean.getImages().get(0));
        simpleStory.setType(recentBean.getType());
        simpleStory.setGaPrefix(recentBean.getGa_prefix());
        simpleStory.setTitle(recentBean.getTitle());
        return simpleStory;
    }

    public static SimpleStory convertStoriesBean2SimpleStory(LatestStory.StoriesBean storiesBean) {
        SimpleStory simpleStory = new SimpleStory();
        simpleStory.setStoryId(storiesBean.getStoryId());
        simpleStory.setImageUrl(storiesBean.getImages().get(0));
        simpleStory.setTitle(storiesBean.getTitle());
        simpleStory.setGaPrefix(storiesBean.getGa_prefix());
        simpleStory.setType(storiesBean.getType());
        return simpleStory;
    }

    public static SectionStory convertStoriesBean2SimpleStory(SectionStoryList.StoriesBean storiesBean) {
        SectionStory sectionStory = new SectionStory();
        sectionStory.setStoryId(storiesBean.getStoryId());
        sectionStory.setImageUrl(storiesBean.getImages().get(0));
        sectionStory.setDate(storiesBean.getDate());
        sectionStory.setDisplayDate(storiesBean.getDisplay_date());
        sectionStory.setTitle(storiesBean.getTitle());
        return sectionStory;
    }
}
