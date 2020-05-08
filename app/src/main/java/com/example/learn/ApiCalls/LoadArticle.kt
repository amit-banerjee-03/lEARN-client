package com.example.learn.ApiCalls

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.learn.*
import com.example.learn.Api.*
import com.example.learn.Config.Constants
import com.example.learn.CustomAdapters.ImageListView
import com.example.learn.Models.Articles
import com.example.learn.Models.Courses
import com.example.learn.Models.Labs
import com.example.learn.Models.Login
import com.example.learn.Utils.*
import kotlinx.android.synthetic.main.activity_home.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

object LoadArticle {
    fun getArticles(context: Context, articleCoverImage:ImageView, articleListElement: RecyclerView, searchQuery:String?) {
        RetrofitClientArticles.instance.getArticles("Bearer ${AuthenticationToken(context).getJWT()}",searchQuery)
            .enqueue(object: Callback<Articles> {
                override fun onFailure(call: Call<Articles>, t: Throwable) {
                    ErrorHandler.handle(context,t.toString())
                }
                override fun onResponse(call: Call<Articles>, response: Response<Articles>) {
                    try{
                        if(response.isSuccessful) {
                            LoadImage(articleCoverImage,response.body()!!.cover_image!!).execute()
                            ArticlesActivity.LoadArticles(context,articleListElement,response.body()!!.articles).execute()
                        } else{
                            val error= ApiError().getError(response.errorBody()?.string())
                            ErrorHandler.handle(context,error)
                            if(response.code()==401){
                                AuthenticationToken(context).setJWT("")
                                StartLoginActivity().execute(context)
                            }
                        }
                    }catch (e:Exception){
                        Log.v("Debug",e.toString())
                        ErrorHandler.handle(context,"Error loading articles")
                        return
                    }
                }
            })
    }
}

object RetrofitClientArticles {
    private const val BASE_URL= Constants.SERVER_HOST
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor{chain ->
            val original=chain.request()

            val requestBuilder=original.newBuilder()
                .method(original.method(),original.body())
            val request=requestBuilder.build()
            chain.proceed(request)
        }.build()

    val instance: Article by lazy {
        val retrofit= Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        retrofit.create(Article::class.java)
    }
}