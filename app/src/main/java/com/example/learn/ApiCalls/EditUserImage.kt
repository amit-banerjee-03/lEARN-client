package com.example.learn.ApiCalls
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.FileUtils
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import com.example.learn.Api.ApiError
import com.example.learn.Models.User
import com.example.learn.Models.UserObj
import com.example.learn.Models.UserProgress
import com.example.learn.ProgressMonitorActivity
import com.example.learn.Utils.AuthenticationToken
import com.example.learn.Utils.ErrorHandler
import com.example.learn.Utils.LoadImage
import com.example.learn.Utils.StartLoginActivity
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.Exception
import java.net.URI

object EditUserImage {
    fun uploadImage(context: Context, image: Uri,userImage:ImageView) {
        val file=File(getPathFromURI(context,image))
        val mediaType=MediaType.parse("image/*")!!
        val filePart=RequestBody.create(
            mediaType,file
        )
        val fileX=MultipartBody.Part.createFormData("image",file.name,filePart)
        RetrofitClientPorgress.instance.editProfileImage("Bearer ${AuthenticationToken(context).getJWT()}",fileX)
            .enqueue(object : Callback<UserObj> {
                override fun onFailure(call: Call<UserObj>, t: Throwable) {
                    Log.v("Coding",t.toString())
                    ErrorHandler.handle(context,t.toString())
                }
                override fun onResponse(call: Call<UserObj>, response: Response<UserObj>) {
                    try{
                        if(response.isSuccessful) {
                            Log.v("GetVideos",response.body().toString())
                            val at=AuthenticationToken(context)
                            at.setUserDp(response.body()!!.user.display_picture_url.toString())
                            LoadImage(userImage,response.body()!!.user.display_picture_url.toString()).execute()
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
    fun getPathFromURI(context: Context, uri: Uri): String? {
        val path: String = uri.path!!
        var realPath: String? = null

        val databaseUri: Uri
        val selection: String?
        val selectionArgs: Array<String>?
        if (path.contains("/document/image:")) { // files selected from "Documents"
            databaseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            selection = "_id=?"
            selectionArgs = arrayOf(DocumentsContract.getDocumentId(uri).split(":")[1])
        } else { // files selected from all other sources, especially on Samsung devices
            databaseUri = uri
            selection = null
            selectionArgs = null
        }
        try {
            val projection = arrayOf(
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.ORIENTATION,
                MediaStore.Images.Media.DATE_TAKEN
            ) // some example data you can query
            val cursor = context.contentResolver.query(
                databaseUri,
                projection, selection, selectionArgs, null
            )
            if (cursor!!.moveToFirst()) {
                val columnIndex = cursor!!.getColumnIndex(projection[0])
                realPath = cursor.getString(columnIndex)
            }
            cursor.close()
        } catch (e: Exception) {
            Log.v("Coding", e.message)
        }
        Log.v("Coding",realPath)
        return realPath
    }
}