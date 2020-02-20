package com.example.learn.ApiCalls

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Base64
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.example.learn.Api.Course
import com.example.learn.Api.User
import com.example.learn.Config.Constants
import com.example.learn.CustomAdapters.ImageListView
import com.example.learn.Home
import com.example.learn.MainActivity
import com.example.learn.Models.Courses
import com.example.learn.Models.Login
import com.example.learn.Routes
import com.example.learn.Utils.AuthenticationToken
import com.example.learn.Utils.ErrorHandler
import com.example.learn.Utils.RetrofitClient
import kotlinx.android.synthetic.main.activity_home.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

object HomePageCourses {
    class StartHomeActivity: AsyncTask<Context, Context, Context>(){
        override fun doInBackground(vararg p0: Context?): Context? {
            return p0[0]
        }

        override fun onPostExecute(result: Context?) {
            result!!.startActivity(Intent(result, Home::class.java))
            (result as MainActivity).finish()
        }

    }

    fun getCourses(context: Context, courseListElement: ListView) {
        RetrofitClientCourse.instance.getCourses()
            .enqueue(object: Callback<Courses> {
                override fun onFailure(call: Call<Courses>, t: Throwable) {
                    ErrorHandler.handle(context,t.toString())
                }
                override fun onResponse(call: Call<Courses>, response: Response<Courses>) {
                    try{
                        Home.LoadCourses(context,courseListElement,response.body()!!.courses).execute()
                    }catch (e:Exception){
                        ErrorHandler.handle(context,"Error loading courses")
                        return
                    }
                }
            })
    }
}

object RetrofitClientCourse {
    private const val BASE_URL= Constants.SERVER_HOST
    private val AUTH="Bearer "+ Base64.encodeToString("username:pass".toByteArray(), Base64.NO_WRAP)
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor{chain ->
            val original=chain.request()

            val requestBuilder=original.newBuilder()
                .addHeader("Authorization",AUTH)
                .method(original.method(),original.body())
            val request=requestBuilder.build()
            chain.proceed(request)
        }.build()

    val instance: Course by lazy {
        val retrofit= Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        retrofit.create(Course::class.java)
    }
}