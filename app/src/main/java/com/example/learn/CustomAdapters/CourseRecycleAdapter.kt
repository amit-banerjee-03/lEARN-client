package com.example.learn.CustomAdapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.learn.Models.Course
import com.example.learn.R
import java.lang.Exception
import java.net.URL

class CourseRecycleAdapter(
    val context: Context,
    val courses: List<Course>,
    val itemClick: (Course) -> Unit
) : Adapter<CourseRecycleAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent?.context).inflate(R.layout.course_list_item, parent, false)
        return Holder(view,itemClick)
    }

    override fun getItemCount(): Int {
        return courses.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bindCourse(courses[position], context)
    }

    class LoadImage(courseImage: ImageView?, image: String) : AsyncTask<String, Void, Bitmap>() {
        val courseImage = courseImage
        val image = image
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
            courseImage?.setImageBitmap(result)
        }

    }

    inner class Holder(itemView: View, val itemClick: (Course) -> Unit) : ViewHolder(itemView) {
        val courseImage = itemView?.findViewById<ImageView>(R.id.courseImage)
        val courseName = itemView?.findViewById<TextView>(R.id.courseName)

        fun bindCourse(course: Course, context: Context) {
//            val resourceId=context.resources.getIdentifier(course.cover_image,"drawable",context.packageName)
//            courseImage.setImageResource(resourceId)
            courseName.text = course.name
            LoadImage(courseImage, course.cover_image).execute()
            itemView.setOnClickListener{itemClick(course)}
        }

    }
}