package cn.neillee.dailyzhijiu.presenter.constract;

import cn.neillee.dailyzhijiu.base.BasePresenter;
import cn.neillee.dailyzhijiu.base.BaseView;

/**
 * 作者：Neil on 2017/4/11 21:10.
 * 邮箱：cn.neillee@gmail.com
 */

public interface ImageSplashContract {
    interface View extends BaseView {
        void showImage(String imgUrl, String intro);
        void showError(String errMsg);
    }

    interface Presenter extends BasePresenter<View> {
        void getSplash(int splashType);
    }
}
