package com.example.thez.data.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_favorite")
data class UserFavorite(
    val login: String,
    @PrimaryKey
    val id: Int,
    val avatar_url : String
): java.io.Serializable
