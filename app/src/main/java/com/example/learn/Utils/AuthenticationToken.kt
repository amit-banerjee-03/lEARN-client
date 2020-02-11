package com.example.learn.Utils

import android.content.Context

class AuthenticationToken(context: Context) {
    val sharedPreference=context.getSharedPreferences("JWT_LOGIN", Context.MODE_PRIVATE)

    fun getJWT(): String{
        val token = sharedPreference.getString("JWT","")
        return token!!
    }

    fun setJWT(token:String){
        val editor=sharedPreference.edit()
        editor.putString("JWT",token)
        editor.apply()
    }

}