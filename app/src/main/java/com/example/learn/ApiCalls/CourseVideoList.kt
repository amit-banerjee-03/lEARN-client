package com.example.learn.ApiCalls

import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.learn.Api.ApiError
import com.example.learn.Api.User
import com.example.learn.Config.Constants
import com.example.learn.CustomAdapters.ImageListView
import com.example.learn.Home
import com.example.learn.MainActivity
import com.example.learn.Models.Course
import com.example.learn.Models.Courses
import com.example.learn.Models.Login
import com.example.learn.Models.Video
import com.example.learn.Routes
import com.example.learn.Utils.*
import com.example.learn.VideoList
import kotlinx.android.synthetic.main.activity_home.*
import okhttp3.OkHttpClient
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

object CourseVideoList {
    fun getVideos(context: Context,courseDetailImage:ImageView, videoListElement: RecyclerView,courseDescription: TextView,noVideoFoundElement:TextView,id:Int) {
        RetrofitClientVideo.instance.getDetails("Bearer ${AuthenticationToken(context).getJWT()}",id)
            .enqueue(object : Callback<Course>{
                override fun onFailure(call: Call<Course>, t: Throwable) {
                    ErrorHandler.handle(context,t.toString())
                }

                override fun onResponse(call: Call<Course>, response: Response<Course>) {
                    try{
                        if(response.isSuccessful) {
                            Log.v("GetVideos",response.body().toString())
                            LoadImage(courseDetailImage,response.body()!!.cover_image).execute()
                            VideoList.LoadVideos(context, videoListElement, noVideoFoundElement,courseDescription,response.body()!!).execute()
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
                        ErrorHandler.handle(context,"Error loading videos")
                        return
                    }
                }

            });
    }
}

object RetrofitClientVideo {
    private const val BASE_URL= Constants.SERVER_HOST
    //    private val AUTH="Bearer "+ Base64.encodeToString("Bearer\n{$}".toByteArray(), Base64.NO_WRAP)
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor{chain ->
            val original=chain.request()

            val requestBuilder=original.newBuilder()
                .method(original.method(),original.body())
            val request=requestBuilder.build()
            chain.proceed(request)
        }.build()

    val instance: com.example.learn.Api.Course by lazy {
        val retrofit= Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        retrofit.create(com.example.learn.Api.Course::class.java)
    }
}