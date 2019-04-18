package net.chenxiy.bilimusic.view;


import android.os.Bundle;

import net.chenxiy.bilimusic.R;

import androidx.preference.PreferenceFragmentCompat;

public class SettingFragment extends PreferenceFragmentCompat {
    public SettingFragment() {
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.setting_preference,rootKey);

    }
}
