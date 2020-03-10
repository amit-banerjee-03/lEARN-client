package com.example.learn.Utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import java.lang.Exception
import java.net.URL

class LoadImage(private val imageView: ImageView?, private val image: String) : AsyncTask<String, Void, Bitmap>() {
    override fun doInBackground(vararg p0: String?): Bitmap? {
        var bmp: Bitmap = Bitmap.createBitmap(100, 50, Bitmap.Config.ARGB_8888)
        try {
            val url = URL(image)
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
        } catch (e: Exception) {
            Log.i("Image Not Loaded", e.toString())
        }
        return bmp
    }

    override fun onPostExecute(result: Bitmap?) {
        imageView?.setImageBitmap(result)
    }

}