package com.neil.dailyzhihu.ui.setting;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.ui.NightModeBaseActivity;

/**
 * 作者：Neil on 2017/3/5 20:48.
 * 邮箱：cn.neillee@gmail.com
 */

public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
    private SettingActivity mContext;

    private Preference mSwitchTheme;
    private CheckBoxPreference mDayNightMode;
    private CheckBoxPreference mExitWithEnsuring;
    private CheckBoxPreference mNoImageMode;
    private Preference mClearCache;

    private String SWITCH_THEME = "key_switch_theme";
    private String DAY_NIGHT_MODE = "key_day_night_mode";
    private String EXIT_WITH_ENSURING = "key_exit_with_ensuring";
    private String NO_IMAGE_MODE = "key_no_image_mode";
    private String CLEAR_CACHE = "key_clear_cache";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);

        mContext = (SettingActivity) getActivity();
        initAllPreferences();
    }

    private void initAllPreferences() {
        mSwitchTheme = findPreference(SWITCH_THEME);
        mDayNightMode = (CheckBoxPreference) findPreference(DAY_NIGHT_MODE);
        mExitWithEnsuring = (CheckBoxPreference) findPreference(EXIT_WITH_ENSURING);
        mNoImageMode = (CheckBoxPreference) findPreference(NO_IMAGE_MODE);
        mClearCache = findPreference(CLEAR_CACHE);

        mSwitchTheme.setOnPreferenceClickListener(this);
        mDayNightMode.setOnPreferenceClickListener(this);
        mExitWithEnsuring.setOnPreferenceClickListener(this);
        mNoImageMode.setOnPreferenceClickListener(this);
        mClearCache.setOnPreferenceClickListener(this);

        // init preference state
        mDayNightMode.setChecked(mContext.isNightMode());
        mExitWithEnsuring.setChecked(mContext.isExitConfirm());
        mNoImageMode.setChecked(mContext.isNoPicMode());
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        View view = getView();
        if (mSwitchTheme == preference) {
            Snackbar.make(view, getResources().getString(R.string.to_do), Snackbar.LENGTH_SHORT).show();
        } else if (mDayNightMode == preference) {
            mContext.callChangeNightMode();
        } else if (mExitWithEnsuring == preference) {
            mContext.callChangeExitConfirm();
        } else if (mNoImageMode == preference) {
            mContext.callChangeNoPicMode();
        } else if (mClearCache == preference) {
            Snackbar.make(view, getResources().getString(R.string.to_do), Snackbar.LENGTH_SHORT).show();
        }
        return false;
    }
}
