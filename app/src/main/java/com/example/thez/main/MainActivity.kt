package com.example.thez.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thez.R
import com.example.thez.data.User
import com.example.thez.databinding.ActivityMainBinding
import com.example.thez.main.detail.DetailUserActivity
import com.example.thez.main.detail.favorite.FavoriteActivity
import com.example.thez.main.theme.SettingPreferences
import com.example.thez.main.theme.Thema_Activity
import com.example.thez.main.theme.ViewModelFactory
import androidx.datastore.preferences.core.Preferences

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        //val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(dataStore)
        val mainViewModelTheme = ViewModelProvider(this, ViewModelFactory(pref)).get(
            MainViewModel::class.java
        )

        mainViewModelTheme.getThemeSetting().observe(this) {isDarkModeActivity: Boolean ->
            if (isDarkModeActivity){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        //switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
        //    mainViewModelTheme.saveThemeSetting(isChecked)
        //}

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also{
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_AVATAR_URL, data.avatar_url)
                    startActivity(it)
                }
            }

        })

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter

            btnSearch.setOnClickListener {
                searchUser()
            }

            etQuery.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
        viewModel.getSearchUsers().observe(this, {
            if (it!=null){
                adapter.setList(it)
                showLoading(false)
            }
        })
    }


    private fun searchUser(){
        binding.apply {
            val query = etQuery.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            viewModel.setSearchUsers(query)
        }
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
            binding.listUser.visibility = View.GONE
        }else{
            Thread.sleep(1000)
            binding.progressBar.visibility = View.GONE
            binding.listUser.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite_menu -> {
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
                true
            }
            R.id.setting -> {
                Intent(this, Thema_Activity::class.java).also {
                    startActivity(it)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}

private fun Any.observe(mainActivity: MainActivity, function: (Boolean) -> Unit) {

}


