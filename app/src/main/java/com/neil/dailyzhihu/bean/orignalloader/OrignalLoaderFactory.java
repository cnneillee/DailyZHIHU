package com.neil.dailyzhihu.bean.orignalloader;

import com.neil.dailyzhihu.utils.cnt.UniversalContentLoaderTest;

/**
 * Created by Neil on 2016/5/7.
 */
public class OrignalLoaderFactory {
    private static UniversalContentLoaderTest sInstanceCntVolley;

    /**
     * 获取内容加载器
     *
     * @return ContentLoaderWrapper(接口)，隐藏了实现
     */
    public static UniversalContentLoaderTest getContentLoaderVolley() {
        if (sInstanceCntVolley == null) {
            synchronized (OrignalLoaderFactory.class) {
                if (sInstanceCntVolley == null) {
                    sInstanceCntVolley = new UniversalContentLoaderTest();
                }
            }
        }
        return sInstanceCntVolley;
    }

}
