package com.example.thez.data.favorite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = (arrayOf(UserFavorite::class)),
    version = 1
)

abstract class UserDB: RoomDatabase() {
    companion object{
        var INSTANCE : UserDB? = null

        fun getDataBase(context: Context): UserDB?{
            if (INSTANCE==null){
                synchronized(UserDB::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, UserDB::class.java, "user_db").build()
                }
            }
            return  INSTANCE
        }
    }

    abstract fun  userFavoriteList(): UserFavoriteList
}