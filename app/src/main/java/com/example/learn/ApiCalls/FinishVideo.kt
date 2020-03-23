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
import com.example.learn.Api.ApiError
import com.example.learn.Api.User
import com.example.learn.Config.Constants
import com.example.learn.CustomAdapters.ImageListView
import com.example.learn.Home
import com.example.learn.MainActivity
import com.example.learn.Models.Course
import com.example.learn.Models.Courses
import com.example.learn.Models.FinishVideo
import com.example.learn.Models.Login
import com.example.learn.Models.Video
import com.example.learn.Routes
import com.example.learn.Utils.AuthenticationToken
import com.example.learn.Utils.ErrorHandler
import com.example.learn.Utils.RetrofitClient
import com.example.learn.Utils.StartLoginActivity
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

object FinishVideo {
    fun execute(context: Context,id:Int) {
        RetrofitClientVideo.instance.finishVideo("Bearer ${AuthenticationToken(context).getJWT()}",id)
            .enqueue(object : Callback<FinishVideo>{
                override fun onFailure(call: Call<FinishVideo>, t: Throwable) {
                    ErrorHandler.handle(context,t.toString())
                }

                override fun onResponse(call: Call<FinishVideo>, response: Response<FinishVideo>) {
                    try{
                        if(response.isSuccessful) {
                            Log.v("GetVideos",response.body().toString())
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