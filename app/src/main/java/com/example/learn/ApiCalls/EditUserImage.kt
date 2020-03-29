package com.example.learn.ApiCalls
import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import com.example.learn.Api.ApiError
import com.example.learn.Models.User
import com.example.learn.Models.UserProgress
import com.example.learn.ProgressMonitorActivity
import com.example.learn.Utils.AuthenticationToken
import com.example.learn.Utils.ErrorHandler
import com.example.learn.Utils.LoadImage
import com.example.learn.Utils.StartLoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.lang.Exception

object EditUserImage {
    fun uploadImage(context: Context, image: Bitmap,userImage:ImageView) {
        val byteArrayOutputStream=ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream)
        val byteArray=byteArrayOutputStream.toByteArray()
        val base64String=Base64.encodeToString(byteArray,Base64.DEFAULT)
        RetrofitClientPorgress.instance.editProfileImage("Bearer ${AuthenticationToken(context).getJWT()}",base64String)
            .enqueue(object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    ErrorHandler.handle(context,t.toString())
                }
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    try{
                        if(response.isSuccessful) {
                            Log.v("GetVideos",response.body().toString())
                            LoadImage(userImage,response.body()!!.display_picture_url.toString())
                        } else{
                            val error= ApiError().getError(response.errorBody()?.string())
                            ErrorHandler.handle(context,error)
                            if(response.code()==401){
                                AuthenticationToken(context).setJWT("")
                                StartLoginActivity().execute(context)
                            }
                        }
                    }catch (e: Exception){
                        Log.v("GetVideos",e.toString())
                        return
                    }
                }
            })
    }
}