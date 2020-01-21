package com.harloomDeveloper.moviecatalogharloom.ui.preference



import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference

import com.harloomDeveloper.moviecatalogharloom.R
import kotlinx.android.synthetic.main.settings_activity.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        setSupportActionBar(toolbar)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat(),
        SharedPreferences.OnSharedPreferenceChangeListener {
        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
            if (key == DAILY) {
                dailyPreference.isChecked = sharedPreferences.getBoolean(DAILY, false)
            }
            if (key == RELEASE) {
                releasePreference.isChecked = sharedPreferences.getBoolean(RELEASE, false)
            }
        }

        private lateinit var DAILY: String
        private lateinit var RELEASE: String


        private lateinit var dailyPreference: SwitchPreference
        private lateinit var releasePreference: SwitchPreference

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            init()
            setSummary()
        }

        override fun onResume() {
            super.onResume()
            preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }
        override fun onPause() {
            super.onPause()
            preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }


        private fun  init(){
            DAILY = resources.getString(R.string.key_daily)
            RELEASE = resources.getString(R.string.key_release_today)

            dailyPreference = findPreference<SwitchPreference>(DAILY) as SwitchPreference
            releasePreference = findPreference<SwitchPreference>(RELEASE) as SwitchPreference

        }

        private fun setSummary(){
            val sh = preferenceManager.sharedPreferences
            dailyPreference.isChecked = sh.getBoolean(DAILY,false)
            releasePreference.isChecked = sh.getBoolean(RELEASE,false)
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}