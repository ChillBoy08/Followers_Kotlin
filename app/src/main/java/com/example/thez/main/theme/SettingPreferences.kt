package com.example.thez.main.theme

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>){

    private val THEME_KEY = booleanPreferencesKey("Theme_Setting")

    fun getThemeSetting() = dataStore.data.map{preferences ->
        preferences[THEME_KEY] ?: false
    }

    suspend fun saveThemeSetting(isDarkModeActivity: Boolean){
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActivity
        }
    }

    companion object{

        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences{
            return INSTANCE ?: synchronized(this){
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}


