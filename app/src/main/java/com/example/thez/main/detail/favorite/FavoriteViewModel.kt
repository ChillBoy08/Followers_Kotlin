package com.example.thez.main.detail.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.thez.data.favorite.UserDB
import com.example.thez.data.favorite.UserFavorite
import com.example.thez.data.favorite.UserFavoriteList

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var userDao: UserFavoriteList?
    private var userDB: UserDB?

    init {
        userDB = UserDB.getDataBase(application)
        userDao = userDB?.userFavoriteList()
    }

    fun getFavoriteUser(): LiveData<List<UserFavorite>>?{
        return userDao?.getFavoriteUser()
    }
}