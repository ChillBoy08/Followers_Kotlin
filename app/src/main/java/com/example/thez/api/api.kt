package com.example.thez.api

import com.example.thez.data.User
import com.example.thez.data.UserResponse
import com.example.thez.main.DetailUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface api {
    @GET("search/users")
    @Headers("Authorization: token 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 ")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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 ")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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 ")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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 ")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>

}