package com.neil.dailyzhihu.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.bean.orignallayer.StoryContent;
import com.neil.dailyzhihu.utils.storage.StorageOperatingHelper;
import com.neil.dailyzhihu.utils.share.QRCodeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 作者：Neil on 2016/4/22 20:34.
 * 邮箱：cn.neillee@gmail.com
 */
public class StoryFabMenuPopupWindow extends PopupWindow {
    private static final String MENU_DRAWABLE_RES_ID = "drawable";
    private static final String MENU_STRING = "string";

    private Context mContext;
    private StoryContent mStoryContent;
    private FrameLayout mMainLayout;

//    private static final String LOG_TAG = StoryFabMenuPopupWindow.class.getSimpleName();

    private AdapterView.OnItemClickListener mListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0://查看评论
                    Toast.makeText(mContext, "查看评论", Toast.LENGTH_SHORT).show();
                    viewComment();
                    break;
                case 1://收藏
                    Toast.makeText(mContext, "收藏", Toast.LENGTH_SHORT).show();
                    // TODO starStory()
                    break;
                case 2://分享
                    Toast.makeText(mContext, "分享", Toast.LENGTH_SHORT).show();
                    showSharePopupWindow();
                    break;
                case 3://二维码
                    Toast.makeText(mContext, "生成二维码", Toast.LENGTH_SHORT).show();
                    makingQRCode();
                    Toast.makeText(mContext, "分享成功", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public StoryFabMenuPopupWindow(Context context, StoryContent story, FrameLayout mainLayout) {
        this.mContext = context;
        this.mStoryContent = story;
        this.mMainLayout = mainLayout;

        // Construct the PopupWindow view
        constructPopupWindowView();

        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x9A000000);
        mMainLayout.setForeground(dw);
    }

    // 构建 PopupWindow 的界面
    private void constructPopupWindowView() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_comments_menu, null);

        ListView lv = (ListView) contentView.findViewById(R.id.lv_pwmenu);

        final String[] menuItemsString = new String[]{"查看评论", "收藏", "分享", "二维码"};
        final int[] resIds = new int[]{R.drawable.ic_comments, R.drawable.ic_star_menu,
                R.drawable.ic_share_menu, R.drawable.ic_qr_code};
        List<Map<String, Object>> menuItems = new ArrayList<>();
        for (int i = 0; i < menuItemsString.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put(MENU_DRAWABLE_RES_ID, resIds[i]);
            map.put(MENU_STRING, menuItemsString[i]);
            menuItems.add(map);
        }

        lv.setAdapter(new SimpleAdapter(mContext, menuItems, R.layout.item_menu_popupwindow,
                new String[]{MENU_DRAWABLE_RES_ID, MENU_STRING}, new int[]{R.id.iv_menu, R.id.tv_menu}));

        lv.setOnItemClickListener(mListener);

        this.setContentView(contentView);

        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(android.support.design.R.anim.abc_grow_fade_in_from_bottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x9A000000);
        this.setBackgroundDrawable(dw);

        this.setOutsideTouchable(true);
    }

    // 显示分享PopupWindow
    private void showSharePopupWindow() {
        ShareMenuPopupWindow popupWindow = new ShareMenuPopupWindow(mContext, mStoryContent,mMainLayout);
        // 设置layout在PopupWindow中显示的位置
        popupWindow.showAtLocation(mMainLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        this.dismiss();
    }

    private void viewComment() {
        if (mStoryContent == null) return;
        CommentAlertDialog commentAlertDialog = new CommentAlertDialog(mContext, mStoryContent.getId() + "");
        commentAlertDialog.show();
        this.dismiss();
    }

    // 生成二维码
    private void makingQRCode() {
        final String shareUrl = mStoryContent.getShare_url();

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_qr_display, null, false);
        ImageView ivQR = (ImageView) view.findViewById(R.id.iv_qrDisplay);
        AlertDialog dialog = builder.setView(view).setTitle("二维码分享").create();
        dialog.show();

        this.dismiss();

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
    }
}
