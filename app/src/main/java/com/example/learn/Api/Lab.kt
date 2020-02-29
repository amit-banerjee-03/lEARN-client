package com.example.learn.Api

import com.example.learn.Models.Course
import com.example.learn.Models.Courses
import com.example.learn.Models.Lab
import com.example.learn.Models.Labs
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface Lab {
    @GET("labs/")
    fun getLabs(
        @Header("Authorization") authorization:String
    ): Call<Labs>

    @GET("labs/lab")
    fun getDetails(
        @Header("Authorization") authorization:String,
        @Query("id") id: Int
    ): Call<Lab>
}