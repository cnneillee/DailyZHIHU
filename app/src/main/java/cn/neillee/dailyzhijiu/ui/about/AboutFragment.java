package cn.neillee.dailyzhijiu.ui.about;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.neil.dailyzhijiu.BuildConfig;
import com.neil.dailyzhijiu.R;
import cn.neillee.dailyzhijiu.model.bean.orignal.UpdateInfoBean;
import cn.neillee.dailyzhijiu.model.http.api.API;
import cn.neillee.dailyzhijiu.model.http.api.AtyExtraKeyConstant;
import cn.neillee.dailyzhijiu.ui.aty.WebViewActivity;
import cn.neillee.dailyzhijiu.utils.AppUtil;
import cn.neillee.dailyzhijiu.utils.Formater;
import cn.neillee.dailyzhijiu.utils.SnackbarUtil;
import cn.neillee.dailyzhijiu.utils.update.UpdateAppUtils;


/**
 * 作者：Neil on 2017/2/26 01:34.
 * 邮箱：cn.neillee@gmail.com
 */

public class AboutFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    private Context mContext;

    private Preference mAppIntro;
    private Preference mCheckUpdate;
    private Preference mVersionIntro;
    private Preference mShareApp;
    private Preference mFeedback;
    private Preference mBlog;
    private Preference mGithub;
    private Preference mEmail;
    private Preference mOpensourceLicense;

    private boolean canUpdate = true;

    private static final String APP_INTRO = "key_app_intro";
    private static final String CHECK_UPDATE = "key_checkupdate";
    private static final String VERSION_INFO = "key_versioninfo";
    private static final String SHARE_APP = "key_shareapp";
    private static final String FEEDBACK = "key_feedback";
    private static final String BLOG = "key_blog";
    private static final String GITHUB = "key_github";
    private static final String EMAIL = "key_email";

    private static final String OPENSOURCE_LICENSE = "key_opensource_license";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.about);

        mContext = getActivity();
        initAllPreferences();
    }

    private void initAllPreferences() {
        mAppIntro = findPreference(APP_INTRO);
        mCheckUpdate = findPreference(CHECK_UPDATE);
        mVersionIntro = findPreference(VERSION_INFO);
        mShareApp = findPreference(SHARE_APP);
        mFeedback = findPreference(FEEDBACK);
        mBlog = findPreference(BLOG);
        mGithub = findPreference(GITHUB);
        mEmail = findPreference(EMAIL);
        mOpensourceLicense = findPreference(OPENSOURCE_LICENSE);

        mCheckUpdate.setSummary(AppUtil.getVersionInfo(mContext));

        mAppIntro.setOnPreferenceClickListener(this);
        mCheckUpdate.setOnPreferenceClickListener(this);
        mVersionIntro.setOnPreferenceClickListener(this);
        mFeedback.setOnPreferenceClickListener(this);
        mShareApp.setOnPreferenceClickListener(this);
        mBlog.setOnPreferenceClickListener(this);
        mGithub.setOnPreferenceClickListener(this);
        mEmail.setOnPreferenceClickListener(this);
        mOpensourceLicense.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        View view = getView();
        if (mAppIntro == preference) {
            Intent intent = new Intent(mContext, WebViewActivity.class);
            intent.putExtra(AtyExtraKeyConstant.WEB_URL, API.APP_INTRODUCTION);
            startActivity(intent);
        } else if (mCheckUpdate == preference) {
            if (canUpdate) {
                canUpdate = false;
                update(view);
            }
        } else if (mVersionIntro == preference) {
            Intent intent = new Intent(mContext, WebViewActivity.class);
            intent.putExtra(AtyExtraKeyConstant.WEB_URL, API.VERSION_INFO);
            startActivity(intent);
        } else if (mShareApp == preference) {
            AppUtil.copyText2Clipboard(mContext, mContext.getResources().getString(R.string.about_shareapp_content));
            SnackbarUtil.ShortSnackbar(view, getResources().getString(R.string.notify_info_copied), SnackbarUtil.Info).show();
        } else if (mFeedback == preference) {
            Intent mail = new Intent(Intent.ACTION_SENDTO);
            mail.setData(Uri.parse("mailto:cn.neillee@gmail.com"));
            mail.putExtra(Intent.EXTRA_SUBJECT, "DailyZHIHU Feedback");
            String content = getEmailContent();
            mail.putExtra(Intent.EXTRA_TEXT, content);
            startActivity(mail);
        } else if (mBlog == preference) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri content_url = Uri.parse(getString(R.string.about_blog_summary));
            intent.setData(content_url);
            startActivity(intent);
        } else if (mGithub == preference) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri content_url = Uri.parse(getString(R.string.about_github_address));
            intent.setData(content_url);
            startActivity(intent);
        } else if (mEmail == preference) {
            AppUtil.copyText2Clipboard(mContext, mContext.getResources().getString(R.string.about_email_address));
            SnackbarUtil.ShortSnackbar(view, getResources().getString(R.string.notify_info_copied), SnackbarUtil.Info).show();
        } else if (mOpensourceLicense == preference) {
            Intent intent = new Intent(mContext, WebViewActivity.class);
            intent.putExtra(AtyExtraKeyConstant.WEB_URL, API.OPENSOURCE_LICENSE);
            startActivity(intent);
        }
        return true;
    }

    private void update(final View view) {
        UpdateAppUtils.checkUpdate(new UpdateAppUtils.UpdateCallback() {
            @Override
            public void onSuccess(UpdateInfoBean updateInfo) {
                int versionCode = updateInfo.getVersionCode();
                if (versionCode > AppUtil.getVersionCode(mContext)) {
                    showUpdateDialog(updateInfo);
                } else {
                    SnackbarUtil.ShortSnackbar(view, getResources().getString(R.string.updating_is_updated), SnackbarUtil.Info).show();
                    canUpdate = true;
                }
            }

            @Override
            public void onError() {
                canUpdate = true;
            }
        });
    }

    private void showUpdateDialog(final UpdateInfoBean updateInfo) {
        String desc = updateInfo.getDescription();
        int versionCode = updateInfo.getVersionCode();
        String versionName = updateInfo.getVersionName();
        String size = AppUtil.bytes2kmgb(updateInfo.getSize());
        final String apkUrl = updateInfo.getUrl();

        View contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_update, null);
        TextView tvVersion = (TextView) contentView.findViewById(R.id.tv_version);
        tvVersion.setText(Formater.fromatUpdateVersionInfo(mContext, versionName, versionCode));
        TextView tvSize = (TextView) contentView.findViewById(R.id.tv_size);
        tvSize.setText(Formater.fromatUpdatePgSize(mContext, size));
        TextView tvDesc = (TextView) contentView.findViewById(R.id.tv_update_info);
        tvDesc.setText(desc);

        new AlertDialog.Builder(mContext).setTitle(getResources().getString(R.string.updating_get_updates))
                .setPositiveButton(getResources().getString(R.string.updating_update_now),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(apkUrl));
                                startActivity(intent);
                            }
                        })
                .setNegativeButton(getResources().getString(R.string.updating_update_later),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setView(contentView).create().show();
        canUpdate = true;
    }

    private String getEmailContent() {
        String content = "\n\n" + "------------------------" + "\n";
        content += "Package Name: " + getActivity().getPackageName() + "\n";
        content += "App Version: " + BuildConfig.VERSION_NAME + "\n";
        content += "App Version Code: " + BuildConfig.VERSION_CODE + "\n";
        content += "Device Model: " + Build.MODEL + "\n" + "Device Brand: " + Build.BRAND + "\n" + "SDK Version: " + Build.VERSION.SDK_INT + "\n" + "------------------------";
        return content;
    }
}
