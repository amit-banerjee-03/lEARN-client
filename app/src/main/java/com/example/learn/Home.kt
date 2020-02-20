package com.example.learn

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import com.example.learn.ApiCalls.HomePageCourses
import com.example.learn.CustomAdapters.ImageListView
import com.example.learn.Models.Course
import com.example.learn.Models.Courses
import kotlinx.android.synthetic.main.activity_home.*
import java.lang.Exception
import java.net.URL

class Home : AppCompatActivity() {

    lateinit var adapter: ImageListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        HomePageCourses.getCourses(this,courseListView)
    }

    class LoadCourses(context: Context,courseListElement:ListView,courses:List<Course>): AsyncTask<String, Void, String>(){
        val courses=courses
        val context=context
        val courseListElement=courseListElement
        override fun doInBackground(vararg p0: String?): String? {
            return "executed"
        }

        override fun onPostExecute(result: String?) {
            courseListElement.adapter=ImageListView(context,courses)
        }

    }


}
