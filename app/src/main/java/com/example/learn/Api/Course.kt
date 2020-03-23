package com.example.learn.Api

import android.database.Observable
import com.example.learn.Models.*
import com.example.learn.Models.Course
import retrofit2.Call
import retrofit2.http.*

interface Course {
//    @FormUrlEncoded
//    @POST("user/login")
//    fun login(
//        @Field("email") email:String,
//        @Field("password") password:String
//    ): Call<Login>
//
//    @FormUrlEncoded
//    @POST("user/signup")
//    fun signUp(
//        @Field("email") email:String,
//        @Field("password") password:String,
//        @Field("first_name") firstName:String,
//        @Field("last_name") lastName:String,
//        @Field("gender") gender:String
//    ): Call<User>

    @GET("courses/")
    fun getCourses(
        @Header("Authorization") authorization:String
    ): Call<Courses>

    @GET("courses/course")
    fun getDetails(
        @Header("Authorization") authorization:String,
        @Query("id") id: Int
    ): Call<Course>

    @GET("courses/course/finish-video")
    fun finishVideo(
        @Header("Authorization") authorization:String,
        @Query("id") id: Int
    ): Call<FinishVideo>
}