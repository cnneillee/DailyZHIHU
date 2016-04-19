package com.neil.dailyzhihu.ui.aty;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.ui.widget.ShareMenuPopupWindow;
import com.neil.dailyzhihu.utils.ShareHelper;
import com.neil.dailyzhihu.utils.StorageOperatingHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Neil on 2016/4/19.
 */
public class ImageStoryActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    @Bind(R.id.rg_img_story_theme)
    RadioGroup mRgImgStoryTheme;
    @Bind(R.id.tv_content)
    TextView mTvContent;
    @Bind(R.id.tv_share)
    TextView mTvShare;
    @Bind(R.id.tv_save)
    TextView mTvSave;

    private final String msgContent = "来自知乎-testMsgBody";
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_share:
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
                showShareModule();
                break;
            case R.id.tv_save:
                String absolutePath = saveView2SDCard(mTvContent, msgContent);
                if (absolutePath != null)
                    Toast.makeText(this, "保存成功 " + absolutePath, Toast.LENGTH_SHORT).show();
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
                    break;
                case 1://票圈
                    break;
                case 2://新浪微博
                    break;
                case 3://QQ好友
                    break;
                case 4://更多
                    Toast.makeText(ImageStoryActivity.this, "更多分享", Toast.LENGTH_SHORT).show();
                    shareViewImg(mTvContent, msgContent);
                    break;
            }
        }
    };

    private void showShareModule() {
        List<HashMap<String, Object>> mMenuData = getShareMenuModuleData();
        ShareMenuPopupWindow popupWindow = new ShareMenuPopupWindow(this, mOnItemClickListener, mMenuData);
        //显示窗口//设置layout在PopupWindow中显示的位置
        popupWindow.showAtLocation(this.findViewById(R.id.main),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private List<HashMap<String, Object>> getShareMenuModuleData() {
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

    private void shareViewImg(TextView tvContent, String imgName) {
        String imgUri = saveView2SDCard(tvContent, imgName);
        String appNmae = this.getApplicationInfo().getClass().getSimpleName();
        ShareHelper.shareMsg(this, appNmae, imgName, "via DailyZHIHU", imgUri);
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
}
