package com.anamaria.onlineshop

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    companion object {
        const val REGION = "region"
        const val REGION_USA = "usa"
        const val REGION_EUROPE = "europe"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)
    }

    override fun onStart() {
        super.onStart()

        val country =
            PreferenceManager.getDefaultSharedPreferences(applicationContext)
                .getString(REGION, REGION_USA)

        radio_country.check(if (country == REGION_USA) R.id.usa else R.id.europe)

        radio_country.setOnCheckedChangeListener { group, checkedId ->
            val chosenRegion = if (checkedId == R.id.usa) REGION_USA else REGION_EUROPE
            chooseRegion(chosenRegion)
        }

    }

    private fun chooseRegion(region: String) {
        PreferenceManager.getDefaultSharedPreferences(applicationContext)
            .edit()
            .putString(REGION, region)
            .apply()
    }
}