package com.example.learn

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learn.ApiCalls.HomePageCourses
import com.example.learn.CustomAdapters.CourseRecycleAdapter
import com.example.learn.CustomAdapters.ImageListView
import com.example.learn.Models.Course
import com.example.learn.Models.Courses
import kotlinx.android.synthetic.main.activity_home.*
import java.lang.Exception
import java.net.URL

class Home : AppCompatActivity() {

    lateinit var adapter: CourseRecycleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        HomePageCourses.getCourses(this,courseListView)
    }

    class LoadCourses(context: Context,courseListElement:RecyclerView,courses:List<Course>): AsyncTask<String, Void, String>(){
        val courses=courses
        val context=context
        val courseListElement=courseListElement
        override fun doInBackground(vararg p0: String?): String? {
            return "executed"
        }

        override fun onPostExecute(result: String?) {
            courseListElement.adapter=CourseRecycleAdapter(context,courses){course ->  
                Toast.makeText(context,course.description,Toast.LENGTH_LONG).show()
                val intent = Intent(context,VideoList::class.java)
                intent.putExtra("COURSE_ID",course.id)
                context.startActivity(intent)
            }
            val layoutManager = LinearLayoutManager(context)
            courseListElement.layoutManager=layoutManager
            courseListElement.setHasFixedSize(true)
        }

    }


}
