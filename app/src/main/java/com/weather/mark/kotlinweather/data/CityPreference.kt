package com.weather.mark.kotlinweather.data

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log

class CityPreference {
    companion object {
        private const val CITY_SHOWING = "city_visible"
        private const val LAST_LOCATION = "location"
        private val TAG = "City"

        fun getCityVisible(context: Context): String? {
            Log.e(
                TAG, "getCityVisible: " + PreferenceManager.getDefaultSharedPreferences(context)
                    .getString(CITY_SHOWING, null)
            )
            return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(CITY_SHOWING, null)
        }

        fun setCityVisible(context: Context, city: String) {
            Log.e(TAG, "setCityVisible: $city")
            PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(CITY_SHOWING, city)
                .apply()
        }

        fun getLastLocation(context: Context): String? {
            return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(LAST_LOCATION, null)
        }

        fun setLastLocation(context: Context, city: String) {
            PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(LAST_LOCATION, city)
                .apply()
        }
    }
}