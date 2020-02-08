package com.example.learn.Utils

import android.content.Context
import android.widget.Toast
import com.example.learn.Config.Constants
import okhttp3.*
import java.io.IOException
import java.lang.Exception


object RequestServer {
    val client = OkHttpClient()
    fun postData(context:Context) {
        try {
            val url = Constants.SERVER_HOST + "products/"
            val request = Request.Builder().url(url).build()
            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val body = response?.body?.string()
                    println(body)
                }

                override fun onFailure(call: Call, e: IOException) {
                    ErrorHandler.handle(context, e.toString())
                }
            })
        } catch (e:Exception){
            Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show()
        }

    }
}