package com.solvro.topwr.di

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        //Force Light Mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        AndroidThreeTen.init(this);
    }
}