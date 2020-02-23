package com.example.learn

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import kotlinx.android.synthetic.main.activity_video_player.*
import java.net.URI

class VideoPlayerActivity : AppCompatActivity() {

    private var playbackPosition=0
    private var rtspUrl=""
    private var caption=""
    private var description=""
    private var serialNumber=0
    private lateinit var mediaController:MediaController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)
        rtspUrl=intent.getStringExtra("VIDEO_URL")
        caption=intent.getStringExtra("VIDEO_CAPTION")
        description=intent.getStringExtra("VIDEO_DESCRIPTION")
        mediaController= MediaController(this)

        videoPlayer.setOnPreparedListener{
            mediaController.setAnchorView(videoContainer)
            videoPlayer.setMediaController(mediaController)
            videoPlayer.seekTo(playbackPosition)
            videoPlayer.start()
        }

        videoPlayer.setOnInfoListener{player,what,extras->
            if(what==MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START){
                videoPlayerProgressBar.visibility=View.GONE
            }
            true
        }
    }

    override fun onStart() {
        super.onStart()
        val uri=Uri.parse((rtspUrl))
        videoPlayer.setVideoURI(uri)
        videoPlayerProgressBar.visibility=View.VISIBLE
        videoTitle.text=caption
        videoDescription.text=description
    }

    override fun onPause() {
        super.onPause()
        videoPlayer.pause()
        playbackPosition=videoPlayer.currentPosition
    }

    override fun onStop() {
        videoPlayer.stopPlayback()
        super.onStop()
    }

}
