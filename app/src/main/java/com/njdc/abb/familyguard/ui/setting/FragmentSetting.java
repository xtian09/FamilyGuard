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
        if (preference.getKey().equals("add_abb")) {
            startActivity(new Intent(getContext(), AddDeviceActivity.class));
        }
        if (preference.getKey().equals("set_entironment_entities")) {
            startActivity(new Intent(getContext(), EnvironmentItemActivity.class));
        }
        if (preference.getKey().equals("set_password")) {
            startActivity(new Intent(getContext(), MdpwdActivity.class));
        }
        if (preference.getKey().equals("about")) {
            startActivity(new Intent(getContext(), MdpwdActivity.class));
        }
        return super.onPreferenceTreeClick(preference);
    }


}

