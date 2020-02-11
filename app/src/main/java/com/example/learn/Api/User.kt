package com.example.learn.Api

import android.database.Observable
import com.example.learn.Models.Login
import com.example.learn.Models.Users
import com.example.learn.Routes
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface User {
    @FormUrlEncoded
    @POST("user/login")
    fun login(
        @Field("email") email:String,
        @Field("password") password:String
    ): Call<Login>

    @FormUrlEncoded
    @POST("user/signup")
    fun signUp(
        @Field("email") email:String,
        @Field("password") password:String,
        @Field("first_name") firstName:String,
        @Field("last_name") lastName:String,
        @Field("gender") gender:String
    ): Call<User>

    @GET("user/")
    fun getDetails(
//        @Query("_id") id: String
    ): Observable<Users>
}