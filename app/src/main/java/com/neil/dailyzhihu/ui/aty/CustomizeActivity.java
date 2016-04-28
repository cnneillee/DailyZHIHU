package com.neil.dailyzhihu.ui.aty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.ExpandableLVAdapter;
import com.neil.dailyzhihu.adapter.MyGroup;
import com.neil.dailyzhihu.bean.ShareRecord;
import com.neil.dailyzhihu.bean.StarRecord;
import com.neil.dailyzhihu.utils.db.FavoriteStory;
import com.neil.dailyzhihu.utils.db.catalog.a.DBFactory;
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
//        List<FavoriteStory> favoriteStoryList = FavoriteStoryDBdaoFactory.getInstance(this).queryAll();
        List<FavoriteStory> favoriteStoryList = new ArrayList<>();
        List<StarRecord> starRecordList = DBFactory.getIIDBStarRecordDetailStoryTabledao(this).queryAllStarRecord();
        if (starRecordList == null) return;

        Log.e(LOG_TAG, "starRecordList.size()" + starRecordList.size());
        List<ShareRecord> shareRecordList = DBFactory.getsIDBShareRecordDetailStoryTabledao(this).queryAllShareRecord();
        Log.e(LOG_TAG, "shareRecordList.size()" + shareRecordList.size());
        Log.e(LOG_TAG, favoriteStoryList.size() + "");
        final List<MyGroup> lists = formateFav(favoriteStoryList);
        lvStar = (ExpandableListView) findViewById(R.id.lv_myStar);
        lvStar.setAdapter(new ExpandableLVAdapter(lists, this));
        lvStar.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                FavoriteStory story = lists.get(groupPosition).getChildList().get(childPosition);
                int storyId = Integer.valueOf(story.getStoryId());
                Intent intent = new Intent(CustomizeActivity.this, StoryActivity.class);
                intent.putExtra(Constant.STORY_ID, storyId);
                startActivity(intent);
                return true;
            }
        });
        lvStar.setGroupIndicator(getResources().getDrawable(R.drawable.ic_expanable_lv_group_indictor));
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
            List<FavoriteStory> children = new ArrayList<>();
            Log.e(LOG_TAG, story.toString());
//            Child child = new Child(story.getTitle(), story.getImgUrl(), Integer.valueOf(story.getSectionId()));
            children.add(story);
            MyGroup myGroup = new MyGroup(story.getStaredTimestamp(), children);
            lists.add(myGroup);
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
