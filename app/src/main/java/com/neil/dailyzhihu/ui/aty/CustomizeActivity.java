package com.neil.dailyzhihu.ui.aty;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.Child;
import com.neil.dailyzhihu.adapter.ExpandableLVAdapter;
import com.neil.dailyzhihu.adapter.MyGroup;
import com.neil.dailyzhihu.utils.db.FavoriteStory;
import com.neil.dailyzhihu.utils.db.FavoriteStoryDBdaoFactory;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neil on 2016/4/21.
 */
public class CustomizeActivity extends AppCompatActivity {
    private static final String LOG_TAG = CustomizeActivity.class.getSimpleName();
    private ExpandableListView lvStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);
        List<FavoriteStory> favoriteStoryList = FavoriteStoryDBdaoFactory.getInstance(this).queryAll();
        Log.e(LOG_TAG, favoriteStoryList.size() + "");
        List<MyGroup> lists = formateFav(favoriteStoryList);
        lvStar = (ExpandableListView) findViewById(R.id.lv_myStar);
        lvStar.setAdapter(new ExpandableLVAdapter(lists, this));
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("我的收藏", getResources().getDrawable(R.drawable.ic_star)).setContent(R.id.tabStar));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("我的分享", getResources().getDrawable(R.drawable.ic_share)).setContent(R.id.tabShare));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("个性化");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private List<MyGroup> formateFav(List<FavoriteStory> favoriteStoryList) {
        List<MyGroup> lists = new ArrayList<>();
        if (favoriteStoryList == null)
            return null;
        for (int i = 0; i < favoriteStoryList.size(); i++) {
            FavoriteStory story = favoriteStoryList.get(i);
            List<Child> children = new ArrayList<>();
            Child child = new Child(story.getTitle(), story.getImgUrl(), Integer.valueOf(story.getStoryId()));
            children.add(child);
            MyGroup myGroup = new MyGroup(story.getStaredTimestamp(), children);
            lists.add(myGroup);
        }
        return lists;
    }

    private List<MyGroup> makeList() {
        List<MyGroup> lists = new ArrayList<>();
        Child child = new Child("10.3title1", "baidu.com", 12);
        Child child1 = new Child("10.3title2", "baidu.com", 102);
        Child child2 = new Child("10.3title3", "baidu.com", 71272);
        Child child3 = new Child("10.3title4", "baidu.com", 78128);
        Child child4 = new Child("10.3title5", "baidu.com", 27187278);
        List<Child> children1 = new ArrayList<>();
        children1.add(child);
        children1.add(child1);
        children1.add(child2);
        children1.add(child3);
        children1.add(child4);

        Child child01 = new Child("6.1title1", "baidu.com", 12);
        Child child12 = new Child("6.1title2", "baidu.com", 102);
        Child child23 = new Child("6.1title3", "baidu.com", 71272);
        Child child34 = new Child("6.1title4", "baidu.com", 78128);
        Child child45 = new Child("6.1title5", "baidu.com", 27187278);
        List<Child> children2 = new ArrayList<>();
        children2.add(child01);
        children2.add(child12);
        children2.add(child23);
        children2.add(child34);
        children2.add(child45);

        Child child011 = new Child("3.15title1", "baidu.com", 12);
        Child child122 = new Child("3.15title2", "baidu.com", 102);
        Child child233 = new Child("3.15title3", "baidu.com", 71272);
        Child child344 = new Child("3.15title4", "baidu.com", 78128);
        Child child455 = new Child("3.15title5", "baidu.com", 27187278);
        List<Child> children3 = new ArrayList<>();
        children3.add(child);
        children3.add(child1);
        children3.add(child2);
        children3.add(child3);
        children3.add(child4);

        MyGroup[] groups = new MyGroup[]{new MyGroup("10.3", children1), new MyGroup("6.1", children2), new MyGroup("3.15", children3)};
        for (int i = 0; i < groups.length; i++) {
            lists.add(groups[i]);
        }
        return lists;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.custom_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_scan:
                scan();
                Toast.makeText(this, "扫描", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }


    private void scan() {
        int requestCode = 0x123;
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x123) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                String resultUrl = (String) bundle.get("result");
                if (TextUtils.isEmpty(resultUrl)) {
                    Toast.makeText(this, "识别失败", Toast.LENGTH_SHORT).show();
                } else {
                    decodingResultUrl(resultUrl);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void decodingResultUrl(String resultUrl) {
        //http://daily.zhihu.com/story/4772126
        if (resultUrl.contains(Constant.URL_SHARE_STORY_HEAD)) {
            String storyIdStr = resultUrl.substring(Constant.URL_SHARE_STORY_HEAD.length(), resultUrl.length());
            try {
                int storyId = Integer.valueOf(storyIdStr);
                Intent intent = new Intent(this, StoryActivity.class);
                intent.putExtra(Constant.STORY_ID, storyId);
                Toast.makeText(this, "正在跳转...", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, "识别不出来的呢！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "这不是本站文章哦~~", Toast.LENGTH_SHORT).show();
        }
    }

    public void scan(View view) {
        scan();
    }
}
