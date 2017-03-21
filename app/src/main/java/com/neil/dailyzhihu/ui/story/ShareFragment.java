package com.neil.dailyzhihu.ui.story;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.neil.dailyzhihu.utils.SnackbarUtil;
import com.neil.dailyzhihu.utils.share.PlatformInfoUtils;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.listener.BitmapLoadCallback;
import com.neil.dailyzhihu.utils.share.ShareSDKUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

/**
 * 作者：Neil on 2017/3/4 14:12.
 * 邮箱：cn.neillee@gmail.com
 */

public class ShareFragment extends Fragment {

    private static final String LOG_TAG = ShareFragment.class.getSimpleName();
    @Bind(R.id.iv_story_image)
    ImageView mIvStory;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.rg_share_type)
    RadioGroup mRgShareType;
    @Bind(R.id.gv_share)
    GridView mGvShare;
    @Bind(R.id.iv_share_type)
    ImageView mIvShareType;
    @Bind(R.id.tv_share_content)
    TextView mTvShareContent;

    private String mStoryTitle;
    private String mShareText;
    private String mImageUrl;
    private String mStoryId;
    private String mStoryImagePath;

    /*0:腾讯系，1：墙内，2：墙外，3：新浪微博*/
    private List<Map<String, Object>> mShareData;

    private static int mSeriesType;

    private View mRootView;
    private Context mContext;

    private BitmapLoadCallback mCallback;

    private PlatformActionListener mActionListener = new PlatformActionListener() {

        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            SnackbarUtil.ShortSnackbar(mRootView, platform.getName() + "分享成功", SnackbarUtil.Info).show();
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            SnackbarUtil.ShortSnackbar(mRootView, platform.getName() + "分享失败", SnackbarUtil.Alert).show();
        }

        @Override
        public void onCancel(Platform platform, int i) {
            SnackbarUtil.ShortSnackbar(mRootView, platform.getName() + "分享被取消", SnackbarUtil.Info).show();
        }
    };
    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (mShareData == null) return;
            String platformType = (String) mShareData.get(position).get("type");
            Log.e(LOG_TAG, "mShareData is not null" + "\tcontext is " + mContext);
            int checkedId = mRgShareType.getCheckedRadioButtonId();
            if (checkedId == R.id.rb_image_share) {// 图片分享
                ShareSDKUtil.shareImage(mContext,mActionListener, platformType, mStoryTitle, mShareText, mImageUrl);
            } else {// 文字分享
                ShareSDKUtil.shareText(mContext,mActionListener,  platformType, mStoryTitle, mShareText);
            }
        }
    };

    public static ShareFragment newInstance(int seriesType) {
        Bundle args = new Bundle();
        mSeriesType = seriesType;
        ShareFragment fragment = new ShareFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        mShareData = PlatformInfoUtils.getShareData(mSeriesType);
        SimpleAdapter adapter = new SimpleAdapter(mContext, mShareData, R.layout.item_gv_share,
                new String[]{"text", "drawable"}, new int[]{R.id.tv_share_platform, R.id.iv_share_platform});
        mGvShare.setAdapter(adapter);
        mGvShare.setNumColumns(mShareData.size());
        mGvShare.setOnItemClickListener(mOnItemClickListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_share, container, false);
        ButterKnife.bind(this, mRootView);

        mCallback.bitmapDisplay(mIvStory);
        mTvTitle.setText(mStoryTitle);
        String shareText = mStoryTitle + " via " + "http://daily.zhihu.com/story/" + mStoryId + "\n(powered by DailyZHIHU)\n";
        mTvShareContent.setText(shareText);
        mRgShareType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb_image_share) {
                    mTvShareContent.setVisibility(View.GONE);
                    mIvStory.setVisibility(View.VISIBLE);
                    mIvShareType.setImageResource(R.drawable.ic_content_image);
                } else {
                    mTvShareContent.setVisibility(View.VISIBLE);
                    mIvStory.setVisibility(View.GONE);
                    mIvShareType.setImageResource(R.drawable.ic_content_text);
                }
            }
        });
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (BitmapLoadCallback) context;
    }

    public void setStoryTitle(String storyTitle) {
        mStoryTitle = storyTitle;
    }

    public void setShareText(String shareText) {
        mShareText = shareText;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public void setStoryId(String storyId) {
        mStoryId = storyId;
    }

    public void setStoryImagePath(String storyImagePath) {
        mStoryImagePath = storyImagePath;
    }
}
