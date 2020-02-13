package com.example.learn.Utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.widget.Toast
import com.example.learn.Home
import com.example.learn.MainActivity
import com.example.learn.Models.Login
import com.example.learn.Routes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.math.log

object Authenticate {

    class StartHomeActivity:AsyncTask<Context,Context,Context>(){
        override fun doInBackground(vararg p0: Context?): Context? {
            return p0[0]
        }

        override fun onPostExecute(result: Context?) {
            result!!.startActivity(Intent(result,Home::class.java))
            (result as MainActivity).finish()
        }

    }

    fun login(context: Context, userName: String, password: String) {
        val url = Routes.routes.getValue("LOGIN")
        if (url.isNullOrEmpty()) {
            ErrorHandler.handle(context, "Route not found")
            return
        }
        RetrofitClient.instance.login(userName,password)
            .enqueue(object: Callback<Login>{
                override fun onFailure(call: Call<Login>, t: Throwable) {
                    ErrorHandler.handle(context,t.toString())
                }
                override fun onResponse(call: Call<Login>, response: Response<Login>) {
                    println(response)
                    if(response.body()!!.error!=""){
                        ErrorHandler.handle(context,response.body()!!.error)
                        return
                    }
                    AuthenticationToken(context).setJWT(response.body()!!.token)
                    Toast.makeText(context,"Successfully logged in",Toast.LENGTH_LONG).show()
                    StartHomeActivity().execute(context)
                    println(response.body()?.toString())
                }

            })
    }
}