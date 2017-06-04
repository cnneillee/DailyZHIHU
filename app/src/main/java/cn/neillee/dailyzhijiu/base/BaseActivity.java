package cn.neillee.dailyzhijiu.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cn.neillee.dailyzhijiu.Constant;
import cn.neillee.dailyzhijiu.app.DailyApp;
import com.neil.dailyzhijiu.R;
import cn.neillee.dailyzhijiu.di.component.ActivityComponent;
import cn.neillee.dailyzhijiu.di.component.DaggerActivityComponent;
import cn.neillee.dailyzhijiu.di.module.ActivityModule;
import cn.neillee.dailyzhijiu.utils.AppUtil;
import cn.neillee.dailyzhijiu.utils.Settings;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：Neil on 2017/4/7 15:51.
 * 邮箱：cn.neillee@gmail.com
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {
    @Inject
    protected T mPresenter;
    protected Activity mContext;
    private Unbinder mUnBinder;

    public Settings mSettings = Settings.getInstance();
    private int mLang = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Settings
        Settings.isExitConfirm = mSettings.getBoolean(Settings.EXIT_CONFIRM, true);
        Settings.isNightMode = mSettings.getBoolean(Settings.NIGHT_MODE, false);
        Settings.noPicMode = mSettings.getBoolean(Settings.NO_PIC_MODE, false);

        // change Brightness
        if (Settings.isNightMode && AppUtil.getSysScreenBrightness() > Constant.NIGHT_BRIGHTNESS) {
            AppUtil.setSysScreenBrightness(Constant.NIGHT_BRIGHTNESS);
        } else if (!Settings.isNightMode && AppUtil.getSysScreenBrightness() == Constant.NIGHT_BRIGHTNESS) {
            AppUtil.setSysScreenBrightness(Constant.DAY_BRIGHTNESS);
        }

        if (Settings.isNightMode) {
            this.setTheme(R.style.AppNightTheme);
        } else {
            this.setTheme(R.style.AppDayTheme);
        }

        // Language
        mLang = AppUtil.getCurrentLanguage();
        if (mLang > -1) {
            AppUtil.changeLanguage(this, mLang);
        }

        setContentView(getLayout());
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        initInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        DailyApp.getInstance().addActivity(this);
        initEventAndData();
    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(DailyApp.getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    protected abstract void initEventAndData();

    protected abstract void initInject();

    protected abstract int getLayout();

    protected void setToolbar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Settings.needRecreate) {
            Settings.needRecreate = false;
            this.recreate();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
        mUnBinder.unbind();
        DailyApp.getInstance().removeActivity(this);
    }

    protected void changeNightMode() {
        Settings.isNightMode = !Settings.isNightMode;
        mSettings.putBoolean(Settings.NIGHT_MODE, Settings.isNightMode);
        this.recreate();
    }

    protected void changeExitConfirm() {
        Settings.isExitConfirm = !Settings.isExitConfirm;
        this.mSettings.putBoolean(Settings.EXIT_CONFIRM, Settings.isExitConfirm);
    }

    protected void changeNoPicMode() {
        Settings.isExitConfirm = !Settings.isExitConfirm;
        this.mSettings.putBoolean(Settings.NO_PIC_MODE, Settings.isExitConfirm);
    }

    protected void changeNeedRecreated() {
        Settings.needRecreate = true;
    }
}
