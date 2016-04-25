package com.neil.dailyzhihu.utils.db.catalog;

import android.content.Context;

import com.neil.dailyzhihu.bean.orignallayer.RecentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neil on 2016/4/25.
 */
public class LatestStoryCatalogDBFactory {
    private static final String LOG_TAG = LatestStoryCatalogDBFactory.class.getSimpleName();
    private static LatestCatalogDBdao sInstanceImg;

    public static LatestCatalogDBdao getInstance(Context context) {
        if (sInstanceImg == null) {
            synchronized (LatestCatalogDBdao.class) {
                if (sInstanceImg == null) {
                    sInstanceImg = new LatestCatalogDBdao(context);
                }
            }
        }
        return sInstanceImg;
    }

    public static List<RecentBean> convertStoryCatalog2Beans(List<StoryCatalog> catalogList) {
        if (catalogList == null)
            return null;
        List<RecentBean> recentBean = new ArrayList<>();
        for (int i = 0; i < catalogList.size(); i++) {
            StoryCatalog storyCatalog = catalogList.get(i);
            String title = storyCatalog.getTitle();
            String stoyId = storyCatalog.getStoryId();
            String imagePath = storyCatalog.getImagePath();
            String imageUrl = storyCatalog.getImageUrl();
            RecentBean bean = new RecentBean(Integer.valueOf(stoyId), "", imageUrl, title);
            recentBean.add(bean);
        }
        return recentBean;
    }
}
