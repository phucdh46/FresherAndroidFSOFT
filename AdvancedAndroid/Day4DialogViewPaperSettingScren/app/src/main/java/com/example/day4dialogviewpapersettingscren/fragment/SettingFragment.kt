package com.example.day4dialogviewpapersettingscren.fragment

import android.os.Bundle
import androidx.preference.MultiSelectListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.day4dialogviewpapersettingscren.R

class SettingFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        //DropDownPreference: use useSimpleSummaryProvider save select choose

        //save multi select choose
        val columnSelect = findPreference<MultiSelectListPreference>("screen_zoom_and_font")!!
        columnSelect.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                columnSelect.setSummaryFromValues(newValue as Set<String>)
                true
            }
        columnSelect.setSummaryFromValues(columnSelect.values)
    }

    fun MultiSelectListPreference.setSummaryFromValues(values: Set<String>) {
        summary = values.map { entries[findIndexOfValue(it)] }.joinToString(", ")
    }
}