package com.njdc.abb.familyguard.ui.setting;

import android.content.Intent;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.njdc.abb.familyguard.R;

public class FragmentSetting extends PreferenceFragmentCompat {

    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.app_preferences, rootKey);
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        if (preference.getKey().equals("set_password")) {
            Intent intent = new Intent(getContext(), MdpwdActivity.class);
            startActivity(intent);
        }
        if (preference.getKey().equals("about")) {
            Intent intent = new Intent(getContext(), AboutActivity.class);
            startActivity(intent);
        }
        return super.onPreferenceTreeClick(preference);
    }


}

