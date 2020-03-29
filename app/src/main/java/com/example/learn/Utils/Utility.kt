package com.example.learn.Utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.learn.Api.ApiError
import com.example.learn.Config.Constants
import okhttp3.ResponseBody
import retrofit2.*
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.URL
import java.net.URLEncoder
import java.security.cert.CertPath

object Utility {
    fun generateGetString(map: Map<String, String>): String {
        var url = ""
        for (key in map.keys) {
            url += "$key=${map[key]}&"
        }
        if (url != "") {
            url = url.substring(0, url.length - 2)
        }
        return URLEncoder.encode(url,"utf-8")
    }

    fun escapeString(string: String):String{
        return string.replace('"','\"',false)
    }

    fun downloadFile(context:Context,link:String){
        val BASE_URL= Constants.SERVER_HOST
        val builder=Retrofit.Builder()
            .baseUrl(BASE_URL)
        val retrofit=builder.build()
        val fileDownloadClient=retrofit.create(com.example.learn.Utils.File::class.java)
        val call = fileDownloadClient.downloadFile(link)
        call.enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                ErrorHandler.handle(context,t.toString())
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try{
                    if(response.isSuccessful) {
                        val success=writeResponseDataToDisk(response.body()!!)
                        if(success){
                            Toast.makeText(context,"Certificate downloaded successfully",Toast.LENGTH_LONG).show()
                        } else{
                            Toast.makeText(context,"Error downloading certificate",Toast.LENGTH_LONG).show()
                        }
                    } else{
                        val error= ApiError().getError(response.errorBody()?.string())
                        ErrorHandler.handle(context,error)
                    }
                }catch (e:Exception){
                    Log.v("Coding",e.toString())
                    ErrorHandler.handle(context,e.toString())
                    return
                }
            }
        })
    }

    fun writeResponseDataToDisk(body: ResponseBody):Boolean{
        try {
            val downloadFile=File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                "certificate.pdf"
            )
            var inputStream:InputStream?=null
            var outputStream:OutputStream?=null
            try {
                var fileReader= ByteArray(4096)
                var fileSize=body.contentLength()
                var fileSizeDownloaded=0
                inputStream=body.byteStream()
                outputStream=FileOutputStream(downloadFile)
                while (true){
                    var read=inputStream.read(fileReader)
                    if(read==-1){
                        break
                    }
                    outputStream.write(fileReader,0,read)
                    fileSizeDownloaded+=read
                }
                outputStream.flush()
                return true
            } catch (e:java.lang.Exception){
                Log.v("Coding",e.toString())
                return false
            } finally {
                if(inputStream!=null){
                    inputStream.close()
                }
                if(outputStream!=null){
                    outputStream.close()
                }
            }
        }catch (e:java.lang.Exception){
            Log.v("Coding",e.toString())
            return false
        }
    }


}