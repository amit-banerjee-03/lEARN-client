package com.example.learn.ApiCalls

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Base64
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.learn.*
import com.example.learn.Api.ApiError
import com.example.learn.Api.Coding
import com.example.learn.Api.User
import com.example.learn.Config.Constants
import com.example.learn.CustomAdapters.ImageListView
import com.example.learn.Models.Course
import com.example.learn.Models.Courses
import com.example.learn.Models.FinishVideo
import com.example.learn.Models.Login
import com.example.learn.Models.UserProgress
import com.example.learn.Models.Video
import com.example.learn.Utils.AuthenticationToken
import com.example.learn.Utils.ErrorHandler
import com.example.learn.Utils.RetrofitClient
import com.example.learn.Utils.StartLoginActivity
import kotlinx.android.synthetic.main.activity_home.*
import okhttp3.OkHttpClient
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit

object UserProgress {
    fun getProgress(context: Context,courseRecyclerView: RecyclerView,virtualLabRecyclerView: RecyclerView) {
        RetrofitClientPorgress.instance.getProgress("Bearer ${AuthenticationToken(context).getJWT()}")
            .enqueue(object : Callback<com.example.learn.Models.UserProgress>{
                override fun onFailure(call: Call<com.example.learn.Models.UserProgress>, t: Throwable) {
                    ErrorHandler.handle(context,t.toString())
                }

                override fun onResponse(call: Call<com.example.learn.Models.UserProgress>, response: Response<com.example.learn.Models.UserProgress>) {
                    try{
                        if(response.isSuccessful) {
                            Log.v("GetVideos",response.body().toString())
                            ProgressMonitorActivity.LoadProgress(context, courseRecyclerView, virtualLabRecyclerView,response.body()!!.courses,response.body()!!.labs).execute()
                        } else{
                            val error= ApiError().getError(response.errorBody()?.string())
                            ErrorHandler.handle(context,error)
                            if(response.code()==401){
                                AuthenticationToken(context).setJWT("")
                                StartLoginActivity().execute(context)
                            }
                        }
                    }catch (e:Exception){
                        Log.v("GetVideos",e.toString())
                        return
                    }
                }

            })
    }
}

object RetrofitClientPorgress {
    private const val BASE_URL= Constants.SERVER_HOST
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(100, TimeUnit.SECONDS)
        .readTimeout(100, TimeUnit.SECONDS)
        .addInterceptor{chain ->
            val original=chain.request()

            val requestBuilder=original.newBuilder()
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