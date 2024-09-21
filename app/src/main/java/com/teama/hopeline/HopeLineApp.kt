package com.teama.hopeline

import android.app.Application
import com.teama.hopeline.data.AppPreference

class HopeLineApp : Application(){

    override fun onCreate() {
        super.onCreate()
        AppPreference.setuppreferences(this)
    }
}