package com.neil.dailyzhihu.ui.setting;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.view.View;

import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.utils.Settings;
import com.neil.dailyzhihu.utils.SnackbarUtil;

/**
 * 作者：Neil on 2017/3/5 20:48.
 * 邮箱：cn.neillee@gmail.com
 */

public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {
    private SettingActivity mContext;
    private String[] mSplashEntries;

    private ListPreference mSetSplash;
    private Preference mSwitchTheme;
    private CheckBoxPreference mDayNightMode;
    private CheckBoxPreference mExitWithEnsuring;
    private CheckBoxPreference mNoImageMode;
    private Preference mClearCache;

    private String SET_SPLASH = "key_set_splash";
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
        mSplashEntries = getResources().getStringArray(R.array.splash_entries);
        initAllPreferences();
    }

    private void initAllPreferences() {
        mSetSplash = (ListPreference) findPreference(SET_SPLASH);
        mSwitchTheme = findPreference(SWITCH_THEME);
        mDayNightMode = (CheckBoxPreference) findPreference(DAY_NIGHT_MODE);
        mExitWithEnsuring = (CheckBoxPreference) findPreference(EXIT_WITH_ENSURING);
        mNoImageMode = (CheckBoxPreference) findPreference(NO_IMAGE_MODE);
        mClearCache = findPreference(CLEAR_CACHE);

        mSetSplash.setOnPreferenceChangeListener(this);
        mSwitchTheme.setOnPreferenceClickListener(this);
        mDayNightMode.setOnPreferenceClickListener(this);
        mExitWithEnsuring.setOnPreferenceClickListener(this);
        mNoImageMode.setOnPreferenceClickListener(this);
        mClearCache.setOnPreferenceClickListener(this);

        // init preference state
        mDayNightMode.setChecked(Settings.isNightMode);
        mExitWithEnsuring.setChecked(Settings.isExitConfirm);
        mNoImageMode.setChecked(Settings.noPicMode);
        int splashSetting = mContext.mSettings.getInt(Settings.SPLASH_SETTING, 0);
        mSetSplash.setSummary(mSplashEntries[splashSetting]);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        View view = getView();
        if (mSwitchTheme == preference) {
            SnackbarUtil.ShortSnackbar(view, getResources().getString(R.string.to_do), SnackbarUtil.Confirm).show();
        } else if (mDayNightMode == preference) {
            mContext.callChangeNightMode();
        } else if (mExitWithEnsuring == preference) {
            mContext.callChangeExitConfirm();
        } else if (mNoImageMode == preference) {
            mContext.callChangeNoPicMode();
        } else if (mClearCache == preference) {
            SnackbarUtil.ShortSnackbar(view, getResources().getString(R.string.to_do), SnackbarUtil.Confirm).show();
        }
        return true;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mSetSplash) {
            mContext.mSettings.putInt(Settings.SPLASH_SETTING, Integer.valueOf((String) newValue));
            preference.setSummary(mSplashEntries[Integer.valueOf((String) newValue)]);
        }
        return true;
    }
}
