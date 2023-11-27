package dev.simulatedrealitysoft.adminfinder.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import dev.simulatedrealitysoft.adminfinder.R;
import dev.simulatedrealitysoft.adminfinder.utils.Utils;

public class Frg_Settings extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    CheckBoxPreference CPUwakelock;
    CheckBoxPreference lckscreen;
    CheckBoxPreference lckwifi;
    SharedPreferences sharedPrefs;
    CheckBoxPreference timestamp;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.settings_main);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        lckscreen = (CheckBoxPreference) findPreference("lockScreen");
        lckwifi = (CheckBoxPreference) findPreference("lockWifi");
        CPUwakelock = (CheckBoxPreference) findPreference("wakeLock");
        timestamp = (CheckBoxPreference) findPreference("timeStamp");
        lckscreen.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                if (Frg_Settings.this.lckscreen.isChecked()) {
                    Log.d("Lockscreen", "Active");
                    Utils.keepScreenOn(Frg_Settings.this.getActivity(), true);
                } else {
                    Log.d("Lockscreen", "Not Active");
                    Utils.keepScreenOn(Frg_Settings.this.getActivity(), false);
                }
                return false;
            }
        });
        lckwifi.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                if (Frg_Settings.this.lckwifi.isChecked()) {
                    Log.d("lockwifi", "Active");
                    Utils.keepWiFiOn(Frg_Settings.this.getActivity(), true);
                } else {
                    Log.d("lockwifi", "Not Active");
                    Utils.keepWiFiOn(Frg_Settings.this.getActivity(), false);
                }
                return false;
            }
        });
        CPUwakelock.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                if (Frg_Settings.this.CPUwakelock.isChecked()) {
                    Log.d("wakelock", "Active");
                    Utils.keepCPUOn(Frg_Settings.this.getActivity(), true);
                } else {
                    Log.d("wakelock", "Not Active");
                    Utils.keepCPUOn(Frg_Settings.this.getActivity(), false);
                }
                return false;
            }
        });
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
    }

    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
        Preference findPreference = findPreference(str);
        if (findPreference instanceof EditTextPreference) {
            findPreference.setSummary(((EditTextPreference) findPreference).getText());
        }
        if (findPreference instanceof ListPreference) {
            findPreference.setSummary(((ListPreference) findPreference).getEntry());
        }
    }
}
