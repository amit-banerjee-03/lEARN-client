package com.example.learn.Utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import com.example.learn.Api.User
import com.example.learn.Config.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

object RetrofitClient {
    private const val BASE_URL=Constants.SERVER_HOST
    private val AUTH="Bearer "+Base64.encodeToString("username:pass".toByteArray(),Base64.NO_WRAP)
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor{chain ->
            val original=chain.request()

            val requestBuilder=original.newBuilder()
                .addHeader("Authorization",AUTH)
                .method(original.method(),original.body())
            val request=requestBuilder.build()
            chain.proceed(request)
        }.build()

    val instance: User by lazy {
        val retrofit= Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        retrofit.create(User::class.java)
    }
}