package com.example.learn.CustomAdapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.learn.Models.Course
import com.example.learn.R
import java.lang.Exception
import java.net.URL


class ImageListView(context: Context, courses: List<Course>):BaseAdapter(){
    val context=context
    val courses=courses

    class LoadImage(courseImage:ImageView?,image:String):AsyncTask<String,Void,Bitmap>(){
        val courseImage=courseImage
        val image=image
        override fun doInBackground(vararg p0: String?): Bitmap? {
            var bmp:Bitmap=Bitmap.createBitmap(100, 50, Bitmap.Config.ARGB_8888)
            try{
                val url= URL(image)
                bmp=BitmapFactory.decodeStream(url.openConnection().getInputStream())
            }catch (e:Exception){
                Log.i("Image Not Loaded",e.toString())
            }
            return bmp
        }

        override fun onPostExecute(result: Bitmap?) {
            courseImage?.setImageBitmap(result)
        }

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val courseView: View
        var holder:ViewHolder
        if(convertView == null){
            courseView=LayoutInflater.from(context).inflate(R.layout.course_list_item,null)
            val courseImage: ImageView=courseView.findViewById(R.id.courseImage)
            val courseName: TextView=courseView.findViewById(R.id.courseName)
            holder=ViewHolder()
            holder.courseImage=courseImage
            holder.courseName=courseName
            courseView.tag=holder
        } else{
            holder=convertView.tag as ViewHolder
            courseView=convertView
        }


        val course=courses[position]
        holder.courseName?.text=course.name
        LoadImage(holder.courseImage, course.cover_image).execute()
//        val resourseId=context.resources.getIdentifier(course.image,"drawable",context.packageName)
//        courseImage.setImageResource(resourseId)
        return courseView
    }

    override fun getItem(position: Int): Any {
        return courses[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return courses.count()
    }

    private class ViewHolder{
        var courseImage:ImageView?=null
        var courseName:TextView?=null

    }

}