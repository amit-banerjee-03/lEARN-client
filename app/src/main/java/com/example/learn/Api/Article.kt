package com.example.learn.Api

import com.example.learn.Models.Article
import com.example.learn.Models.Articles
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface Article {
    @GET("articles/")
    fun getArticles(
        @Header("Authorization") authorization:String,
        @Query("search_query") searchQuery:String?
    ): Call<Articles>

    @GET("articles/article")
    fun getDetails(
        @Header("Authorization") authorization:String,
        @Query("id") id: Int
    ): Call<Article>
}