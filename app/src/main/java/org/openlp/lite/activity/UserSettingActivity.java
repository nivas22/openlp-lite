package org.openlp.lite.activity;

/**
 * Created by Seenivasan on 10/8/2014.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import org.openlp.lite.OpenlpApplicaton;
import org.openlp.lite.R;
import org.openlp.lite.picker.ColorPickerPreference;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;


public class UserSettingActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    public static class MyPreferenceFragment extends PreferenceFragment
    {
        Context context = OpenlpApplicaton.getContext();

        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
            ((ColorPickerPreference)findPreference("color2")).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    preference.setDefaultValue(ColorPickerPreference.convertToARGB(Integer.valueOf(String.valueOf(newValue))));
                    Log.d(this.getClass().getName(), "Font Color:" + ColorPickerPreference.convertToARGB(Integer.valueOf(String.valueOf(newValue))));
                    preference.setSummary(ColorPickerPreference.convertToARGB(Integer.valueOf(String.valueOf(newValue))));
                    return true;
                }

            });
            ((ColorPickerPreference)findPreference("color2")).setAlphaSliderEnabled(true);
        }
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference pref = findPreference(key);
        if (pref instanceof ColorPickerPreference) {
            ColorPickerPreference etp = (ColorPickerPreference) pref;
            if (pref.getKey().equals("color2")) {
               Log.d(this.getClass().getName(),"Get Color value onChanged"+etp.getValue());
                pref.setSummary(etp.getValue());
            }
        }
        if (pref instanceof ListPreference) {
            ListPreference etp = (ListPreference) pref;
            if (pref.getKey().equals("prefSetFont")) {
                pref.setSummary(etp.getValue());
            }
        }
    }

}