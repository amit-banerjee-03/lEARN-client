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
import com.example.learn.Models.Video
import com.example.learn.R
import java.lang.Exception
import java.net.URL

class VideoRecycleAdapter(
    val context: Context,
    val videos: List<Video>,
    val itemClick: (Video) -> Unit
) : Adapter<VideoRecycleAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent?.context).inflate(R.layout.course_video_list, parent, false)
        return Holder(view,itemClick)
    }

    override fun getItemCount(): Int {
        return videos.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bindVideo(videos[position], context)
    }

    inner class Holder(itemView: View, val itemClick: (Video) -> Unit) : ViewHolder(itemView) {
        val videoCaption = itemView?.findViewById<TextView>(R.id.videoCaption)
        fun bindVideo(video: Video, context: Context) {
            videoCaption.text = video.caption
            itemView.setOnClickListener{itemClick(video)}
        }

    }

}