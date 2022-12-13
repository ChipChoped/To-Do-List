package fr.univangers.todolist

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import java.lang.NumberFormatException

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val textSizePreference: Preference = findPreference(getString(R.string.pref_text_size_key))!!
        textSizePreference.summary = preferenceScreen.sharedPreferences!!.getString(textSizePreference.key, "")

        textSizePreference.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { preference, newValue ->
                val string = newValue as String
                try {
                    val type = string.toInt()
                    preference.summary = string
                    return@OnPreferenceChangeListener true
                } catch (nfe: NumberFormatException) {
                    return@OnPreferenceChangeListener false
                }
            }
    }
}