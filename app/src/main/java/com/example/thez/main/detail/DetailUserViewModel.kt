package com.example.thez.main.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.thez.api.RetrofitUser
import com.example.thez.data.favorite.UserDB
import com.example.thez.data.favorite.UserFavorite
import com.example.thez.data.favorite.UserFavoriteList
import com.example.thez.main.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<DetailUserResponse>()

    private var userDao: UserFavoriteList?
    private var userDB: UserDB?

    init {
        userDB = UserDB.getDataBase(application)
        userDao = userDB?.userFavoriteList()
    }

    private val _loading = MutableLiveData<Boolean>()
    val loading: MutableLiveData<Boolean> = _loading

    fun setUserDetail(username: String) {
        loading.value = true
        RetrofitUser.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                        loading.value = false
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                    loading.value = false
                }

            })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return user
    }

    fun addToFavorite(username: String, id: Int, avatar_url: String){
        CoroutineScope(Dispatchers.IO).launch {
            var user = UserFavorite(
                username,
                id,
                avatar_url
            )
            userDao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }
}