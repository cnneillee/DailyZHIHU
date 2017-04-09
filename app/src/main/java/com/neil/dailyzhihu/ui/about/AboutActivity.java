package com.neil.dailyzhihu.ui.about;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.base.NightModeBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：Neil on 2016/12/8 17:31.
 * 邮箱：cn.neillee@gmail.com
 */
public class AboutActivity extends NightModeBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private View.OnClickListener upBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
        mToolbar.setNavigationOnClickListener(upBtnListener);

        getFragmentManager().beginTransaction().
                replace(R.id.fl_about_fragment, new AboutFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

}
