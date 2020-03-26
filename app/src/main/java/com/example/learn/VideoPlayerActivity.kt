package com.example.learn

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.MediaController
import android.widget.Toast
import com.example.learn.ApiCalls.FinishVideo
import com.example.learn.CustomControllers.FullScreenMediaController
import com.example.learn.Menu.Action
import kotlinx.android.synthetic.main.activity_video_player.*
import java.net.URI

class VideoPlayerActivity : AppCompatActivity() {

    private var playbackPosition=0
    private var rtspUrl=""
    private var caption=""
    private var description=""
    private var serialNumber=0
    private var id=0

    private lateinit var mediaController:FullScreenMediaController
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context=this
        setContentView(R.layout.activity_video_player)
        id=intent.getIntExtra("VIDEO_ID",0)
        rtspUrl=intent.getStringExtra("VIDEO_URL")
        caption=intent.getStringExtra("VIDEO_CAPTION")
        description=intent.getStringExtra("VIDEO_DESCRIPTION")
        mediaController= FullScreenMediaController(this,videoPlayer)

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

        videoPlayer.setOnCompletionListener {
            FinishVideo.execute(this,id)
        }

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
