package com.teama.hopeline.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import java.util.prefs.Preferences

object AppPreference {

    lateinit var appPreferences: SharedPreferences

    fun setuppreferences(context: Context) {
        appPreferences = context.getSharedPreferences("MySharedPref",MODE_PRIVATE);
    }

    fun saveString(key: String, data: String) {
        with (appPreferences.edit()) {
            putString(key, data)
            apply()
        }
    }

    fun getStringValue(key: String): String {
        return appPreferences.getString(key, "").orEmpty()
    }
}