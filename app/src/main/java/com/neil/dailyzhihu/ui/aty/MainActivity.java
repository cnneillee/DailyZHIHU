package com.neil.dailyzhihu.ui.aty;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.ui.fm.PastFragment;
import com.neil.dailyzhihu.ui.fm.HotFragment;
import com.neil.dailyzhihu.ui.fm.LatestFragment;
import com.neil.dailyzhihu.ui.fm.SectionFragment;
import com.neil.dailyzhihu.ui.fm.ThemeFragment;
import com.neil.dailyzhihu.ui.theme.SharedPreferrenceHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private ArrayList<Fragment> fragments;
    private final int LATEST_FRAGMENT_IDX = 0;
    private final int HOTTEST_FRAGMENT_IDX = 1;
    private final int PAST_FRAGMENT_IDX = 2;
    private final int THEME_FRAGMENT_IDX = 3;
    private final int SECTION_FRAGMENT_IDX = 4;
    private String theme = Constant.DEFAULT_TIME_THEME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int themeRes = SharedPreferrenceHelper.getAppTheme(this);
        theme = SharedPreferrenceHelper.gettheme(this);
        setTheme(themeRes);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addFragments();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        initHeaderView(navigationView);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initHeaderView(NavigationView navigationView) {
        LinearLayout header = (LinearLayout) navigationView.getHeaderView(0);
        ImageView avatar = (ImageView) header.findViewById(R.id.iv_avatar);
        avatar.setOnClickListener(this);
        TextView name = (TextView) header.findViewById(R.id.tv_name);
        name.setOnClickListener(this);
        TextView email = (TextView) header.findViewById(R.id.tv_email);
        email.setOnClickListener(this);
        ImageView dayMode = (ImageView) header.findViewById(R.id.iv_dayMode);
        dayMode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_avatar:
            case R.id.tv_name:
            case R.id.tv_email:
                Toast.makeText(this, "我叫 刘看山", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_dayMode:
                changeDayMode((ImageView) v);
                break;
        }
    }

    private void changeDayMode(ImageView v) {
        switch (theme) {
            case Constant.DAY_TIME_THEME:
                Toast.makeText(this, "切换成白天模式", Toast.LENGTH_SHORT).show();
                v.setImageResource(R.drawable.ic_night);
                Toast.makeText(this, "现在是白天", Toast.LENGTH_SHORT).show();
                break;
            case Constant.NIGHT_TIME_THEME:
                Toast.makeText(this, "切换成夜间模式", Toast.LENGTH_SHORT).show();
                v.setImageResource(R.drawable.ic_day);
                Toast.makeText(this, "现在是夜间", Toast.LENGTH_SHORT).show();
                break;
        }
        SharedPreferrenceHelper.switchAppTheme(this);
        reload();
    }

    private void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);//不设置进入退出动画
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
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
        int id = item.getItemId();
        if (id == R.id.nav_latest) {
            showFragment(LATEST_FRAGMENT_IDX);
        } else if (id == R.id.nav_hot) {
            showFragment(HOTTEST_FRAGMENT_IDX);
        } else if (id == R.id.nav_past) {
            showFragment(PAST_FRAGMENT_IDX);
        } else if (id == R.id.nav_theme) {
            showFragment(THEME_FRAGMENT_IDX);
        } else if (id == R.id.nav_section) {
            showFragment(SECTION_FRAGMENT_IDX);
        } else if (id == R.id.nav_setting) {
            Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
