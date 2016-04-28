package com.neil.dailyzhihu.ui.aty;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.bean.ShareRecord;
import com.neil.dailyzhihu.ui.widget.ShareMenuPopupWindow;
import com.neil.dailyzhihu.utils.ShareHelper;
import com.neil.dailyzhihu.utils.StorageOperatingHelper;
import com.neil.dailyzhihu.utils.db.catalog.a.DBFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * Created by Neil on 2016/4/19.
 */
public class ImageStoryActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, PlatformActionListener {
    private static final String LOG_TAG = ImageStoryActivity.class.getName();
    @Bind(R.id.rg_img_story_theme)
    RadioGroup mRgImgStoryTheme;
    @Bind(R.id.tv_content)
    TextView mTvContent;
    @Bind(R.id.tv_share)
    TextView mTvShare;
    @Bind(R.id.tv_save)
    TextView mTvSave;

    private final String msgContent = "我是分享文本，啦啦啦~http://uestcbmi.com/";
    private final String imgUrl = "http://7sby7r.com1.z0.glb.clouddn.com/CYSJ_02.jpg";
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    private int storyId = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_story);
        ButterKnife.bind(this);
        setDefaultStyle();
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("生成图片");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageStoryActivity.this.finish();
            }
        });
        mRgImgStoryTheme.setOnCheckedChangeListener(this);
        mTvShare.setOnClickListener(this);
        mTvSave.setOnClickListener(this);

        getExtra();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private boolean isWhite = false;

    private void switchSavingViewStyle() {
        if (isWhite) {
            mTvContent.setTextColor(getResources().getColor(R.color.white));
            mTvContent.setBackgroundColor(getResources().getColor(R.color.asGray));
        } else {
            mTvContent.setBackgroundColor(getResources().getColor(R.color.ivoryWhite));
            mTvContent.setTextColor(getResources().getColor(R.color.black));
        }
        isWhite = !isWhite;
    }

    private void setDefaultStyle() {
        isWhite = false;
        switchSavingViewStyle();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_img_story_theme_white:
                Toast.makeText(this, "优雅白", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rb_img_story_theme_black:
                Toast.makeText(this, "深邃黑", Toast.LENGTH_SHORT).show();
                break;
        }
        switchSavingViewStyle();
    }

    private String absoluteImagePath;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_share:
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
                absoluteImagePath = saveView2SDCard(mTvContent, msgContent);
                Log.e(LOG_TAG, "absoluteImagePath" + absoluteImagePath);
                showSharePopupModule();
                break;
            case R.id.tv_save:
                absoluteImagePath = saveView2SDCard(mTvContent, msgContent);
                if (absoluteImagePath != null)
                    Toast.makeText(this, "保存成功 " + absoluteImagePath, Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0://微信好友
                    //ShareHelper.onKeyShare(ImageStoryActivity.this, "Title", "text", absoluteImagePath, imgUrl);
                    ShareHelper.weChatFriendShare(ImageStoryActivity.this, "TITLE", "TEXT", absoluteImagePath, ImageStoryActivity.this);
                    Toast.makeText(ImageStoryActivity.this, "分享给 微信好友！此功能待开发", Toast.LENGTH_SHORT).show();
                    break;
                case 1://票圈
                    Toast.makeText(ImageStoryActivity.this, "分享到 朋友圈！此功能待开发", Toast.LENGTH_SHORT).show();
                    ShareHelper.weChatCircle(ImageStoryActivity.this, "TITLE", "TEXT", absoluteImagePath, ImageStoryActivity.this);
                    break;
                case 2://新浪微博
                    ShareHelper.sinaWeiboShare(ImageStoryActivity.this, msgContent, absoluteImagePath, ImageStoryActivity.this);
                    Toast.makeText(ImageStoryActivity.this, "新浪微博分享", Toast.LENGTH_SHORT).show();
                    break;
                case 3://QQ好友\图片
                    ShareHelper.qqShare(ImageStoryActivity.this, null, absoluteImagePath, ImageStoryActivity.this);
                    Toast.makeText(ImageStoryActivity.this, "QQ分享", Toast.LENGTH_SHORT).show();
                    break;
                case 4://更多
                    Toast.makeText(ImageStoryActivity.this, "更多分享", Toast.LENGTH_SHORT).show();
                    String appName = ImageStoryActivity.this.getApplicationInfo().getClass().getSimpleName();
                    ShareHelper.orignalMsgShare(ImageStoryActivity.this, appName, msgContent, "via DailyZHIHU", absoluteImagePath);
                    ShareRecord shareRecord = new ShareRecord(storyId, System.currentTimeMillis(), "NONE", "More-Picture", "20160427");
                    int resultCode = (int) DBFactory.getsIDBShareRecordDetailStoryTabledao(ImageStoryActivity.this).addShareRecord(shareRecord);
                    if (resultCode > 0)
                        Toast.makeText(ImageStoryActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void showSharePopupModule() {
        List<HashMap<String, Object>> mMenuData = getShareMenuPopupModuleData();
        ShareMenuPopupWindow popupWindow = new ShareMenuPopupWindow(this, mOnItemClickListener, mMenuData);
        //显示窗口//设置layout在PopupWindow中显示的位置
        popupWindow.showAtLocation(this.findViewById(R.id.main),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    //用于产生分享按钮中分享平台的GridViewItem
    private List<HashMap<String, Object>> getShareMenuPopupModuleData() {
        String[] menuShareStr = new String[]{"微信好友", "微信朋友圈", "新浪微博", "QQ好友", "更多"};
        int[] menuShareImg = new int[]{
                R.drawable.menu_share_wechat_item,
                R.drawable.menu_share_friendcircle_item,
                R.drawable.menu_share_sina_item,
                R.drawable.menu_share_qq_item,
                R.drawable.menu_share_more_item};
        List<HashMap<String, Object>> mMenuData = new ArrayList<>();
        for (int i = 0; i < menuShareImg.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("shareStr", menuShareStr[i]);
            map.put("shareImg", menuShareImg[i]);
            mMenuData.add(map);
        }
        return mMenuData;
    }

    private String saveView2SDCard(TextView tvContent, String imgName) {
        Bitmap bmConent = makingView2Bitmap(tvContent);
        return StorageOperatingHelper.savingBitmap2SD(this, bmConent, imgName);
    }

    private Bitmap makingView2Bitmap(View view) {
        if (view == null) {
            return null;
        }
        Bitmap screenshot;
        screenshot = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(screenshot);
        view.draw(c);
        return screenshot;
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        ShareRecord shareRecord = new ShareRecord(storyId, System.currentTimeMillis(), platform.getName(), "ShareSDK-Picture", "20160427");
        int resultCode = (int) DBFactory.getsIDBShareRecordDetailStoryTabledao(this).addShareRecord(shareRecord);
        if (resultCode > 0)
            Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "成功分享到 " + platform.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }

    public void getExtra() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String body = bundle.getString(Constant.STORY_BODY);
            storyId = bundle.getInt(Constant.STORY_ID);
            mTvContent.setText(Html.fromHtml(body));
        }
    }
}
