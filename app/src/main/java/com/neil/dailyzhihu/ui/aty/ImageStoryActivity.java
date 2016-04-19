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
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;

/**
 * Created by Neil on 2016/4/19.
 */
public class ImageStoryActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, PlatformActionListener {
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
                    Toast.makeText(ImageStoryActivity.this, "新浪微博分享", Toast.LENGTH_SHORT).show();
                    sinaBlogShare(0);
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

    private void sinaBlogShare(int i) {
        ShareSDK.initSDK(this);
        //2、设置分享内容
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setText("我是分享文本，啦啦啦~http://uestcbmi.com/"); //分享文本
        sp.setImageUrl("http://7sby7r.com1.z0.glb.clouddn.com/CYSJ_02.jpg");//网络图片rul

        //3、非常重要：获取平台对象
        Platform sinaWeibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        sinaWeibo.setPlatformActionListener(ImageStoryActivity.this); // 设置分享事件回调
        // 执行分享
        sinaWeibo.share(sp);
    }

    private void sinaBlogShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题：微信、QQ（新浪微博不需要标题）
        oks.setTitle("我是分享标题");  //最多30个字符

        // text是分享文本：所有平台都需要这个字段
        oks.setText("我是分享文本，啦啦啦~http://uestcbmi.com/");  //最多40个字符

        // imagePath是图片的本地路径：除Linked-In以外的平台都支持此参数
        //oks.setImagePath(Environment.getExternalStorageDirectory() + "/meinv.jpg");//确保SDcard下面存在此张图片

        //网络图片的url：所有平台
        oks.setImageUrl("http://7sby7r.com1.z0.glb.clouddn.com/CYSJ_02.jpg");//网络图片rul

        // url：仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");   //网友点进链接后，可以看到分享的详情

        // Url：仅在QQ空间使用
        oks.setTitleUrl("http://www.baidu.com");  //网友点进链接后，可以看到分享的详情

        // 启动分享GUI
        oks.show(this);
    }

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

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Toast.makeText(getApplicationContext(), "微博分享成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }
}
