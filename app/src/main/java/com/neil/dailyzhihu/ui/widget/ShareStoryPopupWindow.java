package com.neil.dailyzhihu.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.api.API;
import com.neil.dailyzhihu.ui.story.ShareActivity;
import com.neil.dailyzhihu.api.AtyExtraKeyConstant;
import com.neil.dailyzhihu.utils.AppUtil;
import com.neil.dailyzhihu.utils.storage.StorageOperatingHelper;
import com.neil.dailyzhihu.utils.share.QRCodeUtil;

/**
 * 作者：Neil on 2017/3/4 12:36.
 * 邮箱：cn.neillee@gmail.com
 */

public class ShareStoryPopupWindow extends PopupWindow {
    private int mWidth;
    private int mHeight;
    private View mContentView;

    private ListView mListView;
    private Context mContext;
    private String mStoryId;
    private String mStoryTitle;
    private String mStoryImageUrl;
    private String mStoryHtmlContent;

    public ShareStoryPopupWindow(Context context, String storyId, String storyTitle,
                                 String storyImageUrl, String storyHtmlContent) {
        super(context);
        this.mContext = context;
        this.mStoryHtmlContent = storyHtmlContent;
        this.mStoryId = storyId;
        this.mStoryImageUrl = storyImageUrl;
        this.mStoryTitle = storyTitle;

        calWidthAndHeight(context);
        setWidth(mWidth);
        setHeight(mHeight);
        mContentView = LayoutInflater.from(context).inflate(R.layout.popupwidow_story_share, null);
        setContentView(mContentView);
        setFocusable(true);
        setTouchable(true);
        setTouchable(true);
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //点击PopupWindow以外区域时PopupWindow消失
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    dismiss();
                }
                return false;
            }
        });
        initListView(context);
    }

    //初始化PopupWindow的listview
    private void initListView(final Context context) {
        ShareListAdapter adapter = new ShareListAdapter(context);
        mListView = (ListView) mContentView.findViewById(R.id.lv_share);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                shareEvents(position);
                Toast.makeText(context, "分享", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void shareEvents(int position) {
        Intent intent;String text;
        switch (position) {
            case 0:
            case 1:
            case 2:
            case 3:// 分享
                intent = new Intent(mContext, ShareActivity.class);
                intent.putExtra(AtyExtraKeyConstant.STORY_SHARE_PLATFORM, position);
                intent.putExtra(AtyExtraKeyConstant.STORY_SHARE_HTML_CONTENT, mStoryHtmlContent);
                intent.putExtra(AtyExtraKeyConstant.STORY_TITLE, mStoryTitle);
                intent.putExtra(AtyExtraKeyConstant.STORY_ID, mStoryId);
                intent.putExtra(AtyExtraKeyConstant.STORY_IMAGE_URL, mStoryImageUrl);
                mContext.startActivity(intent);
                break;
            case 4:// 复制到剪贴板
                text = mStoryTitle+" via "+ API.STORY_PREFIX + mStoryId + "\n(powered by DailyZHIHU)\n";
                AppUtil.copyText2Clipboard(mContext,text);
                break;
            case 5:// 二维码
                // 生成二维码
                final String shareUrl = "http://daily.zhihu.com/story/" + mStoryId;

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_qr_display, null, false);
                ImageView ivQR = (ImageView) view.findViewById(R.id.iv_qrDisplay);
                AlertDialog dialog = builder.setView(view).setTitle("二维码分享").create();
                dialog.show();

                ViewGroup.LayoutParams pm = ivQR.getLayoutParams();
                final Bitmap bm = QRCodeUtil.getQRBitmap(shareUrl, pm.width, pm.height, null);
                ivQR.setImageBitmap(bm);

                ivQR.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        String path = StorageOperatingHelper.savingBitmap2SD(mContext, bm, shareUrl);
                        if (!TextUtils.isEmpty(path))
                            Toast.makeText(mContext, "保存成功" + path, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                break;
            case 6:
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
                text = mStoryTitle+" via "+"http://daily.zhihu.com/story/" + mStoryId + "\n(powered by DailyZHIHU)\n";
                intent.putExtra(Intent.EXTRA_TEXT, text);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(Intent.createChooser(intent, "分享到..."));
                break;
        }
        this.dismiss();
    }

    /**
     * 设置PopupWindow的大小
     *
     * @param context
     */
    private void calWidthAndHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);

        mWidth = metrics.widthPixels;
        mHeight = (int) (metrics.heightPixels * 0.7);
    }

    private class ShareListAdapter extends BaseAdapter {
        private Context mContext;

        private String[] shareNameList = {"微信、QQ分享", "新浪微博分享", "墙内软件分享",
                "墙外软件分享", "复制至剪贴板", "二维码", "更多"};
        private int[] resIdList = {R.drawable.ic_menu_tencent, R.drawable.ic_menu_sina_weibo,
                R.drawable.ic_menu_china, R.drawable.ic_menu_usa, R.drawable.ic_menu_copy,
                R.drawable.ic_menu_qr_code, R.drawable.ic_menu_more};

        ShareListAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return shareNameList.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_story_share, parent, false);
            }
            ViewHolder vh = new ViewHolder(convertView);
            vh.tv.setText(shareNameList[position]);
            vh.iv.setImageResource(resIdList[position]);
            return convertView;
        }

        class ViewHolder {
            LinearLayout ll;
            TextView tv;
            ImageView iv;

            ViewHolder(View view) {
                ll = (LinearLayout) view.findViewById(R.id.ll_story_share);
                iv = (ImageView) view.findViewById(R.id.iv_story_share);
                tv = (TextView) view.findViewById(R.id.tv_story_share);
            }
        }
    }
}
