package com.neil.dailyzhihu.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.google.gson.Gson;
import com.neil.dailyzhihu.MyApplication;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.LongCommentsAdapter;
import com.neil.dailyzhihu.bean.LongComments;
import com.neil.dailyzhihu.bean.StoryContent;
import com.neil.dailyzhihu.bean.StoryExtra;
import com.neil.dailyzhihu.utils.ContentLoader;
import com.neil.dailyzhihu.utils.ImageLoader;
import com.neil.dailyzhihu.utils.UniversalLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Neil on 2016/3/23.
 */
public class StoryActivity extends AppCompatActivity implements View.OnClickListener,ObservableScrollViewCallbacks {
    @Bind(R.id.tv_content)
    WebView tvContent;
    @Bind(R.id.iv_img)
    ImageView ivImg;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_imgSource)
    TextView tvImgSource;
    @Bind(R.id.tv_storyExtra)
    TextView tvStoryExtra;
    @Bind(R.id.btn_getLongComments)
    FloatingActionButton btnGetLongComments;
    @Bind(R.id.btn_getShortComments)
    FloatingActionButton btnGetShortComments;
    @Bind(R.id.tvContent)
    TextView tvcontent;
    @Bind(R.id.osv)
    ObservableScrollView osv;
    @Bind(R.id.detailed_action_settings)
    FloatingActionButton detailedActionSettings;
    @Bind(R.id.detailed_multiple_actions)
    FloatingActionsMenuHidable detailedMultipleActions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_content);
        osv.setScrollViewCallbacks(this);
        ButterKnife.bind(this);
        int storyId = getExtras();
        if (storyId > 0)
            fillingContent(storyId);
    }

    private int getExtras() {
        int storyId = -1;
        if (getIntent().getExtras() != null) {
            storyId = getIntent().getIntExtra("STORY_ID", 0);
        }
        return storyId;
    }

    private int storyId = -1;

    private void fillingContent(final int storyId) {
        this.storyId = storyId;
        ContentLoader.loadString("http://news-at.zhihu.com/api/4/news/" + storyId, new ImageLoader.OnFinishListener() {
            @Override
            public void onFinish(Object s) {
                Gson gson = new Gson();
                StoryContent story = gson.fromJson((String) s, StoryContent.class);
                String body = story.getBody();

                //TODO 在较为特殊的情况下，知乎日报可能将某个主题日报的站外文章推送至知乎日报首页。
                //type=0正常，type特殊情况
                //TODO 图片加载
                MyApplication myApplication = (MyApplication) StoryActivity.this.getApplicationContext();
                UniversalLoader loader = myApplication.getUniversalLoader();
                loader.loadImage(StoryActivity.this, ivImg, story.getImage(), null);
//                ImageLoader.loadImage(ivImg, story.getImage(), null);
                tvTitle.setText(story.getTitle());
                tvImgSource.setText(story.getImage_source());
                //TODO 中文显示乱码
//                tvContent.loadData(body, "text/html", "utf-8");
                tvcontent.setText(Html.fromHtml(body));
                tvcontent.setVisibility(View.VISIBLE);
            }
        });
        ContentLoader.loadString("http://news-at.zhihu.com/api/4/story-extra/" + storyId, new ImageLoader.OnFinishListener() {
            @Override
            public void onFinish(Object s) {
                Gson gson = new Gson();
                StoryExtra storyExtra = gson.fromJson((String) s, StoryExtra.class);
                int longcomments = storyExtra.getLong_comments();
                int shortcomments = storyExtra.getShort_comments();
                int postReasons = storyExtra.getPost_reasons();
                int popularity = storyExtra.getPopularity();
                int normalComments = storyExtra.getNormal_comments();
                int comments = storyExtra.getComments();

                String str = "postReasons:" + postReasons +
                        " 正常评论数" + normalComments +
                        " 点赞: " + popularity +
                        " 长评论:" + longcomments +
                        "  短评论: " + shortcomments +
                        "  评论总数: " + comments;
                tvStoryExtra.setText(str);
            }
        });
        btnGetLongComments.setOnClickListener(this);
        btnGetShortComments.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = null;
        View view = null;
        AlertDialog dialog = null;
        final ListView lv = null;
        switch (v.getId()) {
            case R.id.btn_getLongComments:
                builder = new AlertDialog.Builder(StoryActivity.this);
                view = LayoutInflater.from(StoryActivity.this).
                        inflate(R.layout.view_long_comments, null, false);
                final ListView lv1 = (ListView) view.findViewById(R.id.lv_longComments);
                dialog = builder.setView(view).
                        setTitle("长评论").
                        setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();

                dialog.show();
                ContentLoader.loadString("http://news-at.zhihu.com/api/4/story/" + storyId + "/long-comments", new ImageLoader.OnFinishListener() {
                    @Override
                    public void onFinish(Object s) {
                        Gson gson = new Gson();
                        LongComments longComments = gson.fromJson((String) s, LongComments.class);
                        List<LongComments.CommentsBean> mDatas = longComments.getComments();
                        lv1.setAdapter(new LongCommentsAdapter(StoryActivity.this, mDatas));
                        Toast.makeText(StoryActivity.this, "lv:" + lv1.toString() + "adapter有:" + mDatas.size(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.btn_getShortComments:
                builder = new AlertDialog.Builder(StoryActivity.this);
                view = LayoutInflater.from(StoryActivity.this).
                        inflate(R.layout.view_long_comments, null, false);
                final ListView lv2 = (ListView) view.findViewById(R.id.lv_longComments);
                dialog = builder.setView(view).
                        setTitle("长评论").
                        setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();

                dialog.show();
                ContentLoader.loadString("http://news-at.zhihu.com/api/4/story/" + storyId + "/short-comments", new ImageLoader.OnFinishListener() {
                    @Override
                    public void onFinish(Object s) {
                        Gson gson = new Gson();
                        LongComments longComments = gson.fromJson((String) s, LongComments.class);
                        List<LongComments.CommentsBean> mDatas = longComments.getComments();
                        lv2.setAdapter(new LongCommentsAdapter(StoryActivity.this, mDatas));
                        Toast.makeText(StoryActivity.this, "lv:" + lv2.toString() + "adapter有:" + mDatas.size(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }
}
