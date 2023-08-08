package com.example.thez.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.thez.api.RetrofitUser
import com.example.thez.data.User
import com.example.thez.data.UserResponse
import com.example.thez.main.theme.SettingPreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class MainViewModel(private val pref: SettingPreferences) : ViewModel(){

    val listUser = MutableLiveData<ArrayList<User>>()

    fun setSearchUsers(query: String){
        RetrofitUser.apiInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful){
                        listUser.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }

    fun getThemeSetting(): LiveData<Boolean>{
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean){
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

    fun getSearchUsers(): LiveData<ArrayList<User>>{
        return listUser
    }
}

private fun Any.enqueue(callback: Callback<UserResponse>) {

}

