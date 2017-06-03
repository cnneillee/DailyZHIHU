package com.neil.dailyzhihu.ui.about;

import android.support.v7.widget.Toolbar;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.base.BaseSimpleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：Neil on 2016/12/8 17:31.
 * 邮箱：cn.neillee@gmail.com
 */
public class AboutActivity extends BaseSimpleActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_about;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);

        setupToolbar(mToolbar);

        getFragmentManager().beginTransaction().
                replace(R.id.fl_about_fragment, new AboutFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

}
