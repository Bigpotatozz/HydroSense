package com.oscar.hydrosense

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.HiltAndroidApp



@HiltAndroidApp
class HydroSense: Application() {

    companion object {
        lateinit var appContext: Context
            private set
    }


    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

}