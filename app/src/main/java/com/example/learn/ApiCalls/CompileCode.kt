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
import com.example.learn.Api.*
import com.example.learn.Config.Constants
import com.example.learn.CustomAdapters.ImageListView
import com.example.learn.Models.Code
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

object CompileCode {
    fun compile(context: Context,source:String,lang:String,outputText:TextView,customInputString: String) {
        RetrofitClientCode.instance.compile("Bearer ${AuthenticationToken(context).getJWT()}",Utility.escapeString(source),lang,customInputString)
            .enqueue(object: Callback<Code> {
                override fun onFailure(call: Call<Code>, t: Throwable) {
                    ErrorHandler.handle(context,t.toString())
                }
                override fun onResponse(call: Call<Code>, response: Response<Code>) {
                    try{
                        if(response.isSuccessful) {
                            outputText.text="Compilation Status: "+response.body()!!.response.compile_status
                        } else{
                            val error= ApiError().getError(response.errorBody()?.string())
                            outputText.text=error
                            if(response.code()==401){
                                AuthenticationToken(context).setJWT("")
                                StartLoginActivity().execute(context)
                            }
                        }
                    }catch (e:Exception){
                        Log.v("Coding",e.toString())
                        outputText.text="Error compiling code"
                        return
                    }
                }
            })
    }
}

object RetrofitClientCode {
    private const val BASE_URL= Constants.SERVER_HOST
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor{chain ->
            val original=chain.request()

            val requestBuilder=original.newBuilder()
                .method(original.method(),original.body())
            val request=requestBuilder.build()
            chain.proceed(request)
        }.build()

    val instance: Coding by lazy {
        val retrofit= Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        retrofit.create(Coding::class.java)
    }
}