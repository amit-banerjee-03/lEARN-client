package com.example.learn.Utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.example.learn.Api.ApiError
import com.example.learn.Home
import com.example.learn.MainActivity
import com.example.learn.Models.Login
import com.example.learn.Routes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
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
                    Log.v("LoginResponse",t.toString())
                    ErrorHandler.handle(context,t.toString())
                }
                override fun onResponse(call: Call<Login>, response: Response<Login>) {
                    if(!response.isSuccessful){
                        Log.v("LoginResponse",response.errorBody().toString())
                        try {
                            val error = ApiError().getError(response.errorBody()?.string())
                            Log.v("LoginResponse",response.errorBody()?.string())
                            ErrorHandler.handle(context, error)
                        }catch (e:Exception){
                            Log.v("LoginResponse",e.toString())
                        }
                        return
                    }
                    AuthenticationToken(context).setJWT(response.body()!!.token)
                    Toast.makeText(context,"Successfully logged in",Toast.LENGTH_LONG).show()
                    StartHomeActivity().execute(context)
                }

            })
    }
}