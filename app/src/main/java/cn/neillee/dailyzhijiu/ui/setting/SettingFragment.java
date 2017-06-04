package cn.neillee.dailyzhijiu.ui.setting;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.view.View;

import com.neil.dailyzhijiu.R;

import java.io.File;

import cn.neillee.dailyzhijiu.Constant;
import cn.neillee.dailyzhijiu.utils.ACache;
import cn.neillee.dailyzhijiu.utils.Settings;
import cn.neillee.dailyzhijiu.utils.SnackbarUtil;

/**
 * 作者：Neil on 2017/3/5 20:48.
 * 邮箱：cn.neillee@gmail.com
 */

public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {
    private SettingActivity mContext;
    private String[] mSplashEntries;
    private String[] mLanguageEntries;

    private File mCacheFile;

    private ListPreference mSetSplash;
    private ListPreference mSetLanguage;
    private Preference mSwitchTheme;
    private SwitchPreference mDayNightMode;
    private SwitchPreference mExitWithEnsuring;
    private SwitchPreference mNoImageMode;
    private Preference mClearCache;

    private String SET_SPLASH = "key_set_splash";
    private String SET_LANGUAE = "key_language";
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
        mLanguageEntries = getResources().getStringArray(R.array.language_entries);
        initAllPreferences();
    }

    private void initAllPreferences() {
        mSetSplash = (ListPreference) findPreference(SET_SPLASH);
        mSetLanguage = (ListPreference) findPreference(SET_LANGUAE);
        mSwitchTheme = findPreference(SWITCH_THEME);
        mDayNightMode = (SwitchPreference) findPreference(DAY_NIGHT_MODE);
        mExitWithEnsuring = (SwitchPreference) findPreference(EXIT_WITH_ENSURING);
        mNoImageMode = (SwitchPreference) findPreference(NO_IMAGE_MODE);
        mClearCache = findPreference(CLEAR_CACHE);

        mSetSplash.setDialogTitle(getString(R.string.setting_splash_dialog_title));
        mSetSplash.setOnPreferenceChangeListener(this);
        mSetLanguage.setOnPreferenceChangeListener(this);
        mSwitchTheme.setOnPreferenceClickListener(this);
        mDayNightMode.setOnPreferenceClickListener(this);
        mExitWithEnsuring.setOnPreferenceClickListener(this);
        mNoImageMode.setOnPreferenceClickListener(this);
        mClearCache.setOnPreferenceClickListener(this);

        // init preference state
        mDayNightMode.setTitle(Settings.isNightMode ?
                R.string.setting_day_mode_title : R.string.setting_night_mode_title);
        mDayNightMode.setSummary(Settings.isNightMode ?
                R.string.setting_day_mode_summary : R.string.setting_night_mode_summary);
        mDayNightMode.setChecked(Settings.isNightMode);
        mExitWithEnsuring.setChecked(Settings.isExitConfirm);
        mNoImageMode.setChecked(Settings.noPicMode);
        int splashSetting = mContext.mSettings.getInt(Settings.SPLASH_SETTING, 0);
        mSetSplash.setSummary(mSplashEntries[splashSetting]);
        mSetSplash.setValueIndex(splashSetting);
        mCacheFile = new File(Constant.PATH_CACHE);
        mClearCache.setSummary(ACache.getCacheSize(mCacheFile));
        int languageSetting = mContext.mSettings.getInt(Settings.LANGUAGE, 1);
        mSetLanguage.setSummary(mLanguageEntries[languageSetting]);
        mSetLanguage.setValueIndex(languageSetting);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        View view = getView();
        if (mSwitchTheme == preference) {
            SnackbarUtil.ShortSnackbarWithTheme(mContext, view, getResources().getString(R.string.notify_to_do)).show();
        } else if (mDayNightMode == preference) {
            mContext.callChangeNightMode();
            preference.setTitle(Settings.isNightMode ?
                    R.string.setting_day_mode_title : R.string.setting_night_mode_title);
            preference.setSummary(Settings.isNightMode ?
                    R.string.setting_day_mode_summary : R.string.setting_night_mode_summary);
        } else if (mExitWithEnsuring == preference) {
            mContext.callChangeExitConfirm();
        } else if (mNoImageMode == preference) {
            mContext.callChangeNoPicMode();
        } else if (mClearCache == preference) {
            ACache.deleteDir(mCacheFile);
            mClearCache.setSummary(ACache.getCacheSize(mCacheFile));
            SnackbarUtil.ShortSnackbarWithTheme(mContext, view, getString(R.string.notify_clear_successfully)).show();
        }
        return true;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mSetSplash) {
            mContext.mSettings.putInt(Settings.SPLASH_SETTING, Integer.valueOf((String) newValue));
            preference.setSummary(mSplashEntries[Integer.valueOf((String) newValue)]);
        } else if (preference == mSetLanguage) {
            mContext.mSettings.putInt(Settings.LANGUAGE, Integer.valueOf((String) newValue));
            preference.setSummary(mLanguageEntries[Integer.valueOf((String) newValue)]);
            mContext.callChangeLanguage();
        }
        return true;
    }
}
