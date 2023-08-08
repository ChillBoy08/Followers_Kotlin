package com.example.thez.main.theme

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.thez.R
import com.example.thez.databinding.ActivityMainBinding
import com.example.thez.databinding.ActivityThemaBinding
import com.example.thez.main.MainViewModel
import com.example.thez.main.UserAdapter
import com.example.thez.main.dataStore
import com.google.android.material.switchmaterial.SwitchMaterial

class Thema_Activity : AppCompatActivity(){

    private lateinit var binding: ActivityThemaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thema)

        val pref = SettingPreferences.getInstance(dataStore)
        val mainViewModelTheme = ViewModelProvider(this, ViewModelFactory(pref)).get(
            MainViewModel::class.java
        )

        val adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainViewModelTheme.saveThemeSetting(isChecked)
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    companion object {
        fun inflate(layoutInflater: LayoutInflater): ActivityMainBinding {

            return TODO("Provide the return value")
        }
    }
}



