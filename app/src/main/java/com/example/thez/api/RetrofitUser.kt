package com.example.thez.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUser {
    private const val BASE_URL = "https://api.github.com/"

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiInstance = retrofit.create(api::class.java)
}