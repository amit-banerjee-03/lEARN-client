package com.example.learn.Utils

import android.content.Context
import android.widget.Toast
import com.example.learn.Models.Login
import com.example.learn.Routes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.math.log

object Authenticate {
    fun login(context: Context, userName: String, password: String) {
        val url = Routes.routes.getValue("LOGIN")
        if (url.isNullOrEmpty()) {
            ErrorHandler.handle(context, "Route not found")
            return
        }
        val map = mapOf("username" to userName, "password" to password)

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
                    Toast.makeText(context,"Successfully logged in",Toast.LENGTH_LONG).show();
                    println(response.body()?.toString())
                }

            })
    }
}