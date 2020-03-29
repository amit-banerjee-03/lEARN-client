package com.example.learn.ApiCalls

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Build
import android.text.Html
import android.util.Base64
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.learn.*
import com.example.learn.Api.*
import com.example.learn.Config.Constants
import com.example.learn.CustomAdapters.ImageListView
import com.example.learn.Models.*
import com.example.learn.Utils.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_lab_problem.*
import okhttp3.OkHttpClient
import okhttp3.internal.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

object CourseCompletionCertificate {
    fun getCertificate(context: Context,courseId:Int) {
        RetrofitClientCourse.instance.getCertificate("Bearer ${AuthenticationToken(context).getJWT()}",courseId)
            .enqueue(object: Callback<Certificate> {
                override fun onFailure(call: Call<Certificate>, t: Throwable) {
                    ErrorHandler.handle(context,t.toString())
                }
                override fun onResponse(call: Call<Certificate>, response: Response<Certificate>) {
                    try{
                        if(response.isSuccessful) {
                            Utility.downloadFile(context,response.body()!!.url)
                        } else{
                            val error= ApiError().getError(response.errorBody()?.string())
                            ErrorHandler.handle(context,error)
                            if(response.code()==401){
                                AuthenticationToken(context).setJWT("")
                                StartLoginActivity().execute(context)
                            }
                        }
                    }catch (e:Exception){
                        Log.v("Coding",e.toString())
                        ErrorHandler.handle(context,e.toString())
                        return
                    }
                }
            })
    }
}