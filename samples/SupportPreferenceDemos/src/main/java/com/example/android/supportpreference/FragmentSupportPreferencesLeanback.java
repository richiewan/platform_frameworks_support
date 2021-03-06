/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.example.android.supportpreference;

import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.leanback.preference.LeanbackPreferenceFragmentCompat;
import androidx.leanback.preference.LeanbackSettingsFragmentCompat;
import androidx.preference.Preference;
import androidx.preference.PreferenceDialogFragmentCompat;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.example.androidx.preference.R;

/**
 * TODO(b/112588100): Remove after documentation is updated to point to new samples
 */
@RequiresApi(21)
public class FragmentSupportPreferencesLeanback extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(android.R.id.content,
                    new SettingsFragment()).commit();
        }
    }

//BEGIN_INCLUDE(support_fragment_leanback)

    /**
     * Entry of settings fragment.
     */
    public static class SettingsFragment extends LeanbackSettingsFragmentCompat {
        @Override
        public void onPreferenceStartInitialScreen() {
            startPreferenceFragment(new PrefsFragment());
        }

        @Override
        public boolean onPreferenceStartFragment(PreferenceFragmentCompat caller, Preference pref) {
            final Bundle args = pref.getExtras();
            final Fragment f = getChildFragmentManager().getFragmentFactory().instantiate(
                    requireActivity().getClassLoader(), pref.getFragment());
            f.setArguments(args);
            f.setTargetFragment(caller, 0);
            if (f instanceof PreferenceFragmentCompat
                    || f instanceof PreferenceDialogFragmentCompat) {
                startPreferenceFragment(f);
            } else {
                startImmersiveFragment(f);
            }
            return true;
        }

        @Override
        public boolean onPreferenceStartScreen(PreferenceFragmentCompat caller,
                PreferenceScreen pref) {
            final Fragment f = new PrefsFragment();
            final Bundle args = new Bundle(1);
            args.putString(PreferenceFragmentCompat.ARG_PREFERENCE_ROOT, pref.getKey());
            f.setArguments(args);
            startPreferenceFragment(f);
            return true;
        }
    }

    /**
     * The fragment that is embedded in SettingsFragment.
     */
    public static class PrefsFragment extends LeanbackPreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            // Load the preferences from an XML resource
            setPreferencesFromResource(R.xml.preferences, rootKey);
        }
    }
//END_INCLUDE(support_fragment_leanback)

}
