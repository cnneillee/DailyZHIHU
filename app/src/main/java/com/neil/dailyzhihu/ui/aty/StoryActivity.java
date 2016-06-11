package com.neil.dailyzhihu.ui.aty;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.neil.dailyzhihu.Constant;
import com.neil.dailyzhihu.OnContentLoadingFinishedListener;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.adapter.CommentPagerAdapter;
import com.neil.dailyzhihu.adapter.LongCommentListAdapter;
import com.neil.dailyzhihu.bean.LongComment;
import com.neil.dailyzhihu.bean.ShareRecord;
import com.neil.dailyzhihu.bean.StarRecord;
import com.neil.dailyzhihu.bean.cleanlayer.CleanDetailStory;
import com.neil.dailyzhihu.bean.orignallayer.StoryContent;
import com.neil.dailyzhihu.ui.widget.BaseActivity;
import com.neil.dailyzhihu.ui.widget.CommentAlertDialog;
import com.neil.dailyzhihu.ui.widget.StoryFabMenuPopupwindow;
import com.neil.dailyzhihu.ui.widget.ShareMenuPopupWindow;
import com.neil.dailyzhihu.utils.GsonDecoder;
import com.neil.dailyzhihu.utils.ImageExternalDirectoryUtil;
import com.neil.dailyzhihu.utils.LoaderFactory;
import com.neil.dailyzhihu.utils.ShareHelper;
import com.neil.dailyzhihu.utils.StorageOperatingHelper;
import com.neil.dailyzhihu.utils.db.Story;
import com.neil.dailyzhihu.utils.db.catalog.a.DBFactory;
import com.neil.dailyzhihu.utils.img.ImageLoaderWrapper;
import com.neil.dailyzhihu.utils.share.QQShare;
import com.neil.dailyzhihu.utils.share.QRCodeUtil;
import com.neil.dailyzhihu.utils.share.SinaShare;
import com.neil.dailyzhihu.utils.share.Util;
import com.neil.dailyzhihu.utils.share.WechatShare;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

public class StoryActivity extends BaseActivity implements ObservableScrollViewCallbacks, AdapterView.OnItemClickListener, PlatformActionListener, View.OnClickListener {
    private static final float MAX_TEXT_SCALE_DELTA = 0.3f;
    @Bind(R.id.image)
    ImageView mImageView;
    @Bind(R.id.overlay)
    View mOverlayView;
    @Bind(R.id.tv_scroll_to_top)
    TextView mBottomTv;
    @Bind(R.id.scroll)
    ObservableScrollView mScrollView;
    @Bind(R.id.title)
    TextView mTitleView;
    @Bind(R.id.fab)
    FloatingActionButton mFab;
    @Bind(R.id.tvContent)
    TextView mTvContent;
    @Bind(R.id.main)
    FrameLayout mMainLayout;

    private int mActionBarSize;
    private int mFlexibleSpaceShowFabOffset;
    private int mFlexibleSpaceImageHeight;
    private int mFabMargin;
    private boolean mFabIsShown;

    private String storyPath;

    private static final String LOG_TAG = StoryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        ButterKnife.bind(this);

        initObserableListViewUIParams();
        initEntranceIntentData();
    }

    private void initEntranceIntentData() {
        int storyId = getExtrasStoryId();
        if (storyId > 0)
            fillingContent(storyId);
    }

    private void initObserableListViewUIParams() {
        mFlexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
        mFlexibleSpaceShowFabOffset = getResources().getDimensionPixelSize(R.dimen.flexible_space_show_fab_offset);
        mActionBarSize = getActionBarSize();
        mTitleView.setText(getTitle());
        setTitle(null);
        mScrollView.setScrollViewCallbacks(this);
        mBottomTv.setOnClickListener(this);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildingCommentPopupWindow();
            }
        });
        mFabMargin = getResources().getDimensionPixelSize(R.dimen.margin_standard);
        ViewHelper.setScaleX(mFab, 0);
        ViewHelper.setScaleY(mFab, 0);
        ScrollUtils.addOnGlobalLayoutListener(mScrollView, new Runnable() {
            @Override
            public void run() {
                //正好显现出照片
                onScrollChanged(0, false, false);
            }
        });
    }

    private PopupWindow mFabPW = null;
    private PopupWindow.OnDismissListener mOnDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            mMainLayout.setForeground(null);
        }
    };

    //构造并显示mFab点击后弹出的popupwindow
    private void buildingCommentPopupWindow() {
        if (mFabPW == null) {
            mFabPW = new StoryFabMenuPopupwindow(this, new String[]{"查看评论", "收藏", "分享", "二维码"},
                    new int[]{R.drawable.ic_comments, R.drawable.ic_star_menu, R.drawable.ic_share_menu, R.drawable.ic_qr_code}, this);
            mFabPW.setOnDismissListener(mOnDismissListener);
        }
        mFabPW.showAsDropDown(mFab);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x9A000000);
        mMainLayout.setForeground(dw);
    }

    private int getExtrasStoryId() {
        int storyId = -1;
        if (getIntent().getExtras() != null) {
            storyId = getIntent().getIntExtra(Constant.STORY_ID, 0);
        }
        return storyId;
    }

    private StoryContent story;

    private boolean has = false;

    private void fillingContent(final int storyId) {
        if (loadDB(storyId)) return;
        //api接口下载
        fillingContentUsingAPI(storyId);
    }

    private boolean loadDB(int storyId) {
        //本地数据库查询
        CleanDetailStory cleanDetailStory = DBFactory.getIDBDetailStoryTabledao(this).queryDetailStoryById(storyId);
        if (cleanDetailStory == null) return false;
        mTvContent.setText(Html.fromHtml(cleanDetailStory.getBody()));
        mTitleView.setText(cleanDetailStory.getTitle());
        mTvContent.setVisibility(View.VISIBLE);
        Bitmap bm = ImageExternalDirectoryUtil.getBitmap(StoryActivity.this, storyId);
        if (bm == null) {
            ImageLoaderWrapper loader = LoaderFactory.getImageLoader();
            loader.displayImage(mImageView, cleanDetailStory.getImage(), null, null);
        } else {
            mImageView.setImageBitmap(bm);
            Log.e(LOG_TAG, "BITMAP LOADED FROM SD CARD");
        }
        return true;
    }

    private boolean hasInsert = false;

    private void fillingContentUsingAPI(final int storyId) {
        LoaderFactory.getContentLoader().loadContent(Constant.STORY_HEAD + storyId, new OnContentLoadingFinishedListener() {
            @Override
            public void onFinish(String content) {
                //TODO 在较为特殊的情况下，知乎日报可能将某个主题日报的站外文章推送至知乎日报首页。type=0正常，type特殊情况
                story = GsonDecoder.getDecoder().decoding(content, StoryContent.class);
                Bitmap bm = ImageExternalDirectoryUtil.getBitmap(StoryActivity.this, storyId);
                if (bm == null) {
                    ImageLoaderWrapper loader = LoaderFactory.getImageLoader();
                    loader.displayImage(mImageView, story.getImage(), null, null);
                } else {
                    mImageView.setImageBitmap(bm);
                    Log.e(LOG_TAG, "BITMAP LOADED FROM SD CARD");
                }
                String body = story.getBody();
                if (body != null)
                    mTvContent.setText(Html.fromHtml(body));
                String storyTtitle = story.getTitle();
                mTitleView.setText(storyTtitle);
                if (!hasInsert) {
                    //将下载后的数据写入数据库中
//                    writeStoryIntoDB(story);
                    hasInsert = true;
                }
            }
        });
    }

//    private void writeStoryIntoDB(StoryContent story) {
//        int resultCode = (int) StoryDBFactory.getInstance(this).addStory(StoryDBFactory.convertStoryContent2DBStory(story));
//        if (resultCode > 0)
//            Toast.makeText(this, "缓存成功", Toast.LENGTH_SHORT).show();
//    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        // Translate overlay and image
        float flexibleRange = mFlexibleSpaceImageHeight - mActionBarSize;
        int minOverlayTransitionY = mActionBarSize - mOverlayView.getHeight();
        ViewHelper.setTranslationY(mOverlayView, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
        ViewHelper.setTranslationY(mImageView, ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0));

        // Change alpha of overlay
        ViewHelper.setAlpha(mOverlayView, ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));

        // Scale title text
        float scale = 1 + ScrollUtils.getFloat((flexibleRange - scrollY) / flexibleRange, 0, MAX_TEXT_SCALE_DELTA);
        ViewHelper.setPivotX(mTitleView, 0);
        ViewHelper.setPivotY(mTitleView, 0);
        ViewHelper.setScaleX(mTitleView, scale);
        ViewHelper.setScaleY(mTitleView, scale);

        // Translate title text
        int maxTitleTranslationY = (int) (mFlexibleSpaceImageHeight - mTitleView.getHeight() * scale);
        int titleTranslationY = maxTitleTranslationY - scrollY;
        ViewHelper.setTranslationY(mTitleView, titleTranslationY);

        // Translate FAB
        int maxFabTranslationY = mFlexibleSpaceImageHeight - mFab.getHeight() / 2;
        float fabTranslationY = ScrollUtils.getFloat(
                -scrollY + mFlexibleSpaceImageHeight - mFab.getHeight() / 2,
                mActionBarSize - mFab.getHeight() / 2,
                maxFabTranslationY);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            // On pre-honeycomb, ViewHelper.setTranslationX/Y does not set margin,
            // which causes FAB's OnClickListener not working.
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFab.getLayoutParams();
            lp.leftMargin = mOverlayView.getWidth() - mFabMargin - mFab.getWidth();
            lp.topMargin = (int) fabTranslationY;
            mFab.requestLayout();
        } else {
            ViewHelper.setTranslationX(mFab, mOverlayView.getWidth() - mFabMargin - mFab.getWidth());
            ViewHelper.setTranslationY(mFab, fabTranslationY);
        }

        // Show/hide FAB
        if (fabTranslationY < mFlexibleSpaceShowFabOffset) {
            hideFab();
        } else {
            showFab();
        }
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    private void showFab() {
        if (!mFabIsShown) {
            ViewPropertyAnimator.animate(mFab).cancel();
            ViewPropertyAnimator.animate(mFab).scaleX(1).scaleY(1).setDuration(200).start();
            mFabIsShown = true;
        }
    }

    private void hideFab() {
        if (mFabIsShown) {
            ViewPropertyAnimator.animate(mFab).cancel();
            ViewPropertyAnimator.animate(mFab).scaleX(0).scaleY(0).setDuration(200).start();
            mFabIsShown = false;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0://查看评论
                Toast.makeText(StoryActivity.this, "查看评论", Toast.LENGTH_SHORT).show();
                viewComment();
                break;
            case 1://收藏
                Toast.makeText(StoryActivity.this, "收藏", Toast.LENGTH_SHORT).show();
                starStory();
                break;
            case 2://分享
                Toast.makeText(StoryActivity.this, "分享", Toast.LENGTH_SHORT).show();
                showShareModule();
                break;
            case 3://二维码
                Toast.makeText(StoryActivity.this, "生成二维码", Toast.LENGTH_SHORT).show();
                makingQRCode();
                ShareRecord shareRecord = new ShareRecord(story.getId(), System.currentTimeMillis(), "NONE", "QRCode", "20160427");
                int resultCode = (int) DBFactory.getsIDBShareRecordDetailStoryTabledao(this).addShareRecord(shareRecord);
                if (resultCode > 0)
                    Toast.makeText(StoryActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void starStory() {
        if (story != null) {
            StarRecord starRecord = new StarRecord(story.getId(), "20160427", System.currentTimeMillis(), "default");
            int resultCode = (int) DBFactory.getIIDBStarRecordDetailStoryTabledao(this).addStarRecord(starRecord);
            if (resultCode > 0)
                Toast.makeText(StoryActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
            else if (resultCode == 0)
                Toast.makeText(StoryActivity.this, "已存在", Toast.LENGTH_SHORT).show();
        }
    }

    private void viewComment() {
        if (story == null)
            return;
        //加载页卡
        List<View> views = loadingViewPagerCard();
        CommentAlertDialog commentAlertDialog = new CommentAlertDialog(this, true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        }, new CommentPagerAdapter(views), null);
        ViewPager vp = commentAlertDialog.getVp();
        loadingComment(views, vp);
    }

    private void makingQRCode() {
        final String shareUrl = story.getShare_url();
        ImageView ivQR = buildingQRDisplayDilalog();
        ViewGroup.LayoutParams pm = ivQR.getLayoutParams();
        final Bitmap bm = QRCodeUtil.getQRBitmap(shareUrl, pm.width, pm.height, null);
        ivQR.setImageBitmap(bm);
        ivQR.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String path = StorageOperatingHelper.savingBitmap2SD(StoryActivity.this, bm, shareUrl);
                if (!TextUtils.isEmpty(path))
                    Toast.makeText(StoryActivity.this, "保存成功" + path, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private ImageView buildingQRDisplayDilalog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(StoryActivity.this);
        View view = LayoutInflater.from(StoryActivity.this).
                inflate(R.layout.dialog_qr_display, null, false);
        final ImageView ivQR = (ImageView) view.findViewById(R.id.iv_qrDisplay);
        AlertDialog dialog = builder.setView(view).setTitle("二维码分享").create();
        dialog.show();
        Log.e(LOG_TAG, ivQR.getWidth() + "widthPx," + ivQR.getHeight() + "heightPx");
        return ivQR;
    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String title = story.getTitle();
            String shareUrl = story.getShare_url();
            String img = story.getImage();
            String text = title + "\tmore? via zhihuDaily--->\t" + shareUrl;
            switch (position) {
                case 0://生成图片
                    Intent intent = new Intent(StoryActivity.this, ImageStoryActivity.class);
                    intent.putExtra(Constant.STORY_BODY, story.getBody());
                    intent.putExtra(Constant.STORY_ID, story.getId());
                    startActivity(intent);
                    break;
                case 1://微信好友
                    WechatShare.wechatShareText(StoryActivity.this, StoryActivity.this, title, text, Util.WECHAT_FRIEND);
                    break;
                case 2://票圈
                    WechatShare.wechatShareText(StoryActivity.this, StoryActivity.this, title, text, Util.WECHAT_MOMENTS);
                    break;
                case 3://空间
                    QQShare.qqShareLink(StoryActivity.this, StoryActivity.this, title, shareUrl, text, "", img, Util.QZONE_NAME);
                    break;
                case 4://QQ
                    QQShare.qqShareLink(StoryActivity.this, StoryActivity.this, title, shareUrl, text, "", img, Util.QQ_NAME);
                    break;
                case 5://新浪微博
                    SinaShare.sinaShareLink(StoryActivity.this, StoryActivity.this, text, "", img, shareUrl);
                    break;
                case 6://复制链接
                    ShareHelper.saveToClipboard(shareUrl, StoryActivity.this);
                    Toast.makeText(StoryActivity.this, "成功复制到剪贴板", Toast.LENGTH_SHORT).show();
                    break;
                case 7://更多
                    String storyText = makeShareText();
                    if (storyText == null)
                        return;
                    Toast.makeText(StoryActivity.this, "更多分享", Toast.LENGTH_SHORT).show();
                    ShareHelper.orignalMsgShare(StoryActivity.this, "StoryActivity", storyText, storyText, null);
                    ShareRecord shareRecord = new ShareRecord(story.getId(), System.currentTimeMillis(), "UNKNOWN", "More-Link", "20160427");
                    int resultCode = (int) DBFactory.getsIDBShareRecordDetailStoryTabledao(StoryActivity.this).addShareRecord(shareRecord);
                    if (resultCode > 0)
                        Toast.makeText(StoryActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        private String makeShareText() {
            if (story != null)
                return story.getTitle() + Constant.STORY_HEAD + story.getId() + "\nvia DailyZHIHU";
            return null;
        }
    };

    private void showShareModule() {
        ShareMenuPopupWindow popupWindow = new ShareMenuPopupWindow(this, mOnItemClickListener);
        popupWindow.showAtLocation(mMainLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
    }

    public void share(StoryContent story) {
        if (story == null)
            return;
        ShareHelper.onKeyShareText(StoryActivity.this, story.getTitle(), story.getTitle() + "via zhihuDaily" + story.getShare_url(), story.getImage());
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        ShareRecord shareRecord = new ShareRecord(story.getId(), System.currentTimeMillis(), platform.getName(), "SDKShare-Link", "20160427");
        int resultCode = (int) DBFactory.getsIDBShareRecordDetailStoryTabledao(this).addShareRecord(shareRecord);
        if (resultCode > 0)
            Toast.makeText(StoryActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }

    @Override
    public void onClick(View v) {
        mScrollView.scrollVerticallyTo(0);
    }

    private void loadingComment(final List<View> views, final ViewPager vp) {
        final String tail = "/long-comments";
        final String tailShort = "/short-comments";
        final String storyId = story.getId() + "";
        LoaderFactory.getContentLoader().loadContent(Constant.COMMENT_HEAD + storyId + tail, new OnContentLoadingFinishedListener() {
            @Override
            public void onFinish(String content) {
                LongComment longComment = GsonDecoder.getDecoder().decoding(content, LongComment.class);
                List<LongComment.CommentsBean> mDatas = longComment.getComments();
                if (views.get(0) == null) {
                    return;
                }
                View view = views.get(0);
                ListView lv = (ListView) view.findViewById(R.id.lv_comment);
                TextView tv = (TextView) view.findViewById(R.id.commentType);
                tv.setText("长评论");
                lv.setAdapter(new LongCommentListAdapter(StoryActivity.this, mDatas));
                LoaderFactory.getContentLoader().loadContent(Constant.COMMENT_HEAD + storyId + tailShort, new OnContentLoadingFinishedListener() {
                    @Override
                    public void onFinish(String content) {
                        LongComment longComment = (LongComment) GsonDecoder.getDecoder().decoding(content, LongComment.class);
                        List<LongComment.CommentsBean> mDatas = longComment.getComments();
                        if (views.get(1) == null) {
                            return;
                        }
                        View view = views.get(1);
                        TextView tv = (TextView) view.findViewById(R.id.commentType);
                        tv.setText("短评论");
                        ListView lv = (ListView) view.findViewById(R.id.lv_comment);
                        if (lv == null) {
                            return;
                        }
                        lv.setAdapter(new LongCommentListAdapter(StoryActivity.this, mDatas));
                        vp.setAdapter(new CommentPagerAdapter(views));
                    }
                });
            }
        });
    }

    //加载页卡
    private List<View> loadingViewPagerCard() {
        List<View> views = new ArrayList<>();
        View view = getLayoutInflater().inflate(R.layout.vp_item_comment, null);
        views.add(view);
        view = getLayoutInflater().inflate(R.layout.vp_item_comment, null);
        views.add(view);
        return views;
    }
}