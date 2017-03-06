package com.neil.dailyzhihu.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.MainPageFragmentPagerAdapter;
import com.neil.dailyzhihu.ui.about.AboutActivity;
import com.neil.dailyzhihu.ui.column.NavColumnsActivity;
import com.neil.dailyzhihu.ui.theme.NavThemesActivity;
import com.neil.dailyzhihu.ui.setting.SettingActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * MainActivity
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.tabs)
    TabLayout mTabs;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.vp_news_tab)
    ViewPager mvpNewsTab;
    @Bind(R.id.nav_view)
    NavigationView mNavView;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.app_bar_main)
    LinearLayout mContentMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        String tabsTitleArray[] = {getResources().getString(R.string.tab_latest), getResources().getString(R.string.tab_hot), getResources().getString(R.string.tab_past)};
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new LatestFragment());
        fragmentList.add(new HotFragment());
        fragmentList.add(new PastFragment());
        FragmentPagerAdapter fAdapter = new MainPageFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, tabsTitleArray);
        // Tabs与FragmentPager关联
        mvpNewsTab.setAdapter(fAdapter);
        mTabs.setupWithViewPager(mvpNewsTab);

        // 初始化抽屉的header界面
        initDrawerLayout(mNavView);
    }

    private void initDrawerLayout(NavigationView navigationView) {
        // 抽屉的设置
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavView.setNavigationItemSelectedListener(this);

        LinearLayout header = (LinearLayout) navigationView.getHeaderView(0);
        ImageView avatar = (ImageView) header.findViewById(R.id.iv_avatar);
        avatar.setOnClickListener(this);
        TextView name = (TextView) header.findViewById(R.id.tv_name);
        name.setOnClickListener(this);
        TextView email = (TextView) header.findViewById(R.id.tv_email);
        email.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 抽屉顶部的点击事件
            case R.id.iv_avatar:
            case R.id.tv_name:

            case R.id.tv_email:
                Snackbar.make(mDrawerLayout, getResources().getString(R.string.to_do), Snackbar.LENGTH_SHORT).show();
                break;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.nav_mainpage:
                Log.i(LOG_TAG, "主页item被点击");
                break;
            case R.id.nav_topics:
                Log.i(LOG_TAG, "主题item被点击");
                intent = new Intent(this, NavThemesActivity.class);
                break;
            case R.id.nav_columns:
                Log.i(LOG_TAG, "模块item被点击");
                intent = new Intent(this, NavColumnsActivity.class);
                break;
            case R.id.nav_collection:
                Snackbar.make(mContentMain, getResources().getString(R.string.to_do), Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.nav_setting:
                intent = new Intent(this, SettingActivity.class);
                break;
            case R.id.nav_night:
                Snackbar.make(mContentMain, getResources().getString(R.string.to_do), Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.nav_about:
                intent = new Intent(this, AboutActivity.class);
                break;
        }
        if (intent != null) startActivity(intent);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
