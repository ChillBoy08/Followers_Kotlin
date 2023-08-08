package com.example.thez.data.favorite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserFavoriteList {
    @Insert
    suspend fun addToFavorite(favoriteUser: UserFavorite)

    @Query("SElECT * FROM user_favorite")
    fun getFavoriteUser(): LiveData<List<UserFavorite>>

    @Query("SELECT count(*) FROM user_favorite WHERE user_favorite.id = :id")
    suspend fun checkUser(id: Int): Int

    @Query("DELETE FROM user_favorite WHERE user_favorite.id = :id")
    suspend fun removeFromFavorite(id: Int): Int
}