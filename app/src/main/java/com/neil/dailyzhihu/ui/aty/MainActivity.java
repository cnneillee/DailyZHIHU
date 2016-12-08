package com.neil.dailyzhihu.ui.aty;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
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
import android.widget.Toast;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.ui.fm.PastFragment;
import com.neil.dailyzhihu.ui.fm.HotFragment;
import com.neil.dailyzhihu.ui.fm.LatestFragment;
import com.neil.dailyzhihu.ui.fm.SectionFragment;
import com.neil.dailyzhihu.ui.fm.ThemeFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int LATEST_FRAGMENT_IDX = 0;
    private static final int HOTTEST_FRAGMENT_IDX = 1;
    private static final int PAST_FRAGMENT_IDX = 2;
    private static final int THEME_FRAGMENT_IDX = 3;
    private static final int SECTION_FRAGMENT_IDX = 4;
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //初始化抽屉的header界面
        initHeaderView(navigationView);

        addFragments();
        showFragment(0);
        navigationView.setCheckedItem(0);
}

    private void initHeaderView(NavigationView navigationView) {
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
            case R.id.iv_avatar:
            case R.id.tv_name:
            case R.id.tv_email:
                Toast.makeText(this, "我叫 刘看山", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void addFragments() {
        fragments = new ArrayList<>();
        fragments.add(LATEST_FRAGMENT_IDX, new LatestFragment());
        fragments.add(HOTTEST_FRAGMENT_IDX, new HotFragment());
        fragments.add(PAST_FRAGMENT_IDX, new PastFragment());
        fragments.add(THEME_FRAGMENT_IDX, new ThemeFragment());
        fragments.add(SECTION_FRAGMENT_IDX, new SectionFragment());
    }

    private void showFragment(int id) {
        if (id < 0 || id >= fragments.size())
            return;
        Fragment fragment = fragments.get(id);
        FragmentManager fm = this.getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragment, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        int id = item.getItemId();
        int fmIdx = getSelecteditemFragmentIdx(id);
        String title = (String) item.getTitle();
        setActionBarTitle(title);
        Log.e(LOG_TAG, "fmidx: " + fmIdx + "   title" + title);
        showFragment(fmIdx);
        return true;
    }

    private void setActionBarTitle(String title) {
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }
    }

    /**
     * 获得被选中的fragment常量
     *
     * @param id fragment的下标
     * @return fragment常量
     */
    private int getSelecteditemFragmentIdx(int id) {
        int fmIdx = -1;
        switch (id) {
            case R.id.nav_latest:
                fmIdx = LATEST_FRAGMENT_IDX;
                break;
            case R.id.nav_hot:
                fmIdx = HOTTEST_FRAGMENT_IDX;
                break;
            case R.id.nav_past:
                fmIdx = PAST_FRAGMENT_IDX;
                break;
            case R.id.nav_theme:
                fmIdx = THEME_FRAGMENT_IDX;
                break;
            case R.id.nav_section:
                fmIdx = SECTION_FRAGMENT_IDX;
                break;
            case R.id.nav_custom:
                Intent intent = new Intent(this, CustomizeActivity.class);
                Toast.makeText(this, "个性化", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            case R.id.nav_setting:
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
                Intent intentSetting = new Intent(this, MergeDBActivity.class);
                startActivity(intentSetting);
                break;
            case R.id.nav_send:
                Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
                break;
        }
        return fmIdx;
    }
}
