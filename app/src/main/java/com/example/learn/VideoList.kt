package com.example.learn

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learn.ApiCalls.CourseVideoList
import com.example.learn.CustomAdapters.CourseRecycleAdapter
import com.example.learn.CustomAdapters.VideoRecycleAdapter
import com.example.learn.Menu.Action
import com.example.learn.Models.Course
import com.example.learn.Models.Video
import kotlinx.android.synthetic.main.activity_video_list.*
import org.w3c.dom.Text

class VideoList : AppCompatActivity() {
    lateinit var adapter: VideoRecycleAdapter
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context=this
        setContentView(R.layout.activity_video_list)
        CourseVideoList.getVideos(this,courseDetailImage,videoListView,courseDescription,no_video_found,intent.getIntExtra("COURSE_ID",0))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.default_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId
        Action.performOperation(context,id)
        return super.onOptionsItemSelected(item)
    }

    class LoadVideos(context: Context, videoListElement: RecyclerView, noVideoFound:TextView,courseDescription:TextView,course:Course): AsyncTask<String, Void, String>(){
        val course=course
        val courseDescription=courseDescription
        val videos=course.videos
        val context=context
        val videoListElement=videoListElement
        val noVideoFound=noVideoFound
        override fun doInBackground(vararg p0: String?): String? {
            return "executed"
        }

        override fun onPostExecute(result: String?) {
            courseDescription.text=course.description
            videoListElement.adapter= VideoRecycleAdapter(context,videos){ video ->
                val intent=Intent(context,VideoPlayerActivity::class.java)
                intent.putExtra("VIDEO_ID",video.id)
                intent.putExtra("VIDEO_URL",video.url)
                intent.putExtra("VIDEO_CAPTION",video.caption)
                intent.putExtra("VIDEO_DESCRIPTION",video.description)
                context.startActivity(intent)
            }
            val layoutManager = LinearLayoutManager(context)
            videoListElement.layoutManager=layoutManager
            videoListElement.setHasFixedSize(true)
            if(videos.isEmpty()){
                videoListElement.visibility=View.GONE
                noVideoFound.visibility=View.VISIBLE
            } else{
                noVideoFound.visibility=View.GONE
                videoListElement.visibility=View.VISIBLE
            }
        }

    }

}
