package com.example.learn.ApiCalls

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Base64
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.learn.Api.ApiError
import com.example.learn.Api.Course
import com.example.learn.Api.Lab
import com.example.learn.Api.User
import com.example.learn.Config.Constants
import com.example.learn.CustomAdapters.ImageListView
import com.example.learn.Home
import com.example.learn.MainActivity
import com.example.learn.Models.Courses
import com.example.learn.Models.Labs
import com.example.learn.Models.Login
import com.example.learn.Routes
import com.example.learn.Utils.AuthenticationToken
import com.example.learn.Utils.ErrorHandler
import com.example.learn.Utils.RetrofitClient
import com.example.learn.Utils.StartLoginActivity
import com.example.learn.VirtualLabsActivity
import kotlinx.android.synthetic.main.activity_home.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

object LabList {
    fun getLabs(context: Context, labListElement: RecyclerView) {
        RetrofitClientLabs.instance.getLabs("Bearer ${AuthenticationToken(context).getJWT()}")
            .enqueue(object: Callback<Labs> {
                override fun onFailure(call: Call<Labs>, t: Throwable) {
                    ErrorHandler.handle(context,t.toString())
                }
                override fun onResponse(call: Call<Labs>, response: Response<Labs>) {
                    try{
                        if(response.isSuccessful) {
                            VirtualLabsActivity.LoadLabs(context,labListElement,response.body()!!.labs).execute()
                        } else{
                            val error= ApiError().getError(response.errorBody()?.string())
                            ErrorHandler.handle(context,error)
                            if(response.code()==401){
                                AuthenticationToken(context).setJWT("")
                                StartLoginActivity().execute(context)
                            }
                        }
                    }catch (e:Exception){
                        Log.v("GetLabs",e.toString())
                        ErrorHandler.handle(context,"Error loading labs")
                        return
                    }
                }
            })
    }
}

object RetrofitClientLabs {
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

    val instance: Lab by lazy {
        val retrofit= Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        retrofit.create(Lab::class.java)
    }
}