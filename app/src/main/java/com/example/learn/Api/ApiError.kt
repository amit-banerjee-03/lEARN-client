package com.example.learn.Api

import android.util.Log
import org.json.JSONObject

class ApiError {
    fun getMessage(error:String?):String{
        val json=JSONObject(error)
        return "Message: " + json.get("message").toString()
    }

    fun getError(error:String?):String{
        val json=JSONObject(error)
        return "Error:"+ json.get("error").toString()
    }

}