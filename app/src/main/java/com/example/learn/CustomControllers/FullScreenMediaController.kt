package com.example.learn.CustomControllers

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.net.Uri
import android.text.Layout
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.core.view.marginLeft


class FullScreenMediaController(val contextObj: Context,val videoPlayer:VideoView): MediaController(contextObj) {
    override fun setAnchorView(view: View?) {
        super.setAnchorView(view)
        val fullScreenButton=Button(contextObj)
        fullScreenButton.text="[ ]"
        val params= FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.gravity=Gravity.RIGHT
        fullScreenButton.setOnClickListener {
            Toast.makeText(contextObj,"FullScreen",Toast.LENGTH_LONG).show()
            if((contextObj as Activity).requestedOrientation==ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
                contextObj.actionBar?.show()
                contextObj.requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            } else{
                contextObj.actionBar?.hide()
                contextObj.requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }

        }
        addView(fullScreenButton,params)
    }
}
