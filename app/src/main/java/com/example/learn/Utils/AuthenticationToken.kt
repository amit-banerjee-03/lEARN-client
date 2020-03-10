package com.example.learn.Utils

import android.content.Context
import com.example.learn.Models.Login
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

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

    fun setUserDetails(responseBody: Login){
        setJWT(responseBody.token)
        val editor=sharedPreference.edit()
        val user=responseBody.user
        editor.putString("user_id",user._id)
        editor.putString("user_first_name",user.first_name)
        editor.putString("user_last_name",user.last_name)
        editor.putString("user_gender",user.gender)
        editor.putString("user_date_of_birth",user.date_of_birth)
        editor.putString("user_email",user.email)
        var displayPicture=""
        if(!user.display_picture_url.isNullOrEmpty()){
            displayPicture=user.display_picture_url
        }
        editor.putString("user_display_picture_url",displayPicture)
        editor.apply()
    }

    fun getUserName(): String{
        val name = sharedPreference.getString("user_first_name","First Name")+" "+sharedPreference.getString("user_last_name","Last Name")
        return name!!
    }
    fun getUserGender():String{
        val gender=sharedPreference.getString("user_gender","N/A")
        return gender!!
    }
    fun getUserDateOfBirth():String{
        val dateOfBirth=sharedPreference.getString("user_date_of_birth","")
        return dateOfBirth!!
    }
    fun getUserEmail():String{
        val email=sharedPreference.getString("user_email","Email Id")
        return email!!
    }
    fun getUserDisplayPictureUrl():String{
        val dp=sharedPreference.getString("user_display_picture_url","")
        return dp!!
    }

}