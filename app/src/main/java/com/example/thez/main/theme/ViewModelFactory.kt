package com.example.thez.main.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thez.main.MainViewModel

class ViewModelFactory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(pref) as T
        }

        if (modelClass.isAssignableFrom(MainViewModelTheme::class.java)){
            return MainViewModelTheme(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)

    }
}